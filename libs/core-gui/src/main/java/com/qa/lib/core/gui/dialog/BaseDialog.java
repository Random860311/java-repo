package com.qa.lib.core.gui.dialog;

import com.qa.lib.core.service.i18n.II18nService;

import java.util.concurrent.Executor;

public abstract class BaseDialog {
    protected final Executor uiExecutor;
    protected final II18nService i18nService;

    protected BaseDialog(Executor uiExecutor, II18nService i18nService) {
        this.uiExecutor = uiExecutor;
        this.i18nService = i18nService;
    }
}
