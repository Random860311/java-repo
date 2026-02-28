package com.qa.lib.settings.gui.di;

import com.google.inject.AbstractModule;
import com.qa.lib.settings.gui.controller.FileListController;
import com.qa.lib.settings.gui.viewmodel.FileListViewModel;

public class SettingsGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FileListViewModel.class);

        bind(FileListController.class);
    }
}
