package com.qa.lib.settings.gui.viewmodel.form;

import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class FormRowViewModel extends ComponentViewModel {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();

    public FormRowViewModel() {}

    public FormRowViewModel(String name, String value) {
        this.name.set(name);
        this.value.set(value);
    }

    public StringProperty nameProperty() { return name; }
    public StringProperty valueProperty() { return value; }
}
