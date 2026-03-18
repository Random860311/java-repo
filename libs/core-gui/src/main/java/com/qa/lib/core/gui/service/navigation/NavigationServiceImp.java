package com.qa.lib.core.gui.service.navigation;

import com.google.inject.Inject;
import com.qa.lib.core.gui.ILifeCycle;
import com.qa.lib.core.gui.controller.IParameterReceiver;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class NavigationServiceImp implements INavigationService {
    protected final IViewFactory viewFactory;

    private final Deque<NavState> history = new ArrayDeque<>();
    private NavState currentState;

    @Inject
    public NavigationServiceImp(IViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    private void ensureReady() {
        if (getRootPane() == null) {
            throw new IllegalStateException("NavigationService rootPane not set yet.");
        }
    }

    protected abstract BorderPane getRootPane();

    @Override
    public void navigateTo(int viewId) {
        navigateTo(viewId, null);
    }

    @Override
    public void navigateTo(int viewId, Object parameter) {
        ensureReady();

        LoadedView loadedView = viewFactory.load(viewId, parameter);
        NavState newState = new NavState(viewId, parameter, loadedView);

        // push current state to history (if any)
        if (currentState != null) {
            history.push(currentState);
        }
        Object controller = loadedView.getController();
        if (controller instanceof IParameterReceiver) {
            ((IParameterReceiver) controller).setNavigationParameter(parameter);
        }
        if (controller instanceof ILifeCycle) {
            ((ILifeCycle) controller).onResume();
        }
        getRootPane().setCenter(loadedView.getRoot());
        currentState = newState;
    }

    @Override
    public void back() {
        ensureReady();
        if (history.isEmpty()) return;

        if (currentState != null) {
            Object controller = currentState.loadedView.getController();
            if (controller instanceof ILifeCycle) {
                ((ILifeCycle) controller).onStop();
            }
        }

        NavState prev = history.pop();
        if (prev.loadedView.getController() instanceof ILifeCycle) {
            ((ILifeCycle) prev.loadedView.getController()).onResume();
        }
        getRootPane().setCenter(prev.loadedView.getRoot());
        currentState = prev;
    }

    @Override
    public boolean canGoBack() {
        return !history.isEmpty();
    }

    private static final class NavState {
        final int viewId;
        final Object parameter;
        final LoadedView loadedView;

        NavState(int viewId, Object parameter, LoadedView loadedView) {
            this.viewId = viewId;
            this.parameter = parameter;
            this.loadedView = loadedView;
        }
    }
}
