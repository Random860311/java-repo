package com.qa.lib.settings.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;

public interface IConfigFileService {
    ConfigFileDto readConfigFile(String fileName) throws Exception;
}
