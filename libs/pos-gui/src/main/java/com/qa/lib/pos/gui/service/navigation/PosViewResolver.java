package com.qa.lib.pos.gui.service.navigation;

import com.qa.lib.core.gui.service.navigation.IViewResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class PosViewResolver implements IViewResolver {
    private static final Map<Integer, String> viewsMap;

    static {
        Map<Integer, String> temp = new HashMap<>();
        temp.put(10, "/view/pos-main-screen.fxml");
        temp.put(PosRoutes.HOME, "/view/pos-home-screen.fxml");
        temp.put(PosRoutes.SETTINGS, "/view/pos-settings-screen.fxml");
        temp.put(PosRoutes.SSH, "/view/pos-ssh-screen.fxml");

        viewsMap = Collections.unmodifiableMap(temp);
    }

    @Override
    public String resolveView(int viewId) {
        return viewsMap.get(viewId);
    }
}
