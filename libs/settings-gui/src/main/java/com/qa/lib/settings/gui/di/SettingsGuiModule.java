package com.qa.lib.settings.gui.di;

import com.google.inject.AbstractModule;
import com.qa.lib.settings.gui.controller.file.FileListController;
import com.qa.lib.settings.gui.controller.form.FormRowController;
import com.qa.lib.settings.gui.viewmodel.file.FileListViewModel;
import com.qa.lib.settings.gui.viewmodel.form.FormRowViewModel;
import com.qa.lib.settings.gui.viewmodel.form.FormViewModel;

public final class SettingsGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(FileListViewModel.class);
        bind(FormRowViewModel.class);
        bind(FormViewModel.class);

        bind(FileListController.class);
        bind(FormRowController.class);
        bind(FormRowController.class);
    }
}
