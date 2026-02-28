package com.qa.app.pos.settings.gui.di;

import com.google.inject.AbstractModule;
import com.qa.app.pos.settings.gui.viewmodel.MainViewModel;
import com.qa.lib.core.di.CoreModule;
import com.qa.lib.core.gui.di.CoreGuiModule;
import com.qa.lib.pos.settings.di.PosSettingsModule;
import com.qa.lib.settings.di.SettingsModule;
import com.qa.lib.settings.gui.di.SettingsGuiModule;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new CoreModule());
        install(new CoreGuiModule());
        install(new SettingsModule());
        install(new SettingsGuiModule());
        install(new PosSettingsModule());

        bind(MainViewModel.class);
    }
}
