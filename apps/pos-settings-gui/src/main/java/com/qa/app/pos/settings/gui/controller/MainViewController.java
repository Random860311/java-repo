package com.qa.app.pos.settings.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.service.navigation.IAppNavigationService;
import com.qa.app.pos.settings.gui.viewmodel.MainViewModel;
import com.qa.lib.core.gui.controller.BaseController;
import com.qa.lib.settings.gui.controller.FileListController;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainViewController extends BaseController {
    @FXML
    private BorderPane rootPane;

    private final MainViewModel viewModel;
    private final IAppNavigationService appNavigationService;

    @Inject
    public MainViewController(MainViewModel viewModel, IAppNavigationService appNavigationService) {
        this.viewModel = viewModel;
        this.appNavigationService = appNavigationService;
    }

    @Override
    protected void initialize() {
        super.initialize();
        appNavigationService.setRootPane(rootPane);

        viewModel.onInitialize();
    }
}
