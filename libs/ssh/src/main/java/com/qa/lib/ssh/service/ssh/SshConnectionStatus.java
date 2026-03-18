package com.qa.lib.ssh.service.ssh;

import javax.annotation.Nullable;

public final class SshConnectionStatus {
    private final SshSessionStatus targetStatus;

    private final SshSessionStatus jumpStatus;

    public SshConnectionStatus(SshSessionStatus targetStatus) {
        this(targetStatus, null);
    }

    public SshConnectionStatus(SshSessionStatus targetStatus, SshSessionStatus jumpStatus) {
        this.targetStatus = targetStatus;
        this.jumpStatus = jumpStatus;
    }

    public SshSessionStatus getTargetStatus() {
        return targetStatus;
    }

    @Nullable
    public SshSessionStatus getJumpStatus() {
        return jumpStatus;
    }
}
