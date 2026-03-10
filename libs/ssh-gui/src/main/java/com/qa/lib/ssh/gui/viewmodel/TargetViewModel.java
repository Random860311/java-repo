package com.qa.lib.ssh.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import com.qa.lib.ssh.service.SshConfig;

import java.util.function.Consumer;

public class TargetViewModel extends ComponentViewModel {
    private final SessionViewModel sessionViewModel;

    @Inject
    public TargetViewModel(SessionViewModel sessionViewModel) {
        this.sessionViewModel = sessionViewModel;
    }

    public SessionViewModel getSessionViewModel() {
        return sessionViewModel;
    }


    public SshConfig onConfirm() {
        return new SshConfig(
                sessionViewModel.hostProperty().get(),
                Integer.getInteger(sessionViewModel.portProperty().get(), 22),
                sessionViewModel.usernameProperty().get(),
                sessionViewModel.passwordProperty().get()
        );
    }
}
