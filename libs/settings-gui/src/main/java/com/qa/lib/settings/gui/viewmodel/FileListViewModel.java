package com.qa.lib.settings.gui.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.viewmodel.explist.ExpListViewModel;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.service.config.IConfigFileService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;


public class FileListViewModel extends ExpListViewModel<FileItemViewModel> {
    protected final IConfigFileService configFileService;

    @Inject
    public FileListViewModel(IConfigFileService configFileService) {
        super();
        this.configFileService = configFileService;
    }

    public void setFiles(String @NonNull [] files) throws Exception {
        clear();

        for (String file : files) {
            ConfigFileDto configFileDto = configFileService.readConfigFile(file);
            expItemsVm.add(new FileItemViewModel(configFileDto));
        }
    }
}