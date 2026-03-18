package com.qa.lib.ssh.service.ssh;

import com.google.inject.Inject;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.qa.lib.core.service.log.ILogService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class SshServiceImp implements ISshService {
    private final ILogService logService;
    private final Set<ISshConnectionListener> listeners = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    private final Object sessionLock = new Object();

    private Session jumpSession;
    private Session targetSession;

    @Inject
    public SshServiceImp(ILogService logService) {
        this.logService = logService;
    }

    public void init(SshConfig config) {
        synchronized (sessionLock) {
            close();
            try {
                JSch jsch = new JSch();
                Session newTargetSession = createSession(
                        jsch,
                        config.getTargetHost(),
                        config.getTargetPort(),
                        config.getTargetUsername(),
                        config.getTargetPassword()
                );
                jumpSession = null;
                targetSession = newTargetSession;

                SshSessionStatus targetSessionStatus = new SshSessionStatus(true, targetSession.getHost(), targetSession.getPort());
                notifyConnectionStatusChanged(new SshConnectionStatus(targetSessionStatus));
            } catch (Exception ex) {
                close();
                throw new RuntimeException("Failed to initialize SSH session.", ex);
            }
        }
    }

    public void init(@NonNull SshJumpConfig config) {
        if (!config.isEnabled()) {
            init((SshConfig) config);
        }
        else {
            synchronized (sessionLock) {
                close();
                try {
                    JSch jsch = new JSch();
                    logService.debug("Creating Jump Session");
                    Session newJumpSession = createSession(
                            jsch,
                            config.getJumpHost(),
                            config.getJumpPort(),
                            config.getJumpUsername(),
                            config.getJumpPassword()
                    );

                    int assignedPort = newJumpSession.setPortForwardingL(
                            0,
                            config.getTargetHost(),
                            config.getTargetPort()
                    );
                    logService.debug("Creating Target Session");
                    Session newTargetSession = createSession(
                            jsch,
                            "127.0.0.1",
                            assignedPort,
                            config.getTargetUsername(),
                            config.getTargetPassword()
                    );

                    jumpSession = newJumpSession;
                    targetSession = newTargetSession;

                    SshSessionStatus targetSessionStatus = new SshSessionStatus(true, targetSession.getHost(), targetSession.getPort());
                    SshSessionStatus jumpSessionStatus = new SshSessionStatus(true, jumpSession.getHost(), jumpSession.getPort());
                    notifyConnectionStatusChanged(new SshConnectionStatus(targetSessionStatus, jumpSessionStatus));

                } catch (Exception ex) {
                    close();
                    throw new RuntimeException("Failed to initialize SSH jump session.", ex);
                }
            }
        }
    }

    @Override
    public String runCommand(String command) {
        ChannelExec channel = null;

        try {
            Session session = getConnectedTargetSession();

            // Open a fresh channel for this command.
            channel = (ChannelExec) session.openChannel("exec");

            // Set the command to execute remotely.
            channel.setCommand(command);

            // We are not sending input to the remote command.
            channel.setInputStream(null);

            // Optional: collect stderr separately if needed.
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            channel.setErrStream(errorStream);

            // Stdout from the remote command.
            InputStream inputStream = channel.getInputStream();

            // Start the command execution.
            channel.connect();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while (true) {
                while (inputStream.available() > 0) {
                    bytesRead = inputStream.read(buffer, 0, buffer.length);

                    if (bytesRead < 0) {
                        break;
                    }

                    outputStream.write(buffer, 0, bytesRead);
                }

                if (channel.isClosed()) {
                    // Drain any remaining bytes after command completion.
                    while (inputStream.available() > 0) {
                        bytesRead = inputStream.read(buffer, 0, buffer.length);

                        if (bytesRead < 0) {
                            break;
                        }

                        outputStream.write(buffer, 0, bytesRead);
                    }

                    int exitStatus = channel.getExitStatus();

                    if (exitStatus != 0) {
                        String errorText = new String(errorStream.toByteArray(), StandardCharsets.UTF_8);

                        throw new RuntimeException("Remote command failed. Exit status: " + exitStatus + (errorText.isEmpty() ? "" : ". Error: " + errorText));
                    }

                    break;
                }

                // Small pause to avoid busy looping.
                Thread.sleep(50);
            }

            return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to execute remote command: " + command, ex);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public void downloadTextFile(String remotePath, String localPath) {
        synchronized (sessionLock) {
            ChannelSftp channel = null;

            try {
                Session session = getConnectedTargetSession();

                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();

                Path local = Paths.get(localPath);
                Files.createDirectories(local.getParent());

                try (
                        InputStream inputStream = channel.get(remotePath);
                        OutputStream outputStream = Files.newOutputStream(local)
                ) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

            } catch (Exception ex) {
                throw new RuntimeException("Failed to download remote file: " + remotePath + " to " + localPath, ex);
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
            }
        }
    }

    public String downloadTextFile(String remotePath) {
        synchronized (sessionLock) {
            ChannelSftp channel = null;

            try {
                Session session = getConnectedTargetSession();

                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();

                try (InputStream inputStream = channel.get(remotePath)) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
                }

            } catch (Exception ex) {
                throw new RuntimeException("Failed to download remote file: " + remotePath, ex);
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
            }
        }
    }

    public void uploadTextFile(String remotePath, @NonNull String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            uploadFile(remotePath, inputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to prepare upload for remote file: " + remotePath, ex);
        }
    }

    public void uploadFile(String remotePath, String localPath) {
        Path path = Paths.get(localPath);

        if (!Files.exists(path)) {
            throw new RuntimeException("Local file does not exist: " + localPath);
        }

        if (!Files.isRegularFile(path)) {
            throw new RuntimeException("Local path is not a file: " + localPath);
        }

        try (InputStream inputStream = Files.newInputStream(path)) {
            uploadFile(remotePath, inputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read local file for upload. Local: " + localPath + ", Remote: " + remotePath, ex);
        }
    }

    @Override
    public boolean isConnected() {
        synchronized (sessionLock) {
            return targetSession != null && !targetSession.isConnected();
        }
    }

    @Override
    public boolean isConnectedTo(String targetHost, int targetPort) {
        boolean result = isConnected();
        synchronized (sessionLock) {
            return result && targetHost.equalsIgnoreCase(targetSession.getHost()) && targetSession.getPort() == targetPort;
        }
    }

    @Override
    public boolean isConnectedTo(@NonNull SshConfig config) {
        boolean result = isConnectedTo(config.getTargetHost(), config.getTargetPort());
        synchronized (sessionLock) {
            return result && config.getTargetUsername().equals(targetSession.getUserName());
        }
    }

    private @NonNull Session createSession(@NonNull JSch jsch, String host, int port, String username, String password) throws Exception {
        logService.debug("Establishing SSH connection to: " + host + ":" + username);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");

        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig(sshConfig);
        session.setServerAliveInterval(120_000);
        session.setServerAliveCountMax(3);
        session.connect(15_000);

        logService.debug("Created SSH connection to: " + host + ":" + port);

        return session;
    }

    private void uploadFile(String remotePath, InputStream inputStream) {
        synchronized (sessionLock) {
            ChannelSftp channel = null;

            try {
                Session session = getConnectedTargetSession();

                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();

                channel.put(inputStream, remotePath, ChannelSftp.OVERWRITE);

            } catch (Exception ex) {
                throw new RuntimeException("Failed to upload remote file: " + remotePath, ex);
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
            }
        }
    }

    /**
     * Validates that the session exists and is connected.
     */
    private synchronized Session getConnectedTargetSession() {
        synchronized (sessionLock) {
            if (targetSession == null || !targetSession.isConnected()) {
                throw new IllegalStateException("SSH session is not initialized or not connected.");
            }

            return targetSession;
        }
    }

    public void close() {
        synchronized (sessionLock) {
            SshSessionStatus targetSessionStatus = null;
            if (targetSession != null) {
                targetSession.disconnect();
                targetSessionStatus = new SshSessionStatus(false, targetSession.getHost(), targetSession.getPort());
            }
            SshSessionStatus jumpSessionStatus = null;
            if (jumpSession != null) {
                jumpSession.disconnect();
                jumpSessionStatus = new SshSessionStatus(false, jumpSession.getHost(), jumpSession.getPort());
            }
            notifyConnectionStatusChanged(new SshConnectionStatus(targetSessionStatus, jumpSessionStatus));

            targetSession = null;
            jumpSession = null;
        }
    }

    @Override
    public void addConnectionListener(ISshConnectionListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    @Override
    public void removeConnectionListener(ISshConnectionListener listener) {
        if (listener == null) {
            return;
        }
        listeners.remove(listener);
    }

    private void notifyConnectionStatusChanged(SshConnectionStatus status) {
        List<ISshConnectionListener> snapshot;

        synchronized (listeners) {
            snapshot = new ArrayList<>(listeners);
        }

        for (ISshConnectionListener listener : snapshot) {
            listener.onConnectionChange(status);
        }
    }
}
