package org.pingpong.restquarkusjpa.service;

import java.util.Optional;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.repositorio.Repositorio;

@ApplicationScoped
public class ServiceItem implements Servicio {

    @Inject
    Repositorio repo;

    public Optional<MagicalItem> cargaItem(String name) {
        return repo.loadItem(name);
    }

    public List<MagicalItem> cargaItems(String name) {
        return repo.loadItems(name);
    }

    public Optional<MagicalItem> creaItem(MagicalItem item) {

        repo.createItem(item.getName(), item.getQuality(), item.getType());

        return repo.loadItem(item);
    }

    public void eliminaItem(MagicalItem item) {
        repo.deleteItem(item);
    }    
}
