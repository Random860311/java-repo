package com.qa.lib.core.gui.controller.explist;

import com.google.inject.Inject;
import com.qa.lib.core.gui.controller.base.ComponentController;
import com.qa.lib.core.gui.utils.TitleListCell;
import com.qa.lib.core.gui.viewmodel.explist.ExpItemViewModel;
import com.qa.lib.core.gui.viewmodel.explist.ExpListViewModel;
import com.qa.lib.core.gui.viewmodel.explist.SectionViewModel;
import com.qa.lib.core.service.log.ILogService;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class ExpandableListController<TViewModel extends ExpListViewModel<TItem>, TItem extends ExpItemViewModel> extends ComponentController<TViewModel> {

    @FXML
    protected VBox panesContainer;

    protected final ListChangeListener<TItem> itemListener = change -> rebuildAll();

    public void setViewModel(TViewModel viewModel) {
        super.setViewModel(viewModel);
        if (this.viewModel != null) {
            this.viewModel.getExpItemsVm().removeListener(itemListener);
        }

        this.viewModel = viewModel;

        if (this.viewModel != null) {
            this.viewModel.getExpItemsVm().addListener(itemListener);
        }

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
        sectionsList.setCellFactory(lv -> new TitleListCell<>());

        // make height computable
        sectionsList.setFixedCellSize(24); // value that matches your font/padding

        // wrap-content height
        double maxVisibleRows = 8;
        sectionsList.prefHeightProperty().bind(
                javafx.beans.binding.Bindings.min(
                        javafx.beans.binding.Bindings.size(itemVm.getSections())
                                .multiply(sectionsList.getFixedCellSize())
                                .add(2), // small padding/border fudge factor
                        maxVisibleRows * sectionsList.getFixedCellSize() + 2
                )
        );

        // Optional: avoid trying to stretch
        sectionsList.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        sectionsList.setMaxHeight(javafx.scene.layout.Region.USE_PREF_SIZE);

        // Selection -> VM
        sectionsList.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldV, sectionVm) -> {
                    if (sectionVm != null) {
                        viewModel.setSelectedItem(itemVm);
                        viewModel.setSelectedSection(sectionVm);
                        logService.debug(itemVm.getItemName() + " " + sectionVm.getTitle() + " " + sectionVm.getData());
                    }
                });

        TitledPane pane = new TitledPane();
        pane.textProperty().bind(itemVm.itemNameProperty());
        pane.setContent(sectionsList);

        // Expanded sync (two-way)
        pane.expandedProperty().bindBidirectional(itemVm.expandedProperty());


        // Clicking header (even without selecting a section) sets selected file
        pane.setGraphic(new Label()); // keeps header layout simple
        pane.setOnMouseClicked(e -> viewModel.setSelectedItem(itemVm));

        return pane;
    }

    @FXML
    protected void initialize() {
        super.initialize();
        // auto-detach when removed from scene
        panesContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                dispose();
            }
        });

        rebuildAll();
    }

    @Override
    public void dispose() {
        super.dispose();
        viewModel.getExpItemsVm().removeListener(itemListener);
    }
}
