package com.qa.lib.core.gui.viewmodel.explist;

import com.qa.lib.core.gui.utils.IHasTitle;
import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class SectionViewModel extends BaseViewModel implements IHasTitle {
    private final StringProperty title = new SimpleStringProperty(this, "title", "");
    private final Map<String, Object> data;

    public SectionViewModel(String title, Map<String, Object> data) {
        this.title.set(title);
        this.data = data == null ? new HashMap<>() : new HashMap<>(data);
    }

    public StringProperty titleProperty() { return title; }

    public String getTitle() { return title.get(); }

    public void setTitle(String value) { title.set(value); }

    public Map<String, Object> getData() { return data; }
}
