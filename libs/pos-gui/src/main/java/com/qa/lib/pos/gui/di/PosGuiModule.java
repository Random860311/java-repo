package com.qa.lib.pos.gui.di;

import com.google.inject.AbstractModule;
import com.qa.lib.pos.gui.controller.HomeController;
import com.qa.lib.pos.gui.controller.SettingsController;
import com.qa.lib.pos.gui.controller.SshController;
import com.qa.lib.pos.gui.viewmodel.HomeViewModel;
import com.qa.lib.pos.gui.viewmodel.SettingsViewModel;
import com.qa.lib.pos.gui.viewmodel.SshViewModel;


public final class PosGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());
        install(new NavigationModule());

        bind(SettingsViewModel.class);
        bind(HomeViewModel.class);
        bind(SshViewModel.class);

        bind(SettingsController.class);
        bind(HomeController.class);
        bind(SshController.class);
    }
}
