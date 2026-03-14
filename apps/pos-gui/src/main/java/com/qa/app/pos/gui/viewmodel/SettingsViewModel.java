package com.qa.app.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.service.config.IPosConfigFileService;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.gui.viewmodel.file.FileListViewModel;
import com.qa.lib.settings.gui.viewmodel.form.FormViewModel;
import com.qa.lib.settings.service.config.IConfigFileService;

import java.util.List;
import java.util.Map;

public final class SettingsViewModel extends ScreenViewModel {
    private final IPosConfigFileService posConfigFileService;
    private final IConfigFileService configFileService;

    private final FileListViewModel fileListViewModel;
    private final FormViewModel settingsFormViewModel;

    private List<ConfigFileDto> configFiles;

    @Inject
    public SettingsViewModel(
            FileListViewModel fileListViewModel,
            FormViewModel settingsFormViewModel,
            IPosConfigFileService posConfigFileService,
            INavigationService navigationService,
            IDialogService dialogService,
            IConfigFileService configFileService
    ) {
        super(navigationService, dialogService);

        this.posConfigFileService = posConfigFileService;
        this.fileListViewModel = fileListViewModel;
        this.settingsFormViewModel = settingsFormViewModel;
        this.configFileService = configFileService;

        this.fileListViewModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logService.debug("File Selected: " + newValue.getItemName());
            settingsFormViewModel.clearRows();
        });
        this.fileListViewModel.selectedSectionProperty().addListener((observable, oldValue, newValue) -> {
            logService.debug("Section selected: " + (newValue != null ? newValue.getTitle() : ""));
            if(newValue != null) {
                settingsFormViewModel.setRows(fileListViewModel.getSelectedItem().getItemName(), newValue.getTitle(), newValue.getData());
            }
            else {
                settingsFormViewModel.clearRows();
            }
        });
    }

    public FileListViewModel getFileListViewModel() {
        return fileListViewModel;
    }

    public FormViewModel getSettingsFormViewModel() {
        return settingsFormViewModel;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        executeTask(() -> {
            String[] fileNames = posConfigFileService.getConfigFileNames();
            return configFileService.readConfigFile(fileNames);
        }, i18nService.getString("pos.gui.file.loading"), null, i18nService.getString("pos.gui.file.fail"))
                .whenCompleteAsync((result, throwable) -> {
                    if (throwable == null) {
                        configFiles = result;
                        this.fileListViewModel.setFiles(configFiles);
                    }
                }, uiExecutor);
    }

//    private void updateFileData(String fileName, )
}
