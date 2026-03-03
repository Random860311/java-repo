package com.qa.lib.settings.gui.controller.form;

import com.google.inject.Inject;
import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.settings.gui.viewmodel.form.FormRowViewModel;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public final class FormRowController extends ComponentController<FormRowViewModel> {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtValue;

    @Inject
    public FormRowController(FormRowViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void setViewModel(FormRowViewModel viewModel) {
        super.setViewModel(viewModel);

        if (viewModel != null) {
            txtName.textProperty().bindBidirectional(viewModel.nameProperty());
            txtValue.textProperty().bindBidirectional(viewModel.valueProperty());
        }
    }
}
