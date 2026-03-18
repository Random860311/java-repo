package com.qa.lib.pos.gui.controller;

import com.google.inject.Inject;
import com.qa.lib.pos.gui.viewmodel.PosHomeViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class PosHomeController extends ScreenController<PosHomeViewModel> {
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSsh;

    @Inject
    public PosHomeController(PosHomeViewModel homeViewModel) {
        super(homeViewModel);
    }

    @Override
    protected void initialize() {
        super.initialize();

        btnSettings.setOnAction(event -> viewModel.getShowSettingsCommand().run());
        btnSsh.setOnAction(event -> viewModel.getShowSshCommand().run());
    }
}
