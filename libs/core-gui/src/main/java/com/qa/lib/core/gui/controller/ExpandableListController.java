package com.qa.lib.core.gui.controller;

import com.qa.lib.core.gui.viewmodel.explist.ExpItemViewModel;
import com.qa.lib.core.gui.viewmodel.explist.ExpListViewModel;
import com.qa.lib.core.gui.viewmodel.explist.SectionViewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

public abstract class ExpandableListController<TViewModel extends ExpListViewModel<TItem>, TItem extends ExpItemViewModel> {
    private TViewModel viewModel;

    @FXML
    protected VBox panesContainer;

    protected final ListChangeListener<ExpItemViewModel> itemListener = change -> rebuildAll();

    public ExpandableListController() {
        FXMLLoader loader = new FXMLLoader(
                ExpandableListController.class.getResource("expandable-view.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load files-expand-list-control.fxml", e);
        }
    }

    public TViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(TViewModel vm) {
        if (this.viewModel != null) {
            this.viewModel.getExpItemsVm().removeListener(itemListener);
        }

        this.viewModel = vm;

        if (vm == null) {
            panesContainer.getChildren().clear();
            return;
        }

        vm.getExpItemsVm().addListener(itemListener);
        rebuildAll();
    }

    protected void rebuildAll() {
        panesContainer.getChildren().clear();

        if (viewModel == null) return;

        for (TItem itemVm : viewModel.getExpItemsVm()) {
            panesContainer.getChildren().add(buildFilePane(itemVm));
        }
    }

    private @NonNull TitledPane buildFilePane(@NonNull TItem itemVm) {
        // Content: sections list
        ListView<SectionViewModel> sectionsList = new ListView<>();
        sectionsList.setItems(itemVm.getSections());

        // How each section row displays
        sectionsList.setCellFactory(lv -> new ListCell<SectionViewModel>() {
            @Override
            protected void updateItem(SectionViewModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitle());
            }
        });

        // Selection -> VM
        sectionsList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (viewModel == null) return;
            if (newV != null) {
                viewModel.setSelectedItem(itemVm);
                viewModel.setSelectedSection(newV);
            }
        });

        TitledPane pane = new TitledPane();
        pane.textProperty().bind(itemVm.itemNameProperty());
        pane.setContent(sectionsList);

        // Expanded sync (two-way)
        pane.setExpanded(itemVm.isExpanded());
        pane.expandedProperty().addListener((obs, was, is) -> itemVm.setExpanded(is));
        itemVm.expandedProperty().addListener((obs, was, is) -> pane.setExpanded(is));

        // Clicking header (even without selecting a section) sets selected file
        pane.setGraphic(new Label()); // keeps header layout simple
        pane.setOnMouseClicked(e -> {
            if (viewModel != null) viewModel.setSelectedItem(itemVm);
        });

        return pane;
    }

    @FXML
    protected void initialize() {
        // nothing yet
    }
}
