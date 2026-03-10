package com.qa.lib.core.service.i18n;

import com.google.inject.Inject;

import java.util.*;

public final class I18nServiceImp implements II18nService {
    private final Set<II18nBundleProvider> bundleProviders;
    private Locale locale;

    @Inject
    public I18nServiceImp(Set<II18nBundleProvider> bundleProviders) {
        this.bundleProviders = bundleProviders;
        this.locale = Locale.getDefault();//Locale.ENGLISH;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("locale cannot be null");
        }

        this.locale = locale;
    }

    @Override
    public ResourceBundle getBundle() {
        List<ResourceBundle> bundles = new ArrayList<>();

        for (II18nBundleProvider provider : bundleProviders) {
            bundles.add(ResourceBundle.getBundle(provider.getBundleBaseName(), locale));
        }

        return new CompositeResourceBundle(bundles);
    }

    @Override
    public String getString(String key) {
        return getBundle().getString(key);
    }
}
