package com.qa.lib.core.ssh.service;

import com.qa.lib.core.ssh.dto.SshConnectionConfigDto;

import java.util.concurrent.CompletableFuture;

public interface ISSHService {

    void init(SshConnectionConfigDto config);

    CompletableFuture<Void> initAsync(SshConnectionConfigDto config);

    String runCommand(String command);

    CompletableFuture<String> runCommandAsync(String command);

    /**
     * Closes the SSH session.
     */
    void close();
}
