package com.qa.lib.pos.di;

import com.google.inject.AbstractModule;
import com.qa.lib.pos.service.manager.IPosService;
import com.qa.lib.pos.service.manager.PosServiceImp;

public final class PosSettingsModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(IPosService.class).to(PosServiceImp.class).asEagerSingleton();
    }
}
