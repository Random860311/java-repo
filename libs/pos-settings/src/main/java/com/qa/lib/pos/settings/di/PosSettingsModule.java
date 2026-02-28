package com.qa.lib.pos.settings.di;

import com.google.inject.AbstractModule;
import com.qa.lib.pos.settings.service.config.IPosConfigFileService;
import com.qa.lib.pos.settings.service.config.PosConfigFileServiceImp;

public class PosSettingsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IPosConfigFileService.class).to(PosConfigFileServiceImp.class).asEagerSingleton();
    }
}
