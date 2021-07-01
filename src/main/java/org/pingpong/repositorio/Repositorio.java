package org.pingpong.repositorio;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.domain.Wizard;

@ApplicationScoped
public class Repositorio {

    @Inject
    RepoWizard repoWizard;

    @Inject
    RepoItem repoItem;

    public Optional<Wizard> loadWizard(String name) {
        return this.repoWizard.findByIdOptional(name);
    }

    public Optional<MagicalItem> loadItem(String name) {
        return this.repoItem.find("name", name).firstResultOptional();
    }

    @Transactional
    public void createItem(String name, int quality, String type) {
        MagicalItem item = new MagicalItem(name, quality, type);
        this.repoItem.persist(item);
    }
    
}
