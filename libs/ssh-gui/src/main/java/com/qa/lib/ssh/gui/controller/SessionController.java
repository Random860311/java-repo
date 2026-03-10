package com.qa.lib.ssh.gui.controller;

import com.google.inject.Inject;
import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.ssh.gui.viewmodel.SessionViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SessionController extends ComponentController<SessionViewModel> {
    @FXML
    private TextField txtHost;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    @Inject
    public SessionController(SessionViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void setViewModel(SessionViewModel viewModel) {
        super.setViewModel(viewModel);

        if (viewModel != null) {
            txtHost.textProperty().bindBidirectional(viewModel.hostProperty());
            txtPort.textProperty().bindBidirectional(viewModel.portProperty());
            txtUsername.textProperty().bindBidirectional(viewModel.usernameProperty());
            txtPassword.textProperty().bindBidirectional(viewModel.passwordProperty());
        }
    }
}
