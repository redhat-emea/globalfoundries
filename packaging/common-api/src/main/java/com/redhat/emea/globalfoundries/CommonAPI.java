package com.redhat.emea.globalfoundries;

import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;

public interface CommonAPI {

    public void doThis();
    public void doThat();
    public void doOther();

    public ThisDTO getThis();
    public ThatDTO getThat();
    public OtherDTO getOther();

}
