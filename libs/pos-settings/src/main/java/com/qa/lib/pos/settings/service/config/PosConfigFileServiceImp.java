package com.qa.lib.pos.settings.service.config;

import com.google.inject.Inject;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.service.config.IConfigFileService;
import org.apache.commons.lang3.NotImplementedException;

public class PosConfigFileServiceImp implements IPosConfigFileService {
    private final IConfigFileService configFileService;

    @Inject
    public PosConfigFileServiceImp(IConfigFileService configFileService) {
        this.configFileService = configFileService;
    }

    @Override
    public String[] getConfigFileNames() {
        return new String[] {
                "/etc/pos/pos-env",
                "/etc/pos/pos.properties",
                "/etc/pos/dbsynchronizer.properties",
                "/etc/yum.repos.d/carwashcontrols.repo",
                "/etc/posupdater/posupdater-env"
        };
    }

    @Override
    public ConfigFileDto[] readAllConfigFiles() throws Exception {
        String[] configFileNames = getConfigFileNames();
        ConfigFileDto[] configFiles= new ConfigFileDto[configFileNames.length];
        for (int i = 0; i < configFileNames.length; i++) {
            configFiles[i] = configFileService.readConfigFile(configFileNames[i]);
        }
        return configFiles;
    }

    @Override
    public void convertToQa() {
        throw new NotImplementedException("Not Implemented");
    }

    @Override
    public void convertToStage() {
        throw new NotImplementedException("Not Implemented");
    }
}
