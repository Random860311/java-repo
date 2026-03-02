package com.qa.lib.core.gui.service.navigation;

import javafx.scene.Parent;

public interface IViewFactory {
    Parent load(int viewId, Object parameter);
}
