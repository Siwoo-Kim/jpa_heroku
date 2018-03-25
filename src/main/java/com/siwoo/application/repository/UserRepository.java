package com.siwoo.application.repository;

import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);


}
