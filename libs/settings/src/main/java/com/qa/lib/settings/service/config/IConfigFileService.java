package com.qa.lib.settings.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IConfigFileService {
    ConfigFileDto readConfigFile(String fileName);
    List<ConfigFileDto> readConfigFile(String[] files);

    CompletableFuture<ConfigFileDto> readConfigFileAsync(String fileName);
    CompletableFuture<List<ConfigFileDto>> readConfigFileAsync(String[] files);
}
