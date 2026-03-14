package com.qa.lib.core.di;

import com.google.inject.AbstractModule;


public final class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new AsyncModule());
        install(new I18nModule());
        install(new LogModule());
    }

}
