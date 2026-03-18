package com.qa.lib.pos.gui.di;

import com.google.inject.AbstractModule;
import com.qa.lib.pos.gui.controller.PosHomeController;
import com.qa.lib.pos.gui.controller.PosSettingsController;
import com.qa.lib.pos.gui.controller.PosSshController;
import com.qa.lib.pos.gui.viewmodel.PosHomeViewModel;
import com.qa.lib.pos.gui.viewmodel.PosSettingsViewModel;
import com.qa.lib.pos.gui.viewmodel.PosSshViewModel;


public final class PosGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());
        install(new NavigationModule());

        bind(PosSettingsViewModel.class);
        bind(PosHomeViewModel.class);
        bind(PosSshViewModel.class);

        bind(PosSettingsController.class);
        bind(PosHomeController.class);
        bind(PosSshController.class);
    }
}
