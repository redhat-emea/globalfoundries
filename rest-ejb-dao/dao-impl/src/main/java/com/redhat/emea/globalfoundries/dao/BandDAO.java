package com.redhat.emea.globalfoundries.dao;

import com.redhat.emea.globalfoundries.service.Band;

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
public class BandDAO implements Band {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public void create(com.redhat.emea.globalfoundries.entity.Band band) {
        em.persist(band);
    }

    @Override
    public com.redhat.emea.globalfoundries.entity.Band find(com.redhat.emea.globalfoundries.entity.Band band) {
        return em.find(com.redhat.emea.globalfoundries.entity.Band.class, band.getId());
    }

    @Override
    public void update(com.redhat.emea.globalfoundries.entity.Band band) {
        em.refresh(band);
    }

    @Override
    public void delete(com.redhat.emea.globalfoundries.entity.Band band) {
        em.remove(band);
    }

    @Override
    public List<com.redhat.emea.globalfoundries.entity.Band> findByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<com.redhat.emea.globalfoundries.entity.Band> query = builder.createQuery(com.redhat.emea.globalfoundries.entity.Band.class);
        Root<com.redhat.emea.globalfoundries.entity.Band> root = query.from(com.redhat.emea.globalfoundries.entity.Band.class);

        query
                .select(root)
                .where(builder.equal(root.get("name"), name));

        return em.createQuery(query).getResultList();
    }
}
