package org.pingpong.repositorio;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.pingpong.restquarkusjpa.domain.Wizard;

@ApplicationScoped
public class Repositorio {

    @Inject
    RepoWizard repoWizard;
    
    public Wizard loadWizard(String name) {
        return this.repoWizard.findById(name);
    }
    
}
