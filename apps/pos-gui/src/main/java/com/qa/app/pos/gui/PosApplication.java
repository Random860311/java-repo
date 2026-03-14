package com.qa.app.pos.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.qa.app.pos.gui.di.ApplicationModule;
import com.qa.lib.core.AppContext;
import com.qa.lib.core.service.i18n.II18nService;
import com.qa.lib.ssh.service.ssh.ISshService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PosApplication extends Application {
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    private ISshService sshService;
    private II18nService i18nService;

    @Override
    public void init() throws Exception {
        super.init();
        injector = Guice.createInjector(new ApplicationModule());
        AppContext.setInjector(injector);

        sshService = injector.getInstance(ISshService.class);
        i18nService = injector.getInstance(II18nService.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/pos-main-screen.fxml"),
                i18nService.getBundle()
        );
        System.out.println(System.getProperty("user.dir"));
        loader.setControllerFactory(injector::getInstance);

//        try {
//            SshConnectionConfigDto config = new SshConnectionConfigDto(
//                    "cwc-azr-jump01.sei.local",
//                    22,
//                    "amed.vazquez@sei.local",
//                    "HM%vun7s",
//                    "cwc-stg-web02.sei.local",
//                    22,
//                    "amed.vazquez@sei.local",
//                    "HM%vun7s"
//            );
//            sshService.initAsync(config).thenAcceptAsync(v -> {
//                try{
//                    System.out.println("Executing command");
//                    String result = sshService.runCommand("tail -n 30 /var/log/backoffice/backoffice.log");
//                    System.out.println(result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        Scene scene = new Scene(loader.load(), 1200, 800);

        stage.setTitle("CentOS Config Editor");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        try {
            sshService = injector.getInstance(ISshService.class);
            sshService.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}