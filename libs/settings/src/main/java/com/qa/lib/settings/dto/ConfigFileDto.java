package com.qa.lib.settings.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigFileDto {
    private final String fileName;
    private final Map<String, Map<String, Object>> configs = new HashMap<>();

    public ConfigFileDto(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, Map<String, Object>> getConfigs() {
        return configs;
    }

    public Set<String> getSections() {
        return configs.keySet();
    }

    public Map<String, Object> getSectionData(String section) {
        return configs.get(section);
    }

    public void setSectionData(String section, Map<String, Object> data) {
        configs.put(section, data);
    }
}
