package com.siwoo.application.service;

import com.siwoo.application.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {
    @Transactional(readOnly = false)
    User save(User user);
    Page<User> findAll(Pageable pageable);
    User findByEmail(String email);
    User findById(Long id);

}
