package com.qa.app.pos.controller;

import com.google.inject.Inject;
import com.qa.app.pos.viewmodel.HomeViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class HomeController extends ScreenController<HomeViewModel> {
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSsh;

    @Inject
    public HomeController(HomeViewModel homeViewModel) {
        super(homeViewModel);
    }

    @Override
    protected void initialize() {
        super.initialize();

        btnSettings.setOnAction(event -> viewModel.getShowSettingsCommand().run());
        btnSsh.setOnAction(event -> viewModel.getShowSshCommand().run());
    }
}
