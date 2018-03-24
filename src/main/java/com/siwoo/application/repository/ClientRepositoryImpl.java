package com.siwoo.application.repository;

import com.siwoo.application.domain.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Client client) {
        if(client.getId() == null) {
            entityManager.persist(client);
        } else {
            entityManager.merge(client);
        }
    }

    @Override
    public Client findById(Long id) {
        return entityManager.find(Client.class,id);
    }

}
