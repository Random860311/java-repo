package com.qa.lib.core.gui.component.led;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class LedIndicator extends StackPane {

    @FXML
    private Circle ledCircle;

    private final ObjectProperty<ELedStatus> status =
            new SimpleObjectProperty<>(ELedStatus.OFF);

    public LedIndicator() {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/led-indicator.fxml")
        );

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load led-indicator.fxml", e);
        }

        bindProperties();
    }

    private void bindProperties() {

        ledCircle.fillProperty().bind(
                Bindings.createObjectBinding(
                        () -> buildGradient(status.get()),
                        status
                )
        );

        ledCircle.strokeProperty().bind(
                Bindings.createObjectBinding(
                        () -> buildStroke(status.get()),
                        status
                )
        );

        ledCircle.effectProperty().bind(
                Bindings.createObjectBinding(
                        () -> buildGlow(status.get()),
                        status
                )
        );
    }

    private Paint buildGradient(ELedStatus state) {

        if (state == null) {
            state = ELedStatus.OFF;
        }

        switch (state) {

            case ON:
                return new RadialGradient(
                        0, 0,
                        0.3, 0.3,
                        0.8,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.WHITE),
                        new Stop(0.2, Color.LIME),
                        new Stop(1, Color.DARKGREEN)
                );

            case FAULTED:
                return new RadialGradient(
                        0, 0,
                        0.3, 0.3,
                        0.8,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.WHITE),
                        new Stop(0.2, Color.RED),
                        new Stop(1, Color.DARKRED)
                );

            case OFF:
            default:
                return new RadialGradient(
                        0, 0,
                        0.3, 0.3,
                        0.8,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.WHITE),
                        new Stop(0.2, Color.LIGHTGRAY),
                        new Stop(1, Color.GRAY)
                );
        }
    }

    private Paint buildStroke(ELedStatus state) {

        if (state == null) {
            state = ELedStatus.OFF;
        }

        switch (state) {

            case ON:
                return Color.DARKGREEN;

            case FAULTED:
                return Color.DARKRED;

            case OFF:
            default:
                return Color.DARKGRAY;
        }
    }

    private Effect buildGlow(ELedStatus state) {

        if (state == null) {
            state = ELedStatus.OFF;
        }

        switch (state) {

            case ON:
                return glow(Color.LIMEGREEN);

            case FAULTED:
                return glow(Color.RED);

            case OFF:
            default:
                return null;
        }
    }

    private Effect glow(Color color) {

        DropShadow glow = new DropShadow();
        glow.setRadius(10);
        glow.setSpread(0.4);
        glow.setColor(color);

        return glow;
    }

    public ELedStatus getStatus() {
        return status.get();
    }

    public void setStatus(ELedStatus value) {
        status.set(value);
    }

    public ObjectProperty<ELedStatus> statusProperty() {
        return status;
    }
}
