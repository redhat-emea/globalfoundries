package com.redhat.emea.globalfoundries.soap;

import com.redhat.emea.globalfoundries.ejb.WhichAPI;
import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.jws.WebService;

@Local
@WebService(serviceName = "WhichService", portName = "WhichService", name = "WhichService", endpointInterface = "com.redhat.emea.globalfoundries.soap.WhichService",
        targetNamespace = "http://emea.redhat.com/globalfoundries/soap/WhichService")
public class WhichEndpoint implements WhichService {

    @EJB
    private WhichAPI bean;

    @Override
    public void doThis() {
        bean.doThis();
    }

    @Override
    public void doThat() {
        bean.doThat();
    }

    @Override
    public void doOther() {
        bean.doOther();
    }

    @Override
    public ThisDTO getThis() {
        return bean.getThis();
    }

    @Override
    public ThatDTO getThat() {
        return bean.getThat();
    }

    @Override
    public OtherDTO getOther() {
        return bean.getOther();
    }
}
