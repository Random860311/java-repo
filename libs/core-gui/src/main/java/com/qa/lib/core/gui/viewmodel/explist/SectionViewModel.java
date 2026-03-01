package com.qa.lib.core.gui.viewmodel.explist;

import com.qa.lib.core.gui.utils.IHasTitle;
import com.qa.lib.core.gui.viewmodel.BaseViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SectionViewModel extends BaseViewModel implements IHasTitle {
    private final StringProperty title = new SimpleStringProperty(this, "title", "");

    public SectionViewModel(String title) { this.title.set(title); }

    public StringProperty titleProperty() { return title; }

    public String getTitle() { return title.get(); }

    public void setTitle(String value) { title.set(value); }
}
