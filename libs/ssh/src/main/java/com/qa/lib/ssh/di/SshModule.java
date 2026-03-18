package com.qa.lib.ssh.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.qa.lib.ssh.service.ssh.ISshService;
import com.qa.lib.ssh.service.ssh.SshServiceImp;

public final class SshModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new I18nModule());

        bind(ISshService.class).to(SshServiceImp.class).in(Scopes.SINGLETON);
    }
}
