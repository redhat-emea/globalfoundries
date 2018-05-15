package com.redhat.emea.globalfoundries.rest;

import com.redhat.emea.globalfoundries.CommonAPI;
import com.redhat.emea.globalfoundries.ejb.WhichAPI;
import com.redhat.emea.globalfoundries.ejb.WhichBean;
import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;
import org.apache.http.client.ClientProtocolException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URL;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class WhichServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-impl.war")
                .addClasses(WhichAPI.class, WhichBean.class, CommonAPI.class, WhichService.class, WhichApplication.class, ThisDTO.class, ThatDTO.class, OtherDTO.class, Authenticator.class, ClientProtocolException.class)
                //.addAsDirectory("WEB-INF/lib") // RESTEASY-507
                .addAsWebInfResource("test-web.xml", "web.xml")
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @InSequence(1)
    public void testThisIsAuthnAuthz(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/this";
        Client client = ClientBuilder.newClient().register(new Authenticator("thisuser", "thispassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Authenticated and Authorized", response.getStatus() == 200);
    }

    @Test
    @InSequence(2)
    public void testThisIsAuthnNotAuthz(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/this";
        Client client = ClientBuilder.newClient().register(new Authenticator("thatuser", "thatpassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Authenticated and Not Authorized", response.getStatus() == 403);
    }

    @Test
    @InSequence(3)
    public void testThisIsNotAuthn(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/this";
        Client client = ClientBuilder.newClient().register(new Authenticator("wronguser", "wrongpassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Not Authenticated", response.getStatus() == 401);
    }

    @Test
    @InSequence(4)
    public void testThatIsAuthnAuthz(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/that";
        Client client = ClientBuilder.newClient().register(new Authenticator("thatuser", "thatpassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Authenticated and Authorized", response.getStatus() == 200);
    }

    @Test
    @InSequence(5)
    public void testThatIsAuthnNotAuthz(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/this";
        Client client = ClientBuilder.newClient().register(new Authenticator("thatuser", "thatpassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Authenticated and Not Authorized", response.getStatus() == 403);
    }

    @Test
    @InSequence(6)
    public void testThatIsNotAuthn(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/that";
        Client client = ClientBuilder.newClient().register(new Authenticator("wronguser", "wrongpassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Not Authenticated", response.getStatus() == 401);
    }

    @Test
    @InSequence(7)
    public void testAnonymousPublicResource(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/other";
        Client client = ClientBuilder.newClient();
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Anonymous are allowed", response.getStatus() == 200);
    }

    @Test
    @InSequence(8)
    public void testAnonymousProtectedResource(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/this";
        Client client = ClientBuilder.newClient();
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Anonymous not allowed", response.getStatus() == 401);
    }

    @Test
    @InSequence(9)
    public void testAuthenticatedPublicResource(@ArquillianResource URL url) throws Exception {
        String uri = url.toString()+"rest/other";
        Client client = ClientBuilder.newClient().register(new Authenticator("thatuser", "thatpassword1!"));
        Response response = client.target(uri).request().buildGet().invoke();
        assertTrue("Authenticated are allowed", response.getStatus() == 200);
    }

}
