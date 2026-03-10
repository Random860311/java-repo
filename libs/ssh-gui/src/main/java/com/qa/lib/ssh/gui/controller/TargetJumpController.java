package com.qa.lib.ssh.gui.controller;

import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.ssh.gui.viewmodel.TargetJumpViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TargetJumpController extends ComponentController<TargetJumpViewModel> {
    @FXML
    private SessionController targetSessionController;

    @FXML
    private SessionController jumpSessionController;

    @Override
    public void setViewModel(TargetJumpViewModel viewModel) {
        super.setViewModel(viewModel);

        if (viewModel != null) {
            targetSessionController.setViewModel(viewModel.getTargetSessionViewModel());
            jumpSessionController.setViewModel(viewModel.getJumpSessionViewModel());
        }
    }
}
