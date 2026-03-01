package com.qa.lib.settings.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.io.FileHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigFileServiceImp implements IConfigFileService {
    @Override
    public ConfigFileDto readConfigFile(String fileName) throws Exception {
        INIConfiguration config = new INIConfiguration();
        FileHandler fileHandler = new FileHandler(config);
        fileHandler.load(new File(fileName));

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
}
