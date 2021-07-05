package org.pingpong.restquarkusjpa.service;

import java.util.List;
import java.util.Optional;

import org.pingpong.restquarkusjpa.domain.MagicalItem;

public interface Servicio {

    Optional<MagicalItem> cargaItem(String name);
    Optional<MagicalItem> cargaItem(MagicalItem item);
    List<MagicalItem> cargaItems(String name);
    Optional<MagicalItem> creaItem(MagicalItem item);
    void eliminaItem(MagicalItem item);
}
