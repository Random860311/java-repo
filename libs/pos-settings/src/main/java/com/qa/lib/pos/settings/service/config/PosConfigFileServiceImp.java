package com.qa.lib.pos.settings.service.config;

import com.google.inject.Inject;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.service.config.IConfigFileService;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PosConfigFileServiceImp implements IPosConfigFileService {
    private final IConfigFileService configFileService;

    @Inject
    public PosConfigFileServiceImp(IConfigFileService configFileService) {
        this.configFileService = configFileService;
    }

    @Override
    public String[] getConfigFileNames() {
        return new String[]{
                "C:\\Users\\rando\\Desktop\\test\\test.config",
                "C:\\Users\\rando\\Desktop\\test\\test2.config",
                "C:\\Users\\rando\\Desktop\\test\\test3.config",
                "C:\\Users\\rando\\Desktop\\test\\test4.config",
                "C:\\Users\\rando\\Desktop\\test\\test5.config",
                "C:\\Users\\rando\\Desktop\\test\\test6.config",
                "C:\\Users\\rando\\Desktop\\test\\test7.config",
                "C:\\Users\\rando\\Desktop\\test\\test8.config",
                "C:\\Users\\rando\\Desktop\\test\\test9.config",
                "C:\\Users\\rando\\Desktop\\test\\test10.config",
                "C:\\Users\\rando\\Desktop\\test\\test11.config",
//                "/etc/pos/pos-env",
//                "/etc/pos/pos.properties",
//                "/etc/pos/dbsynchronizer.properties",
//                "/etc/yum.repos.d/carwashcontrols.repo",
//                "/etc/posupdater/posupdater-env"
        };
    }

    @Override
    public CompletableFuture<Void> convertToQaAsync() {
        throw new NotImplementedException("Not Implemented");
    }

    @Override
    public CompletableFuture<Void> convertToStageAsync() {
        throw new NotImplementedException("Not Implemented");
    }
}
