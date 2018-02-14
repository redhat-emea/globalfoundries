package com.redhat.emea.globalfoundries.rest;

import com.redhat.emea.globalfoundries.CommonAPI;
import com.redhat.emea.globalfoundries.ejb.WhichAPI;
import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Local
@Path("/")
public class WhichService implements CommonAPI {

    @EJB
    private WhichAPI bean;

    @Override
    @POST
    @Path("/this")
    public void doThis() {
        bean.doThis();
    }

    @Override
    @POST
    @Path("/that")
    public void doThat() {
        bean.doThat();
    }

    @Override
    @POST
    @Path("/other")
    public void doOther() {
        bean.doOther();
    }

    @Override
    @GET
    @Path("/this")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ThisDTO getThis() {
        return bean.getThis();
    }

    @Override
    @GET
    @Path("/that")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ThatDTO getThat() {
        return bean.getThat();
    }

    @Override
    @GET
    @Path("/other")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public OtherDTO getOther() {
        return bean.getOther();
    }
}
