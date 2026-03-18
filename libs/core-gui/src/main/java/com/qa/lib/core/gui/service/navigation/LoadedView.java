package com.qa.lib.core.gui.service.navigation;

import javafx.scene.Parent;

public final class LoadedView {
    private final Parent root;
    private final Object controller;

    public LoadedView(Parent root, Object controller) {
        this.root = root;
        this.controller = controller;
    }

    public Parent getRoot() {
        return root;
    }

    public Object getController() {
        return controller;
    }
}
