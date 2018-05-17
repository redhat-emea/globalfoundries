package com.redhat.emea.globalfoundries.ejb;

import com.redhat.emea.globalfoundries.CommonAPI;
import com.redhat.emea.globalfoundries.ejb.local.LocalAPI;
import com.redhat.emea.globalfoundries.ejb.remote.RemoteAPI;
import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;
import org.apache.http.client.ClientProtocolException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class WhichBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "ejb-impl.jar")
                .addClasses(LocalAPI.class, RemoteAPI.class, WhichAPI.class, WhichBean.class, CommonAPI.class, WhichBean.class, WhichAPI.class, ThisDTO.class, ThatDTO.class, OtherDTO.class, ClientProtocolException.class)
                .addAsResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private LocalAPI bean;

    @Test
    public void testThisIsNotNull() throws Exception {
        assertNotNull("This: ", bean.getThis());
    }

    @Test
    public void testThatIsNotNull() throws Exception {
        assertNotNull("That: ", bean.getThat());
    }

    @Test
    public void testOtherIsNotNull() throws Exception {
        assertNotNull("Other: ", bean.getOther());
    }

}
