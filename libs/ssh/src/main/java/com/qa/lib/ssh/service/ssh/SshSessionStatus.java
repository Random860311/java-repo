package com.qa.lib.ssh.service.ssh;

public final class SshSessionStatus {
    private final boolean connected;
    private final String host;
    private final int port;
    private final boolean faulted;

    public SshSessionStatus(boolean connected, String host, int port) {
        this(connected, host, port, false);
    }

    public SshSessionStatus(boolean connected, String host, int port, boolean faulted) {
        this.connected = connected;
        this.host = host;
        this.port = port;
        this.faulted = faulted;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isFaulted() {
        return faulted;
    }
}
