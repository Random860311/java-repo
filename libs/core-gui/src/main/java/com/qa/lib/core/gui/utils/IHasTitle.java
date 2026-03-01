package com.qa.lib.core.gui.utils;

import javafx.beans.property.StringProperty;

public interface IHasTitle {
    StringProperty titleProperty();

    String getTitle();

    void setTitle(String value);
}
