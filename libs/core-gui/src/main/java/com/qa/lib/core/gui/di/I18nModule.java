package com.qa.lib.core.gui.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.qa.lib.core.gui.service.i18n.I18nBundleProvider;
import com.qa.lib.core.service.i18n.II18nBundleProvider;

public final class I18nModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<II18nBundleProvider> binder = Multibinder.newSetBinder(binder(), II18nBundleProvider.class);

        binder.addBinding().to(I18nBundleProvider.class);
    }
}
