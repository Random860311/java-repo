package com.qa.app.main.service.navigation;

import com.qa.lib.core.gui.service.navigation.IViewResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AppViewResolver implements IViewResolver {
    private static final Map<Integer, String> viewsMap;

    static {
        Map<Integer, String> temp = new HashMap<>();
        temp.put(AppRoutes.MAIN, "/view/main-screen.fxml");

        viewsMap = Collections.unmodifiableMap(temp);
    }

    @Override
    public String resolveView(int viewId) {
        return viewsMap.get(viewId);
    }
}
