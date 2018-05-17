package com.redhat.emea.globalfoundries.ejb.client;

import com.redhat.emea.globalfoundries.ejb.remote.RemoteAPI;
import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Client {

    private Context context = null;

    public static void main(String[] args) throws Exception {
        Client main = new Client();

        try {
            main.createInitialContext();
            RemoteAPI bean = main.lookup();
            System.out.println(bean);
            bean.doThis();
            bean.doThat();
            bean.doOther();
            ThisDTO thisDTO = bean.getThis();
            ThatDTO thatDTO = bean.getThat();
            OtherDTO otherDTO = bean.getOther();
            System.out.println(thisDTO);
            System.out.println(thatDTO);
            System.out.println(otherDTO);
        } finally {
            main.closeContext();
        }
    }


    public RemoteAPI lookup() throws NamingException {

        final String appName = "";
        final String moduleName = "rest-impl";
        final String distinctName = "";
        final String beanName = "WhichBean";
        final String viewClassName = RemoteAPI.class.getName();

        //	java:app/rest-impl/WhichBean!com.redhat.emea.globalfoundries.ejb.WhichAPI
        final String lookup = "ejb:" + appName + "/" + moduleName
        + "/" + beanName + "!" + viewClassName;
        System.out.println("lookup: " + lookup);
        final RemoteAPI bean;
        try {
            bean = (RemoteAPI) context.lookup(lookup);
            return bean;
        } catch (Throwable ee) {
            ee.printStackTrace();
        }

        return null;
    }

    public void createInitialContext() throws NamingException {
/*
        Properties prop = new Properties();
        prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        prop.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");

        prop.put("remote.connections","remote");
        prop.put("remote.connection.remote.host","127.0.0.1");
        prop.put("remote.connection.remote.port","8080");
        prop.put("remote.connection.remote.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS","false");
        prop.put("remote.connection.remote.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT","false");
        prop.put("remote.connection.remote.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS","JBOSS-LOCAL-USER");

        prop.put("remote.connection.remote.username","");
        prop.put("remote.connection.remote.password","");
        context = new InitialContext(prop);
*/
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        props.put(Context.PROVIDER_URL, String.format("%s://%s:%d", "remote+http", "localhost", 8080));
//        props.put(Context.SECURITY_PRINCIPAL, "ejbuser");
//        props.put(Context.SECURITY_CREDENTIALS, "ejbuser.1");
        context = new InitialContext(props);

    }

    public void closeContext() throws NamingException{
        if(context != null){
            context.close();
        }
    }
}
