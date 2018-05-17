package com.redhat.emea.globalfoundries.soap;

import com.redhat.emea.globalfoundries.CommonAPI;
import com.redhat.emea.globalfoundries.ejb.WhichAPI;
import com.redhat.emea.globalfoundries.ejb.WhichBean;
import com.redhat.emea.globalfoundries.ejb.local.LocalAPI;
import com.redhat.emea.globalfoundries.ejb.remote.RemoteAPI;
import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;
import org.apache.http.client.ClientProtocolException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class WhichEndpointTest {

    /*
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "ejb-impl.jar")
                .addClasses(WhichAPI.class, WhichBean.class, CommonAPI.class, ThisDTO.class, ThatDTO.class, OtherDTO.class, Authenticator.class, ClientProtocolException.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }
    */

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "soap-impl.war")
                .addClasses(LocalAPI.class, RemoteAPI.class, WhichAPI.class, WhichBean.class, CommonAPI.class, WhichService.class, WhichEndpoint.class, ThisDTO.class, ThatDTO.class, OtherDTO.class, ClientProtocolException.class)
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }

//    @EJB
//    private WhichAPI bean;
//
//    @Test
//    public void testThisIsNotNull() throws Exception {
//        assertNotNull("This: ", bean.getThis());
//    }
//
//    @Test
//    public void testThatIsNotNull() throws Exception {
//        assertNotNull("That: ", bean.getThat());
//    }
//
//    @Test
//    public void testOtherIsNotNull() throws Exception {
//        assertNotNull("Other: ", bean.getOther());
//    }

    @Test @RunAsClient
    public void testAsClient(@ArquillianResource URL url) throws Exception {
        URL wsdl = new URL(url.toExternalForm() + "WhichService?wsdl");
        String namespaceURI = "http://emea.redhat.com/globalfoundries/soap/WhichService";
        String servicePart = "WhichService";
        QName serviceQN = new QName(namespaceURI, servicePart);

        Service service = Service.create(wsdl, serviceQN);
        WhichService whichService = service.getPort(WhichService.class);

        assertNotNull("WhichService: ", whichService);
        assertNotNull("This: ", whichService.getThis());
        assertNotNull("That: ", whichService.getThat());
        assertNotNull("Other: ", whichService.getOther());
        whichService.doThis();
        whichService.doThat();
        whichService.doOther();
    }

}
