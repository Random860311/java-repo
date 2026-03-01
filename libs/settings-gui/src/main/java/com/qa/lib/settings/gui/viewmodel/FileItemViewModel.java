package com.qa.lib.settings.gui.viewmodel;

import com.qa.lib.core.gui.viewmodel.explist.ExpItemViewModel;
import com.qa.lib.core.gui.viewmodel.explist.SectionViewModel;
import com.qa.lib.settings.dto.ConfigFileDto;
import javafx.collections.ObservableList;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class FileItemViewModel extends ExpItemViewModel {
    private ConfigFileDto configFileDto;

    public FileItemViewModel(@NonNull ConfigFileDto file) {
        super(file.getFileName());
        setConfigFileDto(file);
    }

    public ConfigFileDto getConfigFileDto() {
        return configFileDto;
    }

    public void setConfigFileDto(@NonNull ConfigFileDto configFileDto) {
        this.configFileDto = configFileDto;
        sections.clear();

        for(String section : configFileDto.getConfigs().keySet()) {
            sections.add(new SectionViewModel(section));
        }
    }

    @Override
    public ObservableList<SectionViewModel> getSections() {
        return super.getSections();
    }
}
