package com.qa.lib.core.gui.utils;

import javafx.scene.control.ListCell;

public class TitleListCell<T extends IHasTitle> extends ListCell<T> {
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        textProperty().unbind();

        if (empty || item == null) {
            setText(null);
        } else {
            textProperty().bind(item.titleProperty());
        }
    }
}
