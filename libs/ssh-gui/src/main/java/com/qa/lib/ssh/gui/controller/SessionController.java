package com.qa.lib.ssh.gui.controller;

import com.google.inject.Inject;
import com.qa.lib.core.gui.component.textfield.LabeledTextField;
import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.ssh.gui.viewmodel.SessionViewModel;
import javafx.fxml.FXML;

public final class SessionController extends ComponentController<SessionViewModel> {
    @FXML
    private LabeledTextField txtHost;
    @FXML
    private LabeledTextField txtPort;
    @FXML
    private LabeledTextField txtUsername;
    @FXML
    private LabeledTextField txtPassword;

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

            txtHost.editableProperty().bind(viewModel.enabledProperty());
            txtPort.editableProperty().bind(viewModel.enabledProperty());
            txtUsername.editableProperty().bind(viewModel.enabledProperty());
            txtPassword.editableProperty().bind(viewModel.enabledProperty());

            txtHost.disableProperty().bind(viewModel.enabledProperty().not());
            txtPort.disableProperty().bind(viewModel.enabledProperty().not());
            txtUsername.disableProperty().bind(viewModel.enabledProperty().not());
            txtPassword.disableProperty().bind(viewModel.enabledProperty().not());
        }
    }
}
