package com.redhat.emea.globalfoundries.dao;

import com.redhat.emea.globalfoundries.service.Singer;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@Local
public class SingerDAO implements Singer {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public void create(com.redhat.emea.globalfoundries.entity.Singer singer) {
        em.persist(singer);
    }

    @Override
    public com.redhat.emea.globalfoundries.entity.Singer find(com.redhat.emea.globalfoundries.entity.Singer singer) {
        return em.find(com.redhat.emea.globalfoundries.entity.Singer.class, singer.getId());
    }

    @Override
    public void update(com.redhat.emea.globalfoundries.entity.Singer singer) {
        em.refresh(singer);
    }

    @Override
    public void delete(com.redhat.emea.globalfoundries.entity.Singer singer) {
        em.remove(singer);
    }

    @Override
    public List<com.redhat.emea.globalfoundries.entity.Singer> findByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<com.redhat.emea.globalfoundries.entity.Singer> query = builder.createQuery(com.redhat.emea.globalfoundries.entity.Singer.class);
        Root<com.redhat.emea.globalfoundries.entity.Singer> root = query.from(com.redhat.emea.globalfoundries.entity.Singer.class);

        query
            .select(root)
            .where(builder.equal(root.get("name"), name));

        return em.createQuery(query).getResultList();
    }
}
