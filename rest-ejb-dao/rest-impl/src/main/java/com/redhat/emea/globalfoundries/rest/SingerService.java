package com.redhat.emea.globalfoundries.rest;

import com.redhat.emea.globalfoundries.ejb.SingerAPI;
import com.redhat.emea.globalfoundries.entity.Singer;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Stateless
@Local
@Path("/singer")
public class SingerService {

    @EJB
    private SingerAPI bean;

    @POST
    public Response create(Singer singer) {
        bean.create(singer);
        URI uri = URI.create("/" + singer.getId());
        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Long id) {
        Singer singer = new Singer();
        singer.setId(id);
        return Response.ok(bean.find(singer)).build();
    }

    @PUT
    public Response update(Singer singer) {
        bean.update(singer);
        return Response.noContent().build();
    }

    @DELETE
    public Response delete(Singer singer) {
        bean.delete(singer);
        return Response.accepted().build();
    }

    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findByName(@PathParam("name") String name) {
        return Response.ok(bean.findByName(name)).build();
    }

}
