package org.pingpong.restquarkusjpa;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    @Path("/itemcrudos")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String wellcome() {
        return "CRUD de Items!";
    }

    @GET
    @Path("/item/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/item/Aged%20Brie -v
    // curl -w "\n" http://localhost:8080/item/Varita -v
    public Response getItem(@PathParam("name") String name) {
        Optional<MagicalItem> item = service.cargaItem(name);
        return item.isPresent()? 
            Response.status(Response.Status.OK).entity(item).build():
            Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/item")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // curl -d '{"name": "Aged Brie", "quality": "50", "type": "MagicalItem"}' 
    // -H "Content-Type: application/json" -X POST http://localhost:8080/item -v
    public Response post(@Valid MagicalItem item) {
        Optional<MagicalItem> itemPersisted = service.creaItem(item);
        return itemPersisted.isPresent()?
            Response.status(Response.Status.CREATED).entity(itemPersisted.get()).build():
            Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/items/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/item/Aged%20Brie -v
    // curl -w "\n" http://localhost:8080/item/Varita -v
    public Response getItems(@PathParam("name") String name) {
        List<MagicalItem> items = service.cargaItems(name);
        return items.isEmpty()? 
        Response.status(Response.Status.NOT_FOUND).build():
        Response.status(Response.Status.OK).entity(items).build();
    }
    
    @DELETE
    @Path("/item/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // curl -d '{"name": "Aged Brie", "quality": "50", "type": "MagicalItem"}' 
    // -H "Content-Type: application/json" -X DELETE http://localhost:8080/item -v   
    public Response delete(@Valid MagicalItem item) {
        service.eliminaItem(item);
        return Response.status(Response.Status.OK).entity(service.cargaItems(item.getName())).build();
    }
}