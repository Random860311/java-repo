package com.qa.lib.settings.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.io.FileHandler;

import java.io.File;

public class ConfigFileServiceImp implements IConfigFileService {
    @Override
    public ConfigFileDto readConfigFile(String fileName) throws Exception {
        INIConfiguration config = new INIConfiguration();
        FileHandler fileHandler = new FileHandler(config);
        fileHandler.load(new File(fileName));

        ConfigFileDto configFileDto = new ConfigFileDto(fileName);
        for (String s : configFileDto.getConfigs().keySet()) {
            configFileDto.getConfigs().put(s, configFileDto.getConfigs().get(s));
        }
        return configFileDto;
    }
}
