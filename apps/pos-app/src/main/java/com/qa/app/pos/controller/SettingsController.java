package com.qa.app.pos.controller;

import com.google.inject.Inject;
import com.qa.app.pos.viewmodel.SettingsViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import com.qa.lib.settings.gui.controller.file.FileListController;
import com.qa.lib.settings.gui.controller.form.FormController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class SettingsController extends ScreenController<SettingsViewModel> {
    @FXML
    private FileListController leftFileListController;
    @FXML
    private FormController settingsFormController;
    @FXML
    private Button btnSave;

    @Inject
    public SettingsController(SettingsViewModel viewModel){
        super(viewModel);
    }

    @Override
    protected void initialize() {
        leftFileListController.setViewModel(viewModel.getFileListViewModel());
        settingsFormController.setViewModel(viewModel.getSettingsFormViewModel());
        btnSave.setOnAction(event -> viewModel.getSaveCommand().run());

        super.initialize();
    }
}
