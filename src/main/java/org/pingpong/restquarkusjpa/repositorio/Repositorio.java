package org.pingpong.restquarkusjpa.repositorio;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.domain.Wizard;

import io.quarkus.hibernate.orm.panache.PanacheQuery;


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

    public Optional<MagicalItem> loadItem(MagicalItem item) {

        return this.loadItems(item.getName())
                        .stream()
                        .filter(it -> it.getName().equals(item.getName()) 
                                      && it.getQuality() == item.getQuality() 
                                      && it.getType().equals(item.getType()))
                        .findFirst();
    }

    public List<MagicalItem> loadItems(String name) {
        return this.repoItem.list("name", name);
    }

    @Transactional
    public void createItem(String name, int quality, String type) {
        MagicalItem item = new MagicalItem(name, quality, type);
        this.repoItem.persist(item);
    }

    @Transactional
    public void createItems(List<MagicalItem> items) {
        this.repoItem.persist(items);
    }

    @Transactional
    public void deleteItem(MagicalItem item) {
        
        PanacheQuery<MagicalItem> target = this.repoItem.find("name = ?1 and quality = ?2 and type = ?3", item.getName(), item.getQuality(), item.getType());

        if (target.firstResultOptional().isPresent()) {
            this.repoItem.delete(target.firstResultOptional().get());
        }
    }
    
}
