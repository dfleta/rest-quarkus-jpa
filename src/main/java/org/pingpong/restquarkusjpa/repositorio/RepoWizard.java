package org.pingpong.restquarkusjpa.repositorio;

import jakarta.enterprise.context.ApplicationScoped;

import org.pingpong.restquarkusjpa.domain.Wizard;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class RepoWizard implements PanacheRepositoryBase<Wizard, String> {
    
}
