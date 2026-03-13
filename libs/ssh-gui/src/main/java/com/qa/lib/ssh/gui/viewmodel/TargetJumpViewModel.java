package com.qa.lib.ssh.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import com.qa.lib.ssh.service.ssh.SshJumpConfig;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

public final class TargetJumpViewModel extends ComponentViewModel {
    private final SessionViewModel targetSessionViewModel;
    private final SessionViewModel jumpSessionViewModel;

    @Inject
    public TargetJumpViewModel(SessionViewModel jumpSessionViewModel, SessionViewModel targetSessionViewModel) {
        this.jumpSessionViewModel = jumpSessionViewModel;
        this.targetSessionViewModel = targetSessionViewModel;
    }

    public BooleanProperty jumpEnabledProperty() { return jumpSessionViewModel.enabledProperty(); }

    public SessionViewModel getTargetSessionViewModel() {
        return targetSessionViewModel;
    }

    public SessionViewModel getJumpSessionViewModel() {
        return jumpSessionViewModel;
    }

    public SshJumpConfig onConfirm() {
        return new SshJumpConfig(
                targetSessionViewModel.hostProperty().get(),
                Integer.getInteger(targetSessionViewModel.portProperty().get(), 22),
                targetSessionViewModel.usernameProperty().get(),
                targetSessionViewModel.passwordProperty().get(),
                jumpSessionViewModel.hostProperty().get(),
                Integer.getInteger(jumpSessionViewModel.portProperty().get(), 22),
                jumpSessionViewModel.usernameProperty().get(),
                jumpSessionViewModel.passwordProperty().get(),
                jumpSessionViewModel.enabledProperty().get()
        );
    }
}
