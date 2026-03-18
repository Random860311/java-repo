package com.qa.app.pos.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.qa.lib.pos.gui.controller.HomeController;
import com.qa.app.pos.controller.MainController;
import com.qa.lib.pos.gui.controller.SettingsController;
import com.qa.app.pos.service.navigation.AppNavigationServiceImp;
import com.qa.app.pos.service.navigation.IAppNavigationService;
import com.qa.lib.pos.gui.di.PosGuiModule;
import com.qa.lib.pos.gui.viewmodel.HomeViewModel;
import com.qa.app.pos.viewmodel.MainViewModel;
import com.qa.lib.pos.gui.viewmodel.SettingsViewModel;
import com.qa.lib.core.di.CoreModule;
import com.qa.lib.core.gui.di.CoreGuiModule;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.ssh.di.SshModule;
import com.qa.lib.pos.di.PosModule;
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
        install(new PosModule());
        install(new PosGuiModule());
        install(new SshModule());
        install(new SshGuiModule());
        install(new I18nModule());
        install(new NavigationModule());
    }

    private void registerServices() {
        bind(AppNavigationServiceImp.class).in(Scopes.SINGLETON);
        bind(INavigationService.class).to(AppNavigationServiceImp.class);
        bind(IAppNavigationService.class).to(AppNavigationServiceImp.class);

    }

    private void registerViewModels() {
        bind(MainViewModel.class);

    }

    private void registerControllers() {
        bind(MainController.class);

    }

    private void registerUtils() {
//        bind(IViewResolver.class).to(ViewResolverImp.class).asEagerSingleton();

    }
}
