package org.pingpong.restquarkusjpa;

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    // curl -w "\n" http://localhost:8080/items/Aged%20Brie -v
    // curl -w "\n" http://localhost:8080/items/Varita -v
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
    // curl -d '{"name": "+5 Dexterity Vest", "quality": "60", "type": "MagicalItem"}' 
    // -H "Content-Type: application/json" -X DELETE http://localhost:8080/item -v   
    public Response delete(@Valid MagicalItem item) {
        service.eliminaItem(item);
        return Response.status(Response.Status.OK).entity(service.cargaItems(item.getName())).build();
    }
}