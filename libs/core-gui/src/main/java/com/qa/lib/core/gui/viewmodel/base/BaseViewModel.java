package com.qa.lib.core.gui.viewmodel.base;

import com.google.inject.Inject;
import com.qa.lib.core.IDisposable;
import com.qa.lib.core.gui.qualifiers.UiThread;
import com.qa.lib.core.qualifiers.BackgroundThread;
import com.qa.lib.core.service.i18n.II18nService;
import com.qa.lib.core.service.log.ILogService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public abstract class BaseViewModel implements IDisposable {
    @Inject
    protected ILogService logService;
    @Inject
    protected II18nService i18nService;

    @UiThread
    @Inject
    protected Executor uiExecutor;

    @BackgroundThread
    @Inject
    protected Executor backgroundExecutor;


    @Override
    public void dispose() {

    }
}
