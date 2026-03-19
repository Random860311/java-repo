package com.qa.lib.ssh.gui.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.qa.lib.core.gui.service.navigation.IViewResolver;
import com.qa.lib.ssh.gui.service.navigation.SshViewResolver;

public final class NavigationModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<IViewResolver> multibinder = Multibinder.newSetBinder(binder(), IViewResolver.class);
        multibinder.addBinding().to(SshViewResolver.class);
    }
}
