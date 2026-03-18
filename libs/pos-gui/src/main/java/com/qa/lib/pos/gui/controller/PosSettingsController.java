package com.qa.lib.pos.gui.controller;

import com.google.inject.Inject;
import com.qa.lib.pos.gui.viewmodel.PosSettingsViewModel;
import com.qa.lib.core.gui.controller.base.ScreenController;
import com.qa.lib.settings.gui.controller.file.FileListController;
import com.qa.lib.settings.gui.controller.form.FormController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class PosSettingsController extends ScreenController<PosSettingsViewModel> {
    @FXML
    private FileListController leftFileListController;
    @FXML
    private FormController settingsFormController;
    @FXML
    private Button btnSave;

    @Inject
    public PosSettingsController(PosSettingsViewModel viewModel){
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
