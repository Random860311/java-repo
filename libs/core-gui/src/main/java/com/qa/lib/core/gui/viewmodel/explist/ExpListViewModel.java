package com.qa.lib.core.gui.viewmodel.explist;

import com.qa.lib.core.gui.viewmodel.base.BaseViewModel;
import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExpListViewModel<TItem extends ExpItemViewModel> extends ComponentViewModel {
    protected final ObservableList<TItem> expItemsVm = FXCollections.observableArrayList();

    protected final ObjectProperty<TItem> selectedItem = new SimpleObjectProperty<>(this, "selectedFile");
    protected final ObjectProperty<SectionViewModel> selectedSection = new SimpleObjectProperty<>(this, "selectedSection");

    public ObservableList<TItem> getExpItemsVm() { return expItemsVm; }

    public ObjectProperty<TItem> selectedItemProperty() { return selectedItem; }
    public TItem getSelectedItem() { return selectedItem.get(); }
    public void setSelectedItem(TItem value) { selectedItem.set(value); }

    public ObjectProperty<SectionViewModel> selectedSectionProperty() { return selectedSection; }
    public SectionViewModel getSelectedSection() { return selectedSection.get(); }
    public void setSelectedSection(SectionViewModel value) { selectedSection.set(value); }

    public void clear() {
        expItemsVm.clear();
        selectedItem.set(null);
        selectedSection.set(null);
    }

}
