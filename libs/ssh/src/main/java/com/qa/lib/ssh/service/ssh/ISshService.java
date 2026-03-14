package com.qa.lib.ssh.service.ssh;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.CompletableFuture;

public interface ISshService {
    boolean isConnectedTo(String targetHost, int targetPort);
    boolean isConnectedTo(SshConfig config);
    boolean isConnected();

    void init(SshConfig config);
    void init(SshJumpConfig config);
    String runCommand(String command);
    void downloadTextFile(String remotePath, String localPath);
    String downloadTextFile(String remotePath);
    void uploadTextFile(String remotePath, @NonNull String content);
    void uploadFile(String remotePath, String localPath);

    void close();
}
