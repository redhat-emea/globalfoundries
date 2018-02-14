package com.redhat.emea.globalfoundries.ejb;

import com.redhat.emea.globalfoundries.model.OtherDTO;
import com.redhat.emea.globalfoundries.model.ThatDTO;
import com.redhat.emea.globalfoundries.model.ThisDTO;

import javax.ejb.Stateless;

@Stateless
public class WhichBean implements WhichAPI {

    @Override
    public void doThis() {
        System.out.println("I'm doing this...");
    }

    @Override
    public void doThat() {
        System.out.println("I'm doing that...");
    }

    @Override
    public void doOther() {
        System.out.println("I'm doing other...");
    }

    @Override
    public ThisDTO getThis() {
        System.out.println("Take this...");
        return new ThisDTO("It's this");
    }

    @Override
    public ThatDTO getThat() {
        System.out.println("Take that...");
        return new ThatDTO("It's that");
    }

    @Override
    public OtherDTO getOther() {
        System.out.println("Take other...");
        return new OtherDTO("It's other");
    }
}
