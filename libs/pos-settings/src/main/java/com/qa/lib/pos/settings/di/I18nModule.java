package com.qa.lib.pos.settings.di;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.qa.lib.core.service.i18n.II18nBundleProvider;
import com.qa.lib.pos.settings.service.i18n.I18nBundleProvider;

public final class I18nModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<II18nBundleProvider> binder = Multibinder.newSetBinder(binder(), II18nBundleProvider.class);

        binder.addBinding().to(I18nBundleProvider.class);
    }
}
