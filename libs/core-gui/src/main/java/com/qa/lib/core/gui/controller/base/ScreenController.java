package com.qa.lib.core.gui.controller.base;

import com.qa.lib.core.gui.ILifeCycle;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public abstract class ScreenController<TViewModel extends ScreenViewModel> extends MvvmController<TViewModel> implements IParameterReceiver, ILifeCycle {
    @FXML
    private Button btnClose;

    protected Object navigationParameter = null;

    public ScreenController(TViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void setNavigationParameter(Object parameter) {
        navigationParameter = parameter;
        viewModel.setNavigationParameter(navigationParameter);
    }

    @Override
    protected void initialize() {
        super.initialize();

        if (btnClose != null) {
            btnClose.setOnMouseClicked(event -> viewModel.getCloseCommand().run());
        }

        viewModel.onInitialize();
    }

    @Override
    public void onStop() {
        viewModel.onStop();
    }

    @Override
    public void onResume() {
        viewModel.onResume();
    }
}
