package com.qa.lib.core.ssh.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.qa.lib.core.ssh.service.ISSHService;
import com.qa.lib.core.ssh.service.SSHServiceImp;

public class CoreSSHModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ISSHService.class).to(SSHServiceImp.class).in(Scopes.SINGLETON);
    }
}
