package com.qa.lib.settings.gui.controller;
import com.google.inject.Inject;
import com.qa.lib.core.gui.controller.ExpandableListController;
import com.qa.lib.settings.gui.viewmodel.FileItemViewModel;
import com.qa.lib.settings.gui.viewmodel.FileListViewModel;
import javafx.fxml.FXML;

public class FileListController extends ExpandableListController<FileListViewModel, FileItemViewModel> {

    @Inject
    public FileListController(FileListViewModel viewModel) {
        super(viewModel);
    }

}
