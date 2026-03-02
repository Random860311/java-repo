package com.qa.app.pos.settings.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.viewmodel.HomeViewModel;
import com.qa.lib.core.gui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController extends BaseController {
    @FXML
    private Button btnSettings;

    private final HomeViewModel homeViewModel;

    @Inject
    public HomeController(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
    }

    @Override
    protected void initialize() {
        super.initialize();

        btnSettings.setOnAction(event -> homeViewModel.getShowSettingsCommand().run());

        homeViewModel.onInitialize();
    }
}
