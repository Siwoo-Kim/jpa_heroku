package com.siwoo.application.repository;

import com.siwoo.application.domain.Client;
import com.siwoo.application.domain.Membership;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MembershipRepository {

    @Transactional(readOnly = false)
    void save(Membership membership);

    Membership findByClient(Client client);

    Membership findByStatusAS(Membership.Status status);
}
