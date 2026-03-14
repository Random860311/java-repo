package com.qa.lib.pos.service.manager;

import com.google.inject.Inject;
import com.qa.lib.core.AppContext;
import com.qa.lib.core.exception.AppException;
import com.qa.lib.core.service.i18n.II18nService;
import com.qa.lib.core.util.Utils;
import com.qa.lib.settings.dto.ConfigFileDto;
import com.qa.lib.settings.service.config.IConfigFileService;
import com.qa.lib.ssh.service.ssh.ISshService;
import org.apache.commons.lang3.NotImplementedException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class PosServiceImp implements IPosService {
    private final IConfigFileService configFileService;
    private final ISshService sshService;
    private final II18nService i18nService;
    private final Path localConfigPath;
    private final Map<String, String> filesMap = new HashMap<>();

    @Inject
    public PosServiceImp(IConfigFileService configFileService, ISshService sshService, II18nService i18nService) {
        this.configFileService = configFileService;
        this.sshService = sshService;
        this.i18nService = i18nService;

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        localConfigPath = Paths.get(System.getProperty("user.dir"), "files", today);
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
    public List<ConfigFileDto> readConfigFiles() {

        String[] configFileNames = getConfigFileNames();

        if (!AppContext.isRunningLocally()) {
            List<String> localList = new ArrayList<>();

            if (sshService.isConnected()) throw new AppException("Ssh service not connected", i18nService.getString("pos.ssh.disconnected"));

            for (String configFileName : configFileNames) {
                String fileName = Utils.getFileName(configFileName);
                String filePath = localConfigPath.resolve(fileName).toString();
                sshService.downloadTextFile(configFileName, filePath);

                filesMap.put(filePath, configFileName);
                localList.add(filePath);
            }

            configFileNames =  localList.toArray(new String[0]);
        }


        return configFileService.readConfigFile(configFileNames);

    }

    @Override
    public void writeConfigFile(ConfigFileDto configFileDto) {
        configFileService.writeConfigFile(configFileDto);

        if (!AppContext.isRunningLocally()) {
            String localFilePath = configFileDto.getFileName();
            if (!filesMap.containsKey(localFilePath))
                throw new AppException("File: " + configFileDto.getFileName() + " not found in local list", i18nService.getString("pos.file.notfound"));

            String remoteFilePath = filesMap.get(localFilePath);
            sshService.uploadFile(remoteFilePath, localFilePath);
        }
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
