package com.qa.lib.ssh.service.ssh;

public interface ISshConnectionListener {
    void onConnectionChange(SshConnectionStatus status);
}
