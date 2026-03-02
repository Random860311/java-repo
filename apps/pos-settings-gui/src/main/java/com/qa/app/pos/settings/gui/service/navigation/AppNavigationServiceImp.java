package com.qa.app.pos.settings.gui.service.navigation;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.navigation.IViewFactory;
import com.qa.lib.core.gui.service.navigation.NavigationServiceImp;
import javafx.scene.layout.BorderPane;

public final class AppNavigationServiceImp extends NavigationServiceImp implements IAppNavigationService {
    private BorderPane rootPane;

    @Inject
    public AppNavigationServiceImp(IViewFactory viewFactory) {
        super(viewFactory);
    }

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    @Override
    protected BorderPane getRootPane() {
        return rootPane;
    }
}
