package com.qa.lib.core.gui.service.dialog;

import com.google.inject.Inject;
import com.qa.lib.core.gui.dialog.LoadingDialog;
import com.qa.lib.core.service.i18n.II18nService;

public class DialogServiceImp implements IDialogService {
    private final LoadingDialog loadingDialog;
    private final II18nService i18nService;

    @Inject
    public DialogServiceImp(II18nService i18nService, LoadingDialog loadingDialog) {
        this.i18nService = i18nService;
        this.loadingDialog = loadingDialog;

    }


    @Override
    public void showInfiniteDialog() {
        loadingDialog.show(i18nService.getString("core.gui.dialog.progress.loading"));
    }

    @Override
    public void showInfiniteDialog(String message) {
        loadingDialog.show(message);
    }

    @Override
    public void hideInfiniteDialog() {
        loadingDialog.close();
    }
}
