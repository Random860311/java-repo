package com.qa.lib.core;

import com.google.inject.Injector;

public final class AppContext {
    private static Injector injector;
    private static boolean isProdEnv = false;

    public static void setInjector(Injector injector) {
        AppContext.injector = injector;
    }

    public static Injector getInjector() {
        return injector;
    }

    public static boolean isProdEnv() {
        return isProdEnv;
    }

    public static void setProdEnv(boolean prodEnv) {
        isProdEnv = prodEnv;
    }

    public static void inject(Object object) {
        injector.injectMembers(object);
    }
}
