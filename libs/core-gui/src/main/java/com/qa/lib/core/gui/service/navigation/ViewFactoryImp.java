package com.qa.lib.core.gui.service.navigation;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class ViewFactoryImp implements IViewFactory {
    private final Injector injector;
    private final IViewResolver viewResolver;

    @Inject
    public ViewFactoryImp(Injector injector, IViewResolver viewResolver) {
        this.injector = injector;
        this.viewResolver = viewResolver;
    }

    @Override
    public Parent load(int viewId, Object parameter) {
        String path = viewResolver.resolveView(viewId);
        if (path == null) {
            throw new IllegalArgumentException("No FXML mapped for " + viewId);
        }

        URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("FXML not found: " + path);
        }

        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(injector::getInstance);

        try {
            Parent root = loader.load();

            // Optional: pass navigation parameters to controller if it supports it
            Object controller = loader.getController();
            if (controller instanceof IParameterReceiver) {
                ((IParameterReceiver) controller).setNavigationParameter(parameter);
            }

            return root;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + path, e);
        }
    }
}
