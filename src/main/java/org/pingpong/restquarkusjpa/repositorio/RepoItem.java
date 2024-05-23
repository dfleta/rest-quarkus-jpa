package org.pingpong.restquarkusjpa.repositorio;

import jakarta.enterprise.context.ApplicationScoped;

import org.pingpong.restquarkusjpa.domain.MagicalItem;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class RepoItem implements PanacheRepository<MagicalItem> {
    
}
