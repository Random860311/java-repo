package com.qa.lib.settings.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IConfigFileService {
    ConfigFileDto readConfigFile(String fileName);
    void writeConfigFile(ConfigFileDto configFileDto);

    default List<ConfigFileDto> readConfigFile(String @NonNull [] files) {
        List<ConfigFileDto> configFilesDto = new ArrayList<>();
        for (String fileName : files) {
            configFilesDto.add(readConfigFile(fileName));
        }
        return configFilesDto;
    }

    default void writeConfigFile(@NonNull List<ConfigFileDto> configFilesDto) {
        for (ConfigFileDto configFileDto : configFilesDto) {
            writeConfigFile(configFileDto);
        }
    }
}
