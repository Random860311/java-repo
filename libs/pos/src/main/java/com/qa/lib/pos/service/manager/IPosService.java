package com.qa.lib.pos.service.manager;

import com.qa.lib.settings.dto.ConfigFileDto;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public interface IPosService {
    String[] getConfigFileNames();
    List<ConfigFileDto> readConfigFiles();

    void writeConfigFile(ConfigFileDto configFileDto);

    default void writeConfigFile(@NonNull List<ConfigFileDto> configFilesDto)  {
        for (ConfigFileDto configFileDto : configFilesDto) {
            writeConfigFile(configFileDto);
        }
    }

    void convertToQa();
    void convertToStage();


}
