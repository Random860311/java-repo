package com.qa.lib.pos.settings.di;

import com.google.inject.AbstractModule;
import com.qa.lib.pos.settings.service.config.IPosConfigFileService;
import com.qa.lib.pos.settings.service.config.PosConfigFileServiceImp;

public final class PosSettingsModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(IPosConfigFileService.class).to(PosConfigFileServiceImp.class).asEagerSingleton();
    }
}
