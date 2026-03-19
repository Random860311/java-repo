package com.qa.app.main.controller;

import com.google.inject.Inject;
import com.qa.app.main.viewmodel.HomeViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class HomeController extends ScreenController<HomeViewModel> {
    @FXML
    private Button btnPos;
    @FXML
    private Button btnSsh;

    @Inject
    public HomeController(HomeViewModel viewModel) {
        super(viewModel);
    }

    @FXML
    public void initialize() {
        btnPos.setOnMouseClicked(event -> viewModel.getShowPosCommand().run());
        btnSsh.setOnAction(event -> viewModel.getShowSshCommand().run());
    }
}
