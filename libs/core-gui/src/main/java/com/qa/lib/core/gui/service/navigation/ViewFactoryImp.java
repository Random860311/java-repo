package com.qa.lib.core.gui.service.navigation;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.service.i18n.II18nService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class ViewFactoryImp implements IViewFactory {
    private final Injector injector;
    private final Set<IViewResolver> viewResolvers;
    private final II18nService i18nService;

    @Inject
    public ViewFactoryImp(Injector injector, Set<IViewResolver> viewResolvers, II18nService i18nService) {
        this.injector = injector;
        this.viewResolvers = viewResolvers;
        this.i18nService = i18nService;
    }

    @Override
    public LoadedView load(int viewId, Object parameter) {
        String path = null;
        for (IViewResolver resolver : viewResolvers) {
            if ((path = resolver.resolveView(viewId)) != null) break;
        }

        if (path == null) {
            throw new IllegalArgumentException("No FXML mapped for " + viewId);
        }

        URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("FXML not found: " + path);
        }

        FXMLLoader loader = new FXMLLoader(
                url,
                i18nService.getBundle()
        );
        loader.setControllerFactory(injector::getInstance);

        try {
            Parent root = loader.load();

            // Optional: pass navigation parameters to controller if it supports it
            Object controller = loader.getController();
            if (controller instanceof IParameterReceiver) {
                ((IParameterReceiver) controller).setNavigationParameter(parameter);
            }

            return new LoadedView(root, controller);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + path, e);
        }
    }
}
