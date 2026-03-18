package com.qa.app.pos.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.qa.app.pos.service.navigation.AppViewResolver;
import com.qa.lib.core.gui.service.navigation.IViewResolver;

public final class NavigationModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<IViewResolver> multibinder = Multibinder.newSetBinder(binder(), IViewResolver.class);
        multibinder.addBinding().to(AppViewResolver.class);
    }
}
