package com.qa.app.pos.controller;

import com.google.inject.Inject;
import com.qa.app.pos.service.navigation.IAppNavigationService;
import com.qa.app.pos.viewmodel.MainViewModel;
import com.qa.lib.core.gui.component.led.LedIndicator;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public final class MainController extends ScreenController<MainViewModel> {
    @FXML
    private BorderPane rootPane;
    @FXML
    private LedIndicator ledTargetStatus;
    @FXML
    private Label txtTargetHost;
    @FXML
    private Label txtTargetPort;

    @FXML
    private HBox jumpContainer;
    @FXML
    private LedIndicator ledJumpStatus;
    @FXML
    private Label txtJumpHost;
    @FXML
    private Label txtJumpPort;


    private final IAppNavigationService appNavigationService;

    @Inject
    public MainController(MainViewModel viewModel, IAppNavigationService appNavigationService) {
        super(viewModel);
        this.appNavigationService = appNavigationService;
    }

    @Override
    protected void initialize() {
        appNavigationService.setRootPane(rootPane);

        ledTargetStatus.statusProperty().bind(viewModel.targetStatusProperty());
        txtTargetHost.textProperty().bind(viewModel.targetHostProperty());
        txtTargetPort.textProperty().bind(viewModel.targetPortProperty());

        jumpContainer.visibleProperty().bind(viewModel.jumpVisibleProperty());
        ledJumpStatus.statusProperty().bind(viewModel.jumpStatusProperty());
        txtJumpHost.textProperty().bind(viewModel.jumpHostProperty());
        txtJumpPort.textProperty().bind(viewModel.jumpPortProperty());

        super.initialize();
    }
}
