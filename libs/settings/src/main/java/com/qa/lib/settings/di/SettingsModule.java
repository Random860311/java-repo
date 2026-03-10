package com.qa.lib.settings.di;

import com.google.inject.AbstractModule;
import com.qa.lib.settings.service.config.ConfigFileServiceImp;
import com.qa.lib.settings.service.config.IConfigFileService;

public final class SettingsModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(IConfigFileService.class)
                .to(ConfigFileServiceImp.class)
                .asEagerSingleton();
    }
}
