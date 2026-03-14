package com.qa.app.pos.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.exception.AppException;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.service.manager.IPosService;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.gui.viewmodel.file.FileListViewModel;
import com.qa.lib.settings.gui.viewmodel.form.FormViewModel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class SettingsViewModel extends ScreenViewModel {
    private final Runnable saveCommand = this::onSave;

    private final IPosService posService;

    private final FileListViewModel fileListViewModel;
    private final FormViewModel settingsFormViewModel;
    private List<ConfigFileDto> configFiles;

    @Inject
    public SettingsViewModel(
            FileListViewModel fileListViewModel,
            FormViewModel settingsFormViewModel,
            IPosService posService,
            INavigationService navigationService,
            IDialogService dialogService
    ) {
        super(navigationService, dialogService);

        this.posService = posService;
        this.fileListViewModel = fileListViewModel;
        this.settingsFormViewModel = settingsFormViewModel;

        this.fileListViewModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logService.debug("File Selected: " + newValue.getItemName());
            settingsFormViewModel.clearRows();
        });
        this.fileListViewModel.selectedSectionProperty().addListener((observable, oldValue, newValue) -> {
            logService.debug("Section selected: " + (newValue != null ? newValue.getTitle() : ""));
            if (newValue != null) {
                settingsFormViewModel.setRows(fileListViewModel.getSelectedItem().getItemName(), newValue.getTitle(), newValue.getData());
            } else {
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

    public Runnable getSaveCommand() {
        return saveCommand;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        executeTask(posService::readConfigFiles, i18nService.getString("pos.gui.file.loading"), null, i18nService.getString("pos.gui.file.fail"))
                .whenCompleteAsync((result, throwable) -> {
                    if (throwable == null) {
                        configFiles = result;
                        this.fileListViewModel.setFiles(result);
                    }
                }, uiExecutor);
    }

    private void onSave() {
        dialogService.showConfirmationAsync(i18nService.getString("pos.gui.settings.save.confirm"))
                .thenCompose(aBoolean -> aBoolean ? executeTask(
                                () -> {
                                    List<ConfigFileDto> filesToSave = settingsFormViewModel.getOpenedConfigFiles();
                                    for (ConfigFileDto fileToSave : filesToSave) {
                                        ConfigFileDto oldFile = configFiles.stream()
                                                .filter(f -> StringUtils.equals(f.getFileName(), fileToSave.getFileName()))
                                                .findFirst()
                                                .orElseThrow(() -> new AppException("File not found in ViewModel " + fileToSave.getFileName(), i18nService.getString("pos.gui.settings.save.fail")));

                                        for (String oldSection : oldFile.getSections()) {
                                            if (fileToSave.getSections().contains(oldSection)) continue;
                                            fileToSave.setSectionData(oldSection, oldFile.getSectionData(oldSection));
                                        }
                                    }
                                    posService.writeConfigFile(filesToSave);
                                },
                                i18nService.getString("pos.gui.settings.save.progress"),
                                i18nService.getString("pos.gui.settings.save.ok"),
                                i18nService.getString("pos.gui.settings.save.fail")
                        ) : CompletableFuture.completedFuture(null)
                )
                .exceptionally(throwable -> {
                    logService.error(throwable.getMessage(), throwable);
                    return null;
                });
    }

//    private void updateFileData(String fileName, )
}
