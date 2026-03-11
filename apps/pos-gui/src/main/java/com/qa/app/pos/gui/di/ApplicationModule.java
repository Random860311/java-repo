package com.qa.app.pos.gui.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.qa.app.pos.gui.controller.HomeController;
import com.qa.app.pos.gui.controller.MainViewController;
import com.qa.app.pos.gui.controller.SettingsController;
import com.qa.app.pos.gui.service.navigation.AppNavigationServiceImp;
import com.qa.app.pos.gui.service.navigation.IAppNavigationService;
import com.qa.app.pos.gui.service.navigation.ViewResolverImp;
import com.qa.app.pos.gui.viewmodel.HomeViewModel;
import com.qa.app.pos.gui.viewmodel.MainViewModel;
import com.qa.app.pos.gui.viewmodel.SettingsViewModel;
import com.qa.lib.core.di.CoreModule;
import com.qa.lib.core.gui.di.CoreGuiModule;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.service.navigation.IViewResolver;
import com.qa.lib.ssh.di.SshModule;
import com.qa.lib.pos.di.PosSettingsModule;
import com.qa.lib.settings.di.SettingsModule;
import com.qa.lib.settings.gui.di.SettingsGuiModule;
import com.qa.lib.ssh.gui.di.SshGuiModule;

public final class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        installDependencies();
        registerServices();
        registerViewModels();
        registerControllers();
        registerUtils();
    }

    private void installDependencies() {
        install(new CoreModule());
        install(new CoreGuiModule());
        install(new SettingsModule());
        install(new SettingsGuiModule());
        install(new PosSettingsModule());
        install(new SshModule());
        install(new SshGuiModule());
        install(new I18nModule());
    }

    private void registerServices() {
        bind(AppNavigationServiceImp.class).in(Scopes.SINGLETON);
        bind(INavigationService.class).to(AppNavigationServiceImp.class);
        bind(IAppNavigationService.class).to(AppNavigationServiceImp.class);

    }

    private void registerViewModels() {
        bind(MainViewModel.class);
        bind(SettingsViewModel.class);
        bind(HomeViewModel.class);
    }

    private void registerControllers() {
        bind(MainViewController.class);
        bind(SettingsController.class);
        bind(HomeController.class);
    }

    private void registerUtils() {
        bind(IViewResolver.class).to(ViewResolverImp.class).asEagerSingleton();

    }
}
