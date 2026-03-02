package com.qa.lib.core.gui.service.navigation;

import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class NavigationServiceImp implements INavigationService {
    protected final IViewFactory viewFactory;

    private final Deque<NavState> history = new ArrayDeque<>();

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

        Parent view = viewFactory.load(viewId, parameter);

        // push current state to history (if any)
        if (getRootPane().getCenter() != null) {
            history.push(new NavState(getRootPane().getCenter()));
        }

        getRootPane().setCenter(view);
    }

    @Override
    public void back() {
        ensureReady();
        if (history.isEmpty()) return;

        NavState prev = history.pop();
        getRootPane().setCenter(prev.center);
    }

    @Override
    public boolean canGoBack() {
        return !history.isEmpty();
    }

    private static final class NavState {
        final javafx.scene.Node center;
        NavState(javafx.scene.Node center) { this.center = center; }
    }
}
