package com.qa.lib.settings.gui.viewmodel.file;


import com.qa.lib.core.gui.viewmodel.explist.ExpListViewModel;
import com.qa.lib.settings.dto.ConfigFileDto;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.stream.Collectors;


public final class FileListViewModel extends ExpListViewModel<FileItemViewModel> {

    public void setFiles(@NonNull List<ConfigFileDto> files) {
        clear();
        items.addAll(files.stream().map(FileItemViewModel::new).collect(Collectors.toList()));
    }
}