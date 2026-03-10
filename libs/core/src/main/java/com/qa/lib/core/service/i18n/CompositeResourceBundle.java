package com.qa.lib.core.service.i18n;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public final class CompositeResourceBundle extends ResourceBundle {
    private final List<ResourceBundle> bundles;

    public CompositeResourceBundle(List<ResourceBundle> bundles) {
        if (bundles == null) {
            throw new IllegalArgumentException("bundles cannot be null");
        }

        this.bundles = new ArrayList<>(bundles);
    }

    @Override
    protected Object handleGetObject(String key) {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        }

        for (ResourceBundle bundle : bundles) {
            if (bundle.containsKey(key)) {
                return bundle.getObject(key);
            }
        }

        throw new MissingResourceException(
                "Key not found in composite bundle",
                CompositeResourceBundle.class.getName(),
                key
        );
    }

    @Override
    public @NonNull Enumeration<String> getKeys() {
        Set<String> keys = new LinkedHashSet<>();

        for (ResourceBundle bundle : bundles) {
            keys.addAll(bundle.keySet());
        }

        return Collections.enumeration(keys);
    }
}
