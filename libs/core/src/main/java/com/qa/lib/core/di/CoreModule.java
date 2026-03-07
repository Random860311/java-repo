package com.qa.lib.core.di;

import com.google.inject.AbstractModule;

import com.google.inject.Scopes;


public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new AsyncModule());

    }

}
