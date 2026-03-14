package com.qa.lib.settings.service.config;

import com.google.inject.Inject;
import com.qa.lib.core.qualifiers.BackgroundThread;
import com.qa.lib.settings.dto.ConfigFileDto;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class ConfigFileServiceImp implements IConfigFileService {
    private final Executor backgroundExecutor;

    @Inject
    public ConfigFileServiceImp(@BackgroundThread Executor backgroundExecutor) {
        this.backgroundExecutor = backgroundExecutor;
    }

    public ConfigFileDto readConfigFile(String fileName) {
        INIConfiguration config = new INIConfiguration();
        FileHandler fileHandler = new FileHandler(config);

        try {
            fileHandler.load(new File(fileName));
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

        ConfigFileDto configFileDto = new ConfigFileDto(fileName);
        for (String sectionName : config.getSections()) {
            SubnodeConfiguration section = config.getSection(sectionName);

            Iterator<String> keys = section.getKeys();
            Map<String, Object> items = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                items.put(key, section.getProperty(key));
            }
            configFileDto.getConfigs().put(sectionName, items);
        }
        return configFileDto;
    }

    @Override
    public List<ConfigFileDto> readConfigFile(String[] files) {
        List<ConfigFileDto> configFileDtos = new ArrayList<>();
        for (String fileName : files) {
            configFileDtos.add(readConfigFile(fileName));
        }
        return configFileDtos;
    }

    @Override
    public CompletableFuture<ConfigFileDto> readConfigFileAsync(String fileName) {
        return CompletableFuture.supplyAsync(() -> readConfigFile(fileName));
    }

    @Override
    public CompletableFuture<List<ConfigFileDto>> readConfigFileAsync(String[] files) {
        return CompletableFuture.supplyAsync(() -> readConfigFile(files), backgroundExecutor);
    }
}
