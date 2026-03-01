package com.qa.app.pos.settings.gui.controller;

import com.google.inject.Inject;
import com.qa.app.pos.settings.gui.viewmodel.MainViewModel;
import com.qa.lib.core.gui.controller.BaseController;
import com.qa.lib.settings.gui.controller.FileListController;
import javafx.fxml.FXML;

public class MainViewController extends BaseController {

    @FXML
    private FileListController leftFileListController;

    private final MainViewModel viewModel;

    @Inject
    public MainViewController(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void initialize() {
        super.initialize();

        leftFileListController.setViewModel(viewModel.getFileListViewModel());

        viewModel.loadData();
    }
}
