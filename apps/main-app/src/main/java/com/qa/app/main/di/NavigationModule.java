package com.qa.app.main.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.qa.app.main.service.navigation.AppViewResolver;
import com.qa.lib.core.gui.service.navigation.IViewResolver;

public final class NavigationModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<IViewResolver> multibinder = Multibinder.newSetBinder(binder(), IViewResolver.class);
        multibinder.addBinding().to(AppViewResolver.class);
    }
}
