package org.pingpong.restquarkusjpa;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pingpong.restquarkusjpa.domain.MagicalItem;
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

    @GET
    @Path("/item/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/item/Aged Brie -v
    // curl -w "\n" http://localhost:8080/item/Varita -v
    public Response getItem(@PathParam("name") String name) {
        Optional<MagicalItem> item = service.cargaItem(name);
        return service.cargaItem(name).isPresent()? 
            Response.status(Response.Status.OK).entity(item).build():
            Response.status(Response.Status.NOT_FOUND).build();
    }


}