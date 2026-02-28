package com.qa.lib.settings.dto;

import java.util.HashMap;
import java.util.Map;

public class ConfigFileDto {
    private final String fileName;
    private Map<String, Object> configs = new HashMap<>();

    public ConfigFileDto(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, Object> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, Object> configs) {
        if (configs != null)
            this.configs = configs;
        else
            this.configs = new HashMap<>();
    }
}
