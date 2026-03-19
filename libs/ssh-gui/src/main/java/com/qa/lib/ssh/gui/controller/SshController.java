package com.qa.lib.ssh.gui.controller;

import com.google.inject.Inject;
import com.qa.lib.ssh.gui.viewmodel.SshViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class SshController extends ScreenController<SshViewModel> {
    @FXML
    private TargetJumpController targetJumpController;

    @FXML
    private Button btnSave;

    @Inject
    public SshController(SshViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void initialize() {
        targetJumpController.setViewModel(viewModel.getSshViewModel());

        btnSave.setOnAction(event -> viewModel.getConfirmCommand().run());

        super.initialize();
    }
}
