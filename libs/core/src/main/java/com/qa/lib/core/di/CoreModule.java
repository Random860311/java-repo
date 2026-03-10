package com.qa.lib.core.di;

import com.google.inject.AbstractModule;

import com.google.inject.Scopes;


public final class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new AsyncModule());
        install(new I18nModule());
    }

}
