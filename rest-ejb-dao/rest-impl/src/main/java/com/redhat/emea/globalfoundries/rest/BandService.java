package com.redhat.emea.globalfoundries.rest;

import com.redhat.emea.globalfoundries.ejb.BandAPI;
import com.redhat.emea.globalfoundries.entity.Band;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Stateless
@Local
@Path("/band")
public class BandService {

    @EJB
    private BandAPI bean;

    @POST
    public Response create(Band band) {
        bean.create(band);
        URI uri = URI.create("/" + band.getId());
        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") Long id) {
        Band band = new Band();
        band.setId(id);
        return Response.ok(bean.find(band)).build();
    }

    @PUT
    public Response update(Band band) {
        bean.update(band);
        return Response.noContent().build();
    }

    @DELETE
    public Response delete(Band band) {
        bean.delete(band);
        return Response.accepted().build();
    }

    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findByName(@PathParam("name") String name) {
        return Response.ok(bean.findByName(name)).build();
    }

}
