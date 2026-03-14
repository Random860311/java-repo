package com.qa.lib.core;

import com.google.inject.Injector;

public final class AppContext {
    private static Injector injector;
    private static boolean prodEnv = false;
    private static boolean runningLocally = true;

    public static void setInjector(Injector injector) {
        AppContext.injector = injector;
    }

    public static Injector getInjector() {
        return injector;
    }

    public static void inject(Object object) {
        injector.injectMembers(object);
    }

    public static boolean isProdEnv() {
        return prodEnv;
    }

    public static void setProdEnv(boolean prodEnv) {
        AppContext.prodEnv = prodEnv;
    }

    public static boolean isRunningLocally() {
        return runningLocally;
    }

    public static void setRunningLocally(boolean runningLocally) {
        AppContext.runningLocally = runningLocally;
    }


}
