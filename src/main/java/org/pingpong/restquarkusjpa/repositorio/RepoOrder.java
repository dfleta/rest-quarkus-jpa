package org.pingpong.restquarkusjpa.repositorio;

import javax.enterprise.context.ApplicationScoped;

import org.pingpong.restquarkusjpa.domain.Order;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class RepoOrder implements PanacheRepository<Order> {
    
}