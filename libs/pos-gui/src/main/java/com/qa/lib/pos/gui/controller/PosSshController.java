package com.qa.lib.pos.gui.controller;

import com.google.inject.Inject;
import com.qa.lib.ssh.gui.controller.TargetJumpController;
import com.qa.lib.pos.gui.viewmodel.PosSshViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class PosSshController extends ScreenController<PosSshViewModel> {
    @FXML
    private TargetJumpController targetJumpController;

    @FXML
    private Button btnSave;

    @Inject
    public PosSshController(PosSshViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void initialize() {
        targetJumpController.setViewModel(viewModel.getSshViewModel());

        btnSave.setOnAction(event -> viewModel.getConfirmCommand().run());

        super.initialize();
    }
}
