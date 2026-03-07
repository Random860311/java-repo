package com.qa.lib.core.ssh.dto;

public class SshConnectionConfigDto {
    private final String jumpHost;
    private final int jumpPort;
    private final String jumpUsername;
    private final String jumpPassword;
    private final String targetHost;
    private final int targetPort;
    private final String targetUsername;
    private final String targetPassword;

    public SshConnectionConfigDto(
            String jumpHost,
            int jumpPort,
            String jumpUsername,
            String jumpPassword,
            String targetHost,
            int targetPort,
            String targetUsername,
            String targetPassword) {
        this.jumpHost = jumpHost;
        this.jumpPort = jumpPort;
        this.jumpUsername = jumpUsername;
        this.jumpPassword = jumpPassword;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.targetUsername = targetUsername;
        this.targetPassword = targetPassword;
    }

    public String getJumpHost() { return jumpHost; }
    public int getJumpPort() { return jumpPort; }
    public String getJumpUsername() { return jumpUsername; }
    public String getJumpPassword() { return jumpPassword; }
    public String getTargetHost() { return targetHost; }
    public int getTargetPort() { return targetPort; }
    public String getTargetUsername() { return targetUsername; }
    public String getTargetPassword() { return targetPassword; }
}
