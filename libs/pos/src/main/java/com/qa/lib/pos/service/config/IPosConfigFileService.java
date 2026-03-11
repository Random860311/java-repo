package com.qa.lib.pos.service.config;

import com.qa.lib.settings.dto.ConfigFileDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPosConfigFileService {
    String[] getConfigFileNames();

    CompletableFuture<Void> convertToQaAsync();
    CompletableFuture<Void> convertToStageAsync();
}
