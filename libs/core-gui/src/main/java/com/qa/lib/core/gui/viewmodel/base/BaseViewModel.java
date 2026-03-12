package com.qa.lib.core.gui.viewmodel.base;

import com.google.inject.Inject;
import com.qa.lib.core.IDisposable;
import com.qa.lib.core.service.log.ILogService;

public abstract class BaseViewModel implements IDisposable {
    @Inject
    protected ILogService logService;

    @Override
    public void dispose() {

    }
}
