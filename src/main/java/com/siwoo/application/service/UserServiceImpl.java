package com.siwoo.application.service;

import com.siwoo.application.domain.User;
import com.siwoo.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired UserRepository userRepository;

    @Override
    public User save(User user) {
        Assert.notNull(user,"user must not null");
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        Assert.notNull(pageable,"pageable must not null");
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
