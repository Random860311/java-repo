package com.qa.app.pos.settings.gui.service.navigation;

import com.qa.lib.core.gui.service.navigation.IViewResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ViewResolverImp implements IViewResolver {
    private static final Map<Integer, String> viewsMap;

    static {
        Map<Integer, String> temp = new HashMap<>();
        temp.put(EViewId.MAIN.ordinal(), "/view/pos-main-screen.fxml");
        temp.put(EViewId.HOME.ordinal(), "/view/pos-home-screen.fxml");
        temp.put(EViewId.POS_SETTINGS.ordinal(), "/view/pos-settings-screen.fxml");
        temp.put(EViewId.POS_SSH.ordinal(), "/view/pos-ssh-screen.fxml");

        viewsMap = Collections.unmodifiableMap(temp);
    }

    @Override
    public String resolveView(int viewId) {
        return viewsMap.get(viewId);
    }
}
