package com.qa.lib.ssh.service.ssh;

public class SshConfig {
    private String targetHost;
    private int targetPort;
    private String targetUsername;
    private String targetPassword;

    public SshConfig(String targetHost, int targetPort, String targetUsername, String targetPassword) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.targetUsername = targetUsername;
        this.targetPassword = targetPassword;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getTargetPassword() {
        return targetPassword;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }
}
