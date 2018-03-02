package com.redhat.emea.globalfoundries.service;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Band {

    public void create(final com.redhat.emea.globalfoundries.entity.Band band);
    public com.redhat.emea.globalfoundries.entity.Band find(final com.redhat.emea.globalfoundries.entity.Band band);
    public void update(final com.redhat.emea.globalfoundries.entity.Band band);
    public void delete(final com.redhat.emea.globalfoundries.entity.Band band);
    public List<com.redhat.emea.globalfoundries.entity.Band> findByName(final String name);

}
