package com.siwoo.application.repository;

import com.siwoo.application.domain.Client;
import com.siwoo.application.domain.Membership;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class MembershipRepositoryImpl implements MembershipRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(Membership membership) {
        entityManager.persist(membership);
        log.warn(membership.toString());
    }

    @Override
    public Membership findByClient(Client client) {
        return entityManager
                .createNamedQuery(Membership.FIND_BY_CLIENT,Membership.class)
                .setParameter("id",client.getId())
                .getSingleResult();
    }

    @Override
    public Membership findByStatusAS(Membership.Status status) {
        return entityManager
                .createNamedQuery(Membership.FIND_WITH_ASSOCIATIONS_BY_STATUS,Membership.class)
                .setParameter("status",status)
                .getSingleResult();
    }


}
