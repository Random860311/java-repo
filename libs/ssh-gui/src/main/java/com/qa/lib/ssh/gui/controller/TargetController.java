package com.qa.lib.ssh.gui.controller;

import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.ssh.gui.viewmodel.TargetViewModel;
import javafx.fxml.FXML;

import javafx.scene.control.Button;


public final class TargetController extends ComponentController<TargetViewModel> {
    @FXML
    private SessionController targetSessionController;

    @Override
    public void setViewModel(TargetViewModel viewModel) {
        super.setViewModel(viewModel);

        if(viewModel != null) {
            targetSessionController.setViewModel(viewModel.getSessionViewModel());
        }
    }
}
