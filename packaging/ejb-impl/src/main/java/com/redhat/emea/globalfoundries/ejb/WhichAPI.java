package com.redhat.emea.globalfoundries.ejb;

import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;

import javax.ejb.Local;

@Local
public interface WhichAPI {

    public void doThis();
    public void doThat();
    public void doOther();

    public ThisDTO getThis();
    public ThatDTO getThat();
    public OtherDTO getOther();

}
