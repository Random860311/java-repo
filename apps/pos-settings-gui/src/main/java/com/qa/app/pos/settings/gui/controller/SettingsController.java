package com.qa.app.pos.settings.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.viewmodel.SettingsViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import com.qa.lib.settings.gui.controller.file.FileListController;
import com.qa.lib.settings.gui.controller.form.FormController;
import javafx.fxml.FXML;

public class SettingsController extends ScreenController<SettingsViewModel> {
    @FXML
    private FileListController leftFileListController;
    @FXML
    private FormController settingsFormController;

    @Inject
    public SettingsController(SettingsViewModel viewModel){
        super(viewModel);
    }

    @Override
    protected void initialize() {
        leftFileListController.setViewModel(viewModel.getFileListViewModel());
        settingsFormController.setViewModel(viewModel.getSettingsFormViewModel());

        super.initialize();
    }
}
