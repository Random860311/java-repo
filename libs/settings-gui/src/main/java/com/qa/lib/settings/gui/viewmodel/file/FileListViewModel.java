package com.qa.lib.settings.gui.viewmodel.file;

import com.google.inject.Inject;
import com.qa.lib.core.gui.qualifiers.UiThread;
import com.qa.lib.core.gui.viewmodel.explist.ExpListViewModel;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.service.config.IConfigFileService;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;


public final class FileListViewModel extends ExpListViewModel<FileItemViewModel> {
    private final IConfigFileService configFileService;
    private final Executor uiExecutor;

    @Inject
    public FileListViewModel(IConfigFileService configFileService, @UiThread Executor uiExecutor) {
        super();
        this.configFileService = configFileService;
        this.uiExecutor = uiExecutor;
    }

    public void setFiles(String @NonNull [] files) throws Exception {
        clear();
        configFileService.readConfigFileAsync(files)
                .thenAcceptAsync(configFileDtos -> expItemsVm.addAll(configFileDtos.stream().map(FileItemViewModel::new).collect(Collectors.toList())), uiExecutor)
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });


    }
}