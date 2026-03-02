package com.qa.lib.core.gui.viewmodel.explist;

import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExpItemViewModel extends BaseViewModel {
    protected final StringProperty itemName = new SimpleStringProperty(this, "itemName", "");
    protected final BooleanProperty expanded = new SimpleBooleanProperty(this, "expanded", false);
    protected final ObservableList<SectionViewModel> sections = FXCollections.observableArrayList();

    public ExpItemViewModel(String itemName) { this.itemName.set(itemName); }

    public StringProperty itemNameProperty() { return itemName; }
    public String getItemName() { return itemName.get(); }
    public void setItemName(String value) { itemName.set(value); }

    public BooleanProperty expandedProperty() { return expanded; }
    public boolean isExpanded() { return expanded.get(); }
    public void setExpanded(boolean value) { expanded.set(value); }

    public ObservableList<SectionViewModel> getSections() { return sections; }
}
