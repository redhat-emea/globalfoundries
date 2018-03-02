package com.redhat.emea.globalfoundries.ejb;

import com.redhat.emea.globalfoundries.entity.Band;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local
public class BandBean implements BandAPI {

    @EJB(beanName = "BandDAO")
    private com.redhat.emea.globalfoundries.service.Band dao;
//    @EJB(beanName = "BandDAO",beanInterface = com.redhat.emea.globalfoundries.service.Band.class)
//    private BandDAO dao;

    @Override
    public void create(Band band) {
        dao.create(band);
    }

    @Override
    public Band find(Band band) {
        return dao.find(band);
    }

    @Override
    public void update(Band band) {
        dao.update(band);
    }

    @Override
    public void delete(Band band) {
        dao.delete(band);
    }

    @Override
    public List<Band> findByName(String name) {
        return dao.findByName(name);
    }
}
