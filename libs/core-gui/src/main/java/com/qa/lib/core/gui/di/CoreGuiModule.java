package com.qa.lib.core.gui.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.qa.lib.core.gui.controller.dialog.LoadingDialogController;
import com.qa.lib.core.gui.dialog.AlertDialog;
import com.qa.lib.core.gui.dialog.LoadingDialog;
import com.qa.lib.core.gui.qualifiers.UiThread;
import com.qa.lib.core.gui.service.async.FxUiExecutor;
import com.qa.lib.core.gui.service.dialog.DialogServiceImp;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.IViewFactory;
import com.qa.lib.core.gui.service.navigation.ViewFactoryImp;

import java.util.concurrent.Executor;

public final class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(IViewFactory.class)
                .to(ViewFactoryImp.class)
                .asEagerSingleton();

        // UI executor abstraction
        bind(Executor.class)
                .annotatedWith(UiThread.class)
                .to(FxUiExecutor.class)
                .in(Scopes.SINGLETON);

        bind(LoadingDialogController.class);
        bind(LoadingDialog.class).in(Scopes.SINGLETON);

        bind(AlertDialog.class).in(Scopes.SINGLETON);

        bind(IDialogService.class).to(DialogServiceImp.class).in(Scopes.SINGLETON);
    }
}
