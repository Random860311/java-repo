package com.qa.lib.core.gui.component;

import com.google.inject.Inject;
import com.qa.lib.core.AppContext;
import com.qa.lib.core.service.i18n.II18nService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class LabeledTextField extends HBox {
    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtValue;

    @Inject
    private II18nService i18nService;

    private final StringProperty label = new SimpleStringProperty(this, "label", "");
    private final StringProperty text = new SimpleStringProperty(this, "text", "");
    private final StringProperty promptText = new SimpleStringProperty(this, "promptText", "");
    private final BooleanProperty editable = new SimpleBooleanProperty(this, "editable", true);

    public LabeledTextField() {
        AppContext.inject(this);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/labeled-text-field.fxml"),
                i18nService.getBundle()
        );

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load labeled-text-field.fxml", ex);
        }

        bindInternalProperties();
    }

    private void bindInternalProperties() {
        lblTitle.textProperty().bind(labelProperty());
        txtValue.textProperty().bindBidirectional(textProperty());
        txtValue.promptTextProperty().bind(promptTextProperty());
        txtValue.editableProperty().bind(editableProperty());
    }

    public final StringProperty labelProperty() {
        return label;
    }

    public final String getLabel() {
        return label.get();
    }

    public final void setLabel(String value) {
        label.set(value);
    }

    public final StringProperty textProperty() {
        return text;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(String value) {
        text.set(value);
    }

    public final StringProperty promptTextProperty() {
        return promptText;
    }

    public final String getPromptText() {
        return promptText.get();
    }

    public final void setPromptText(String value) {
        promptText.set(value);
    }

    public final BooleanProperty editableProperty() {
        return editable;
    }

    public final boolean isEditable() {
        return editable.get();
    }

    public final void setEditable(boolean value) {
        editable.set(value);
    }

    public TextField getTextField() {
        return txtValue;
    }
}
