package com.qa.lib.pos.settings.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;

public interface IPosConfigFileService {
    String[] getConfigFileNames();
    ConfigFileDto[] readAllConfigFiles() throws Exception;

    void convertToQa();
    void convertToStage();
}
