package com.qa.lib.settings.gui.controller.form;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.core.service.i18n.II18nService;
import com.qa.lib.settings.gui.viewmodel.form.FormRowViewModel;
import com.qa.lib.settings.gui.viewmodel.form.FormViewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public final class FormController extends ComponentController<FormViewModel> {
    @FXML
    private VBox rowsBox;
    private final Injector injector;
    private final II18nService i18nService;

    private final ListChangeListener<FormRowViewModel> rowsListener = this::onRowsChangeListener;

    @Inject
    public FormController(Injector injector, II18nService i18nService) {
        this.injector = injector;
        this.i18nService = i18nService;
    }

    private void onRowsChangeListener(ListChangeListener.Change<? extends FormRowViewModel> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (FormRowViewModel vm : change.getAddedSubList()) {
                    addRowNode(vm);
                }
            }

            if (change.wasRemoved()) {
                rebuild(); // simplest safe solution
            }
        }
    }

    @Override
    public void setViewModel(FormViewModel viewModel) {
        super.setViewModel(viewModel);
        rebuild();
        viewModel.getRows().addListener(rowsListener);
    }

    @Override
    protected void initialize() {
        rowsBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                dispose();
            }
        });

        super.initialize();
    }

    private void addRowNode(FormRowViewModel vm) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/settings-form-row-view.fxml"),
                    i18nService.getBundle()
            );

            loader.setControllerFactory(injector::getInstance);

            Parent rowNode = loader.load();

           FormRowController controller = loader.getController();
           controller.setViewModel(vm);

           rowsBox.getChildren().add(rowNode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void rebuild() {
        rowsBox.getChildren().clear();
        viewModel.getRows().forEach(this::addRowNode);
    }

    @Override
    public void dispose() {
        if (viewModel != null) {
            viewModel.getRows().removeListener(rowsListener);
        }
        super.dispose();
    }
}
