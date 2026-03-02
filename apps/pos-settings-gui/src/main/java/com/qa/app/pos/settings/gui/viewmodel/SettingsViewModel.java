package com.qa.app.pos.settings.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import com.qa.lib.core.gui.viewmodel.base.NavigationViewModel;
import com.qa.lib.pos.settings.service.config.IPosConfigFileService;
import com.qa.lib.settings.gui.viewmodel.FileListViewModel;

public class SettingsViewModel extends NavigationViewModel {
    private final IPosConfigFileService posConfigFileService;

    private final FileListViewModel fileListViewModel;

    @Inject
    public SettingsViewModel(FileListViewModel fileListViewModel, IPosConfigFileService posConfigFileService, INavigationService navigationService) {
        super(navigationService);

        this.posConfigFileService = posConfigFileService;
        this.fileListViewModel = fileListViewModel;
        this.fileListViewModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("MainViewModel.selectedItemProperty(): " + newValue);
        });
        this.fileListViewModel.selectedSectionProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("MainViewModel.selectedSectionProperty(): " + newValue);
        });
    }

    public FileListViewModel getFileListViewModel() {
        return fileListViewModel;
    }

    public void loadData() {
        try {
            this.fileListViewModel.setFiles(this.posConfigFileService.getConfigFileNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
