package com.qa.lib.core.gui.service.navigation;

public interface INavigationService {
    void navigateTo(int viewId);
    void navigateTo(int viewId, Object parameter);
    void back();
    boolean canGoBack();
}
