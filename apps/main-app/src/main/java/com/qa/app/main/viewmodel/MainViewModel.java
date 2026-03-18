package com.qa.app.main.viewmodel;

import com.google.inject.Inject;
import com.qa.lib.core.gui.component.led.ELedStatus;
import com.qa.lib.core.gui.service.dialog.IDialogService;
import com.qa.lib.core.gui.service.navigation.INavigationService;
import com.qa.lib.core.gui.viewmodel.base.ScreenViewModel;
import com.qa.lib.pos.gui.service.navigation.PosRoutes;
import com.qa.lib.ssh.service.ssh.ISshConnectionListener;
import com.qa.lib.ssh.service.ssh.ISshService;
import com.qa.lib.ssh.service.ssh.SshConnectionStatus;
import javafx.beans.property.*;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MainViewModel extends ScreenViewModel implements ISshConnectionListener {
    private final ISshService sshService;

    private final StringProperty targetHost = new SimpleStringProperty();
    private final StringProperty targetPort = new SimpleStringProperty();
    private final ObjectProperty<ELedStatus> targetStatus = new SimpleObjectProperty<>(ELedStatus.OFF);

    private final BooleanProperty jumpVisible = new SimpleBooleanProperty(false);
    private final StringProperty jumpHost = new SimpleStringProperty();
    private final StringProperty jumpPort = new SimpleStringProperty();
    private final ObjectProperty<ELedStatus> jumpStatus = new SimpleObjectProperty<>(ELedStatus.OFF);

    @Inject
    public MainViewModel(INavigationService navigationService, IDialogService dialogService, ISshService sshService) {
        super(navigationService, dialogService);

        this.sshService = sshService;
    }

    @Override
    public void onResume() {
        super.onResume();
        sshService.addConnectionListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        sshService.removeConnectionListener(this);
    }

    @Override
    public void onConnectionChange(@NonNull SshConnectionStatus status) {
        logService.debug("Connection Status: " + status);

        targetHost.set(status.getTargetStatus().getHost());
        targetPort.set(String.valueOf(status.getTargetStatus().getPort()));
        if(status.getTargetStatus().isFaulted()) {
            targetStatus.set(ELedStatus.FAULTED);
        }
        else if(status.getTargetStatus().isConnected()) {
            targetStatus.set(ELedStatus.ON);
        }
        else {
            targetStatus.set(ELedStatus.OFF);
        }

        jumpVisible.set(status.getJumpStatus() != null);
        if(status.getJumpStatus() != null) {

            jumpHost.set(status.getJumpStatus().getHost());
            jumpPort.set(String.valueOf(status.getJumpStatus().getPort()));
            if(status.getJumpStatus().isFaulted()) {
                jumpStatus.set(ELedStatus.FAULTED);
            }
            else if(status.getJumpStatus().isConnected()) {
                jumpStatus.set(ELedStatus.ON);
            }
            else {
                jumpStatus.set(ELedStatus.OFF);
            }
        }
    }

    public ISshService getSshService() {
        return sshService;
    }

    public String getTargetHost() {
        return targetHost.get();
    }

    public StringProperty targetHostProperty() {
        return targetHost;
    }

    public String getTargetPort() {
        return targetPort.get();
    }

    public StringProperty targetPortProperty() {
        return targetPort;
    }

    public ELedStatus getTargetStatus() {
        return targetStatus.get();
    }

    public ObjectProperty<ELedStatus> targetStatusProperty() {
        return targetStatus;
    }

    public boolean isJumpVisible() {
        return jumpVisible.get();
    }

    public BooleanProperty jumpVisibleProperty() {
        return jumpVisible;
    }

    public String getJumpHost() {
        return jumpHost.get();
    }

    public StringProperty jumpHostProperty() {
        return jumpHost;
    }

    public String getJumpPort() {
        return jumpPort.get();
    }

    public StringProperty jumpPortProperty() {
        return jumpPort;
    }

    public ELedStatus getJumpStatus() {
        return jumpStatus.get();
    }

    public ObjectProperty<ELedStatus> jumpStatusProperty() {
        return jumpStatus;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        navigationService.navigateTo(PosRoutes.HOME);
    }


}
