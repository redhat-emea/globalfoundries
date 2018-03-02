package com.redhat.emea.globalfoundries.service;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Singer {

    public void create(final com.redhat.emea.globalfoundries.entity.Singer singer);
    public com.redhat.emea.globalfoundries.entity.Singer find(final com.redhat.emea.globalfoundries.entity.Singer singer);
    public void update(final com.redhat.emea.globalfoundries.entity.Singer singer);
    public void delete(final com.redhat.emea.globalfoundries.entity.Singer singer);
    public List<com.redhat.emea.globalfoundries.entity.Singer> findByName(final String name);

}
