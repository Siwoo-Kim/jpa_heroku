package com.siwoo.application.repository;


import com.siwoo.application.ProvidingFixture;
import com.siwoo.application.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestUserRepository {

    @Autowired UserRepository userRepository;
    private List<User> userFixture;

    @Before
    public void setup() {
        userFixture = new ArrayList<>();
        ProvidingFixture.userFixture(userFixture);
    }

    @Test
    public void testFindByEmail() {
        for(User user : userFixture) {
            userRepository.save(user);
        }

        User user = userRepository.findByEmail(userFixture.get(0).getEmail());
        assertTrue(user.equals(userFixture.get(0)));
        log.info(user.toString());
    }
}
