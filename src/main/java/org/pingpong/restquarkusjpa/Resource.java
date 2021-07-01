package org.pingpong.restquarkusjpa;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pingpong.restquarkusjpa.service.ServiceItem;

@Path("/")
public class Resource {

    @Inject
    ServiceItem service;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/itemcrudos")
    public String wellcome() {
        return "CRUD de Items!";
    }
}