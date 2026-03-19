package com.qa.lib.ssh.gui.service.navigation;

import com.qa.lib.core.gui.service.navigation.IViewResolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class SshViewResolver implements IViewResolver {
    private static final Map<Integer, String> viewsMap;

    static {
        Map<Integer, String> temp = new HashMap<>();
        temp.put(SshRoutes.SSH, "/view/ssh-screen.fxml");

        viewsMap = Collections.unmodifiableMap(temp);
    }

    @Override
    public String resolveView(int viewId) {
        return viewsMap.get(viewId);
    }
}
