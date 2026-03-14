package com.qa.lib.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

public final class Utils {
    public static @NonNull String getFileName(@NonNull String path) {
        int slash = path.lastIndexOf('/');
        int backslash = path.lastIndexOf('\\');
        int index = Math.max(slash, backslash);
        return path.substring(index + 1);
    }
}
