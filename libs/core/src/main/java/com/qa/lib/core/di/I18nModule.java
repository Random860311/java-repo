package com.qa.lib.core.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.qa.lib.core.service.i18n.I18nBundleProvider;
import com.qa.lib.core.service.i18n.I18nServiceImp;
import com.qa.lib.core.service.i18n.II18nBundleProvider;
import com.qa.lib.core.service.i18n.II18nService;

public final class I18nModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(II18nService.class).to(I18nServiceImp.class).in(Scopes.SINGLETON);

        Multibinder<II18nBundleProvider> binder = Multibinder.newSetBinder(binder(), II18nBundleProvider.class);

        binder.addBinding().to(I18nBundleProvider.class);
    }
}
