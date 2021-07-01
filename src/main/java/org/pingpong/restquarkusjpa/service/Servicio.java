package org.pingpong.restquarkusjpa.service;

import java.util.Optional;

import org.pingpong.restquarkusjpa.domain.MagicalItem;

public interface Servicio {

    Optional<MagicalItem> cargaItem(String name);
    
}
