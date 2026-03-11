package com.qa.lib.pos.service.i18n;

import com.qa.lib.core.service.i18n.II18nBundleProvider;

public final class I18nBundleProvider implements II18nBundleProvider {

    @Override
    public String getBundleBaseName() {
        return "i18n.pos-messages";
    }
}
