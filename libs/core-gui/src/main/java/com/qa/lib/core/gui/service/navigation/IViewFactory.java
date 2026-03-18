package com.qa.lib.core.gui.service.navigation;

public interface IViewFactory {
    LoadedView load(int viewId, Object parameter);
}
