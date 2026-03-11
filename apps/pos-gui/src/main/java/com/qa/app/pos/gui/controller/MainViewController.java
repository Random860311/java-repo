package com.qa.app.pos.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.gui.service.navigation.IAppNavigationService;
import com.qa.app.pos.gui.viewmodel.MainViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainViewController extends ScreenController<MainViewModel> {
    @FXML
    private BorderPane rootPane;

    private final IAppNavigationService appNavigationService;

    @Inject
    public MainViewController(MainViewModel viewModel, IAppNavigationService appNavigationService) {
        super(viewModel);
        this.appNavigationService = appNavigationService;
    }

    @Override
    protected void initialize() {
        appNavigationService.setRootPane(rootPane);

        super.initialize();
    }
}
