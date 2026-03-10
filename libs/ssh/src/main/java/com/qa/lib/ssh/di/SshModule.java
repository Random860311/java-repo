package com.qa.lib.ssh.di;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.qa.lib.ssh.service.ISSHService;
import com.qa.lib.ssh.service.SSHServiceImp;

public class SshModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ISSHService.class).to(SSHServiceImp.class).in(Scopes.SINGLETON);
    }
}
