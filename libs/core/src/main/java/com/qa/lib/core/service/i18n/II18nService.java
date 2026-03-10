package com.qa.lib.core.service.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public interface II18nService {
    Locale getLocale();

    void setLocale(Locale locale);

    ResourceBundle getBundle();

    String getString(String key);
}
