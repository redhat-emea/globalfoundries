package com.redhat.emea.globalfoundries.ejb;

import com.redhat.emea.globalfoundries.entity.Singer;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local
public class SingerBean implements SingerAPI {

    @EJB(beanName = "SingerDAO")
    private com.redhat.emea.globalfoundries.service.Singer dao;
//    @EJB(beanName = "SingerDAO",beanInterface = com.redhat.emea.globalfoundries.service.Singer.class)
//    private SingerDAO dao;

    @Override
    public void create(Singer singer) {
        dao.create(singer);
    }

    @Override
    public Singer find(Singer singer) {
        return dao.find(singer);
    }

    @Override
    public void update(Singer singer) {
        dao.update(singer);
    }

    @Override
    public void delete(Singer singer) {
        dao.delete(singer);
    }

    @Override
    public List<Singer> findByName(String name) {
        return dao.findByName(name);
    }
}
