package org.pingpong.restquarkusjpa.repositorio;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.domain.Order;
import org.pingpong.restquarkusjpa.domain.Person;
import org.pingpong.restquarkusjpa.domain.Wizard;

import io.quarkus.hibernate.orm.panache.PanacheQuery;


@ApplicationScoped
public class Repositorio {

    @Inject
    RepoWizard repoWizard;

    @Inject
    RepoItem repoItem;

    @Inject
    RepoOrder repoOrder;

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

        // Reutilizar load_item(MagicalItem item)
        // Dejo la query panache para mostrar esta 
        // funcionalidad de Panache Repository

        PanacheQuery<MagicalItem> targetQuery = this.repoItem.find("name = ?1 and quality = ?2 and type = ?3", item.getName(), item.getQuality(), item.getType());

        Optional<MagicalItem> resultQuery = targetQuery.firstResultOptional();

        if (resultQuery.isPresent()) {
            this.repoItem.delete(resultQuery.get());
        }
    }

    // contenido min eval: if-else
    // reutilizar load_item load_wizard
    @Transactional
    public Optional<Order> placeOrder(String usuaria_nombre, String item_nombre) {
        Order orden = null;
        Optional<Wizard> wizard = this.repoWizard.findByIdOptional(usuaria_nombre);
        Optional<MagicalItem> item = this.repoItem.find("name = ?1", item_nombre).firstResultOptional();
        if (wizard.isPresent() && item.isPresent() 
            && ! wizard.get().getPerson().equals(Person.MUDBLOOD) ) {
            orden = new Order();
            orden.setWizard(wizard.get());
            orden.setItem(item.get());
            this.repoOrder.persist(orden);
        }
        return Optional.ofNullable(orden);
    }
}
