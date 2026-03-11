package com.qa.app.pos.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.gui.viewmodel.SshViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import com.qa.lib.ssh.gui.controller.TargetJumpController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SshController extends ScreenController<SshViewModel> {
    @FXML
    private TargetJumpController targetJumpController;

    @FXML
    private Button btnConfirm;

    @Inject
    public SshController(SshViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void initialize() {
        targetJumpController.setViewModel(viewModel.getSshViewModel());

        btnConfirm.setOnAction(event -> viewModel.getConfirmCommand().run());

        super.initialize();
    }
}
