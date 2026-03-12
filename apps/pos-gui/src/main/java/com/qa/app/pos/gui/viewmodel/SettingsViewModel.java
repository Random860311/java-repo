package com.qa.app.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.service.config.IPosConfigFileService;
import com.qa.lib.settings.gui.viewmodel.file.FileListViewModel;
import com.qa.lib.settings.gui.viewmodel.form.FormViewModel;

public class SettingsViewModel extends ScreenViewModel {
    private final IPosConfigFileService posConfigFileService;

    private final FileListViewModel fileListViewModel;
    private final FormViewModel settingsFormViewModel;

    @Inject
    public SettingsViewModel(
            FileListViewModel fileListViewModel,
            FormViewModel settingsFormViewModel,
            IPosConfigFileService posConfigFileService,
            INavigationService navigationService
    ) {
        super(navigationService);

        this.posConfigFileService = posConfigFileService;
        this.fileListViewModel = fileListViewModel;
        this.settingsFormViewModel = settingsFormViewModel;

        this.fileListViewModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logService.debug("MainViewModel.selectedItemProperty(): " + newValue);
            settingsFormViewModel.clearRows();
        });
        this.fileListViewModel.selectedSectionProperty().addListener((observable, oldValue, newValue) -> {
            logService.debug("MainViewModel.selectedSectionProperty(): " + newValue);
            settingsFormViewModel.setRows(newValue.getData());
        });
    }

    public FileListViewModel getFileListViewModel() {
        return fileListViewModel;
    }

    public FormViewModel getSettingsFormViewModel() { return settingsFormViewModel; }

    @Override
    public void onInitialize() {
        super.onInitialize();

        try {
            this.fileListViewModel.setFiles(this.posConfigFileService.getConfigFileNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
