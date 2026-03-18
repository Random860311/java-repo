package com.qa.app.pos.service.navigation;

import com.qa.lib.core.gui.service.navigation.IViewResolver;
import com.qa.lib.pos.gui.service.navigation.PosRoutes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AppViewResolver implements IViewResolver {
    private static final Map<Integer, String> viewsMap;

    static {
        Map<Integer, String> temp = new HashMap<>();
        temp.put(AppRoutes.MAIN, "/view/pos-main-screen.fxml");

        viewsMap = Collections.unmodifiableMap(temp);
    }

    @Override
    public String resolveView(int viewId) {
        return viewsMap.get(viewId);
    }
}
