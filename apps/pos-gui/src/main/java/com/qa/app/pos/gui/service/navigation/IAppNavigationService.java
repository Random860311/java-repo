package com.qa.app.pos.gui.service.navigation;

import com.qa.lib.core.gui.service.navigation.INavigationService;
import javafx.scene.layout.BorderPane;

public interface IAppNavigationService extends INavigationService {
    void setRootPane(BorderPane rootPane);
}
