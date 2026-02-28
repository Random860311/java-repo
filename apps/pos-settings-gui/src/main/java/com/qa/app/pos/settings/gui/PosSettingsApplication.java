package com.qa.app.pos.settings.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.qa.app.pos.settings.gui.di.ApplicationModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PosSettingsApplication extends Application {
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    @Override
    public void init() throws Exception {
        super.init();
        injector = Guice.createInjector(new ApplicationModule());
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/view/main-view.fxml")
        );

        loader.setControllerFactory(injector::getInstance);

        Scene scene = new Scene(loader.load(), 1200, 800);

        stage.setTitle("CentOS Config Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}