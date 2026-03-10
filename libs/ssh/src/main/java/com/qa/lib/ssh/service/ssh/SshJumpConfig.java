package com.qa.lib.ssh.service.ssh;

public class SshJumpConfig extends SshConfig {
    private String jumpHost;
    private int jumpPort;
    private String jumpUsername;
    private String jumpPassword;

    public SshJumpConfig(String targetHost, String jumpHost, int port, String userName, String password) {
        this(targetHost, port, userName, password, jumpHost, port, userName, password);
    }

    public SshJumpConfig(String targetHost, int targetPort, String targetUsername, String targetPassword, String jumpHost, int jumpPort, String jumpUsername, String jumpPassword) {
        super(targetHost, targetPort, targetUsername, targetPassword);
        this.jumpHost = jumpHost;
        this.jumpPort = jumpPort;
        this.jumpUsername = jumpUsername;
        this.jumpPassword = jumpPassword;
    }

    public String getJumpHost() {
        return jumpHost;
    }

    public void setJumpHost(String jumpHost) {
        this.jumpHost = jumpHost;
    }

    public int getJumpPort() {
        return jumpPort;
    }

    public void setJumpPort(int jumpPort) {
        this.jumpPort = jumpPort;
    }

    public String getJumpUsername() {
        return jumpUsername;
    }

    public void setJumpUsername(String jumpUsername) {
        this.jumpUsername = jumpUsername;
    }

    public String getJumpPassword() {
        return jumpPassword;
    }

    public void setJumpPassword(String jumpPassword) {
        this.jumpPassword = jumpPassword;
    }
}
