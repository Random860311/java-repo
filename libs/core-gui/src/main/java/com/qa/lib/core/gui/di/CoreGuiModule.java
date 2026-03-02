package com.qa.lib.core.gui.di;

import com.google.inject.AbstractModule;
import com.qa.lib.core.gui.service.navigation.IViewFactory;
import com.qa.lib.core.gui.service.navigation.ViewFactoryImp;

public class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IViewFactory.class).to(ViewFactoryImp.class).asEagerSingleton();
    }
}
