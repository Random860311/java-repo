package com.qa.lib.core.gui.controller.dialog;

import com.qa.lib.core.gui.controller.base.DialogController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public final class LoadingDialogController extends DialogController {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label lblMessage;

    public void setMessage(String message) {
        lblMessage.setText(message);
    }
}
