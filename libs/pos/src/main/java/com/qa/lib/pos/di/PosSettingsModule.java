package com.qa.lib.pos.di;

import com.google.inject.AbstractModule;
import com.qa.lib.pos.service.config.IPosConfigFileService;
import com.qa.lib.pos.service.config.PosConfigFileServiceImp;

public final class PosSettingsModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(IPosConfigFileService.class).to(PosConfigFileServiceImp.class).asEagerSingleton();
    }
}
