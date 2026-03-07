package com.qa.lib.core.ssh.service;

import com.google.inject.Inject;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.qa.lib.core.qualifiers.BackgroundThread;
import com.qa.lib.core.ssh.dto.SshConnectionConfigDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class SSHServiceImp implements ISSHService {
    private final Executor backgroundExecutor;

    private final Object sessionLock = new Object();

    private Session jumpSession;
    private Session targetSession;
    private int forwardedLocalPort;

    @Inject
    public SSHServiceImp(@BackgroundThread Executor backgroundExecutor) {
        this.backgroundExecutor = backgroundExecutor;
    }

    public void init(SshConnectionConfigDto config) {
        synchronized (sessionLock) {
            close();
            try {
                System.out.println("Init session");
                JSch jsch = new JSch();
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");

                // 1) Connect to jump host
                Session newJumpSession = jsch.getSession(
                        config.getJumpUsername(),
                        config.getJumpHost(),
                        config.getJumpPort()
                );
                newJumpSession.setPassword(config.getJumpPassword());
                newJumpSession.setConfig(sshConfig);
                newJumpSession.setServerAliveInterval(120_000);
                newJumpSession.setServerAliveCountMax(3);
                newJumpSession.connect(15_000);

                System.out.println("Jump session connected: " + newJumpSession.isConnected());

                // 2) Forward random local port -> target:22 through jump host
                int assignedPort = newJumpSession.setPortForwardingL(
                        0,
                        config.getTargetHost(),
                        config.getTargetPort()
                );

                // 3) Connect to target through localhost:assignedPort
                Session newTargetSession = jsch.getSession(
                        config.getTargetUsername(),
                        "127.0.0.1",
                        assignedPort
                );
                newTargetSession.setPassword(config.getTargetPassword());
                newTargetSession.setConfig(sshConfig);
                newTargetSession.setServerAliveInterval(120_000);
                newTargetSession.setServerAliveCountMax(3);
                newTargetSession.connect(15_000);

                System.out.println("Target session connected: " + newTargetSession.isConnected());

                this.jumpSession = newJumpSession;
                this.targetSession = newTargetSession;
                this.forwardedLocalPort = assignedPort;
            } catch (Exception ex) {
                close();
                throw new RuntimeException("Failed to initialize SSH session.", ex);
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

            /**
             * Read stdout until the channel is closed and no more bytes remain.
             * Why this loop structure:
             * - inputStream.read() alone can block
             * - channel.isClosed() helps us know when remote execution finished
             * - inputStream.available() helps us drain remaining bytes
             */
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

    @Override
    public CompletableFuture<Void> initAsync(SshConnectionConfigDto config) {
        return CompletableFuture.runAsync(() -> init(config), backgroundExecutor);
    }

    @Override
    public CompletableFuture<String> runCommandAsync(String command) {
        return CompletableFuture.supplyAsync(() -> runCommand(command), backgroundExecutor);
    }

    public CompletableFuture<String> downloadTextFileAsync(String remotePath) {
        return CompletableFuture.supplyAsync(() -> {
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
        }, backgroundExecutor);
    }

    public CompletableFuture<Void> uploadTextFileAsync(String remotePath, String content) {
        return CompletableFuture.runAsync(() -> {
            synchronized (sessionLock) {
                ChannelSftp channel = null;

                try {
                    Session session = getConnectedTargetSession();

                    channel = (ChannelSftp) session.openChannel("sftp");
                    channel.connect();

                    byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
                        channel.put(inputStream, remotePath);
                    }

                } catch (Exception ex) {
                    throw new RuntimeException("Failed to upload remote file: " + remotePath, ex);
                } finally {
                    if (channel != null) {
                        channel.disconnect();
                    }
                }
            }
        }, backgroundExecutor);
    }

    /**
     * Validates that the session exists and is connected.
     */
    private synchronized Session getConnectedTargetSession() {
        if (targetSession == null || !targetSession.isConnected()) {
            throw new IllegalStateException("SSH session is not initialized or not connected.");
        }

        return targetSession;
    }

    public int getForwardedLocalPort() {
        synchronized (sessionLock) {
            return forwardedLocalPort;
        }
    }

    public void close() {
        synchronized (sessionLock) {
            if (targetSession != null) {
                targetSession.disconnect();
                targetSession = null;
            }

            if (jumpSession != null) {
                jumpSession.disconnect();
                jumpSession = null;
            }

            forwardedLocalPort = 0;
        }
    }
}
