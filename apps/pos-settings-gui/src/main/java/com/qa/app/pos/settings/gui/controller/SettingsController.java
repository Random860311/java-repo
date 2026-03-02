package com.qa.app.pos.settings.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.viewmodel.MainViewModel;
import com.qa.app.pos.settings.gui.viewmodel.SettingsViewModel;
import com.qa.lib.core.gui.controller.BaseController;
import com.qa.lib.settings.gui.controller.FileListController;
import javafx.fxml.FXML;

public class SettingsController extends BaseController {
    @FXML
    private FileListController leftFileListController;

    private final SettingsViewModel viewModel;

    @Inject
    public SettingsController(SettingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void initialize() {
        super.initialize();

        leftFileListController.setViewModel(viewModel.getFileListViewModel());

        viewModel.loadData();
    }
}
