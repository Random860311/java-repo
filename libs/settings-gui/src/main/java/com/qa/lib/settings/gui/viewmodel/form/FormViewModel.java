package com.qa.lib.settings.gui.viewmodel.form;

import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

public final class FormViewModel extends ComponentViewModel {
    private final ObservableList<FormRowViewModel> rows = FXCollections.observableArrayList();

    public ObservableList<FormRowViewModel> getRows() {
        return rows;
    }

    public void addRow(String name, String value) {
        rows.add(new FormRowViewModel(name, value));
    }

    public void addRows(@NonNull Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            addRow(entry.getKey(), entry.getValue().toString());
        }
    }

    public void setRows(@NonNull Map<String, Object> values) {
        rows.clear();
        addRows(values);
    }

    public void clearRows() {
        rows.clear();
    }
}
