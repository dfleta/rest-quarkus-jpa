package org.pingpong.restquarkusjpa.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.repositorio.Repositorio;

@ApplicationScoped
public class ServiceItem implements Servicio {

    @Inject
    public Repositorio repo;

    public Optional<MagicalItem> cargaItem(String name) {
        return repo.loadItem(name);
    }
    
}
