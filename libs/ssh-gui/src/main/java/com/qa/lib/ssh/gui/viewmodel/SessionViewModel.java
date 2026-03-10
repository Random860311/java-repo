package com.qa.lib.ssh.gui.viewmodel;

import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionViewModel extends ComponentViewModel {
    private final StringProperty host = new SimpleStringProperty();
    private final StringProperty port = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public StringProperty hostProperty() { return host; }
    public StringProperty portProperty() { return port; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty passwordProperty() { return password; }
}
