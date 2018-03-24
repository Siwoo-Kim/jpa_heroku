package com.siwoo.application.repository;

import com.siwoo.application.domain.Client;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ClientRepository {
    @Transactional(readOnly = false)
    void save(Client client);
    Client findById(Long id);
}
