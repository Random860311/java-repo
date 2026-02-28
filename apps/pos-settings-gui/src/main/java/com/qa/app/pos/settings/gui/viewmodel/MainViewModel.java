package com.qa.app.pos.settings.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.pos.settings.service.config.IPosConfigFileService;
import com.qa.lib.settings.gui.viewmodel.FileListViewModel;

public class MainViewModel {
    private final FileListViewModel fileListViewModel;
    private final IPosConfigFileService posConfigFileService;


    @Inject
    public MainViewModel(FileListViewModel fileListViewModel, IPosConfigFileService posConfigFileService) {
        this.posConfigFileService = posConfigFileService;
        this.fileListViewModel = fileListViewModel;

        try {
            this.fileListViewModel.setFiles(this.posConfigFileService.getConfigFileNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
