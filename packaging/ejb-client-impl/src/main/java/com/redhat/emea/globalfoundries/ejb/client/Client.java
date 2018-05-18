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

        final String lookup = "ejb:" + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName;
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
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        context = new InitialContext(props);
    }

    public void closeContext() throws NamingException{
        if(context != null){
            context.close();
        }
    }
}
