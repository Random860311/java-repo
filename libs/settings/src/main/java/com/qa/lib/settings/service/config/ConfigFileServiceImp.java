package com.qa.lib.settings.service.config;


import com.qa.lib.settings.dto.ConfigFileDto;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.*;

public final class ConfigFileServiceImp implements IConfigFileService {

    public @NonNull ConfigFileDto readConfigFile(String fileName) {
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
    public void writeConfigFile(@NonNull ConfigFileDto configFileDto) {
        INIConfiguration config = new INIConfiguration();
        FileHandler fileHandler = new FileHandler(config);

        try {
            for (Map.Entry<String, Map<String, Object>> sectionEntry : configFileDto.getConfigs().entrySet()) {
                String sectionName = sectionEntry.getKey();
                Map<String, Object> items = sectionEntry.getValue();

                for (Map.Entry<String, Object> item : items.entrySet()) {
                    config.setProperty(sectionName + "." + item.getKey(), item.getValue());
                }
            }

            fileHandler.save(new File(configFileDto.getFileName()));

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
