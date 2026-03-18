package com.qa.lib.pos.gui.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.qa.lib.core.gui.service.navigation.IViewResolver;
import com.qa.lib.pos.gui.service.navigation.PosViewResolver;

public final class NavigationModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<IViewResolver> multibinder = Multibinder.newSetBinder(binder(), IViewResolver.class);
        multibinder.addBinding().to(PosViewResolver.class);
    }
}
