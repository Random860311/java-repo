package com.qa.lib.ssh.gui.di;

import com.google.inject.AbstractModule;
import com.qa.lib.ssh.gui.controller.SessionController;
import com.qa.lib.ssh.gui.controller.TargetController;
import com.qa.lib.ssh.gui.controller.TargetJumpController;
import com.qa.lib.ssh.gui.viewmodel.SessionViewModel;
import com.qa.lib.ssh.gui.viewmodel.TargetJumpViewModel;
import com.qa.lib.ssh.gui.viewmodel.TargetViewModel;

public final class SshGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(SessionController.class);
        bind(TargetController.class);
        bind(TargetJumpController.class);

        bind(SessionViewModel.class);
        bind(TargetViewModel.class);
        bind(TargetJumpViewModel.class);
    }
}
