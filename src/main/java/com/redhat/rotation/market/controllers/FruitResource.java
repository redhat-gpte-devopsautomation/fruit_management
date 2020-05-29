package com.redhat.rotation.market.controllers;

import com.redhat.rotation.market.model.Fruit;
import com.redhat.rotation.market.service.FruitService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("market/fruits")
public class FruitResource {

    @Inject
    FruitService fruitService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createFruit(Fruit fruit) {
        fruitService.createFruit(fruit);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruit> findAllFruits() {
        return fruitService.findAllFruits();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findFruitById(@PathParam("id") long id) {
        Optional<Fruit> found = fruitService.findFruitById(id);
        return found.isPresent() ? ok(found.get()).build() : status(NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFruitById(@PathParam("id") long id, Fruit fruit) {
        Optional<Fruit> found = fruitService.updateFruitById(id, fruit);
        return found.isPresent() ? ok(found.get()).build() : status(NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFruitById(@PathParam("id") long id) {
        return fruitService.deleteFruitById(id) ? noContent().build() : status(NOT_FOUND).build();
    }
}
