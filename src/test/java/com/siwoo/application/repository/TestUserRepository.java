package com.siwoo.application.repository;


import com.siwoo.application.ProvidingFixture;
import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.TestUser;
import com.siwoo.application.domain.TestUserSummary;
import com.siwoo.application.domain.User;
import com.siwoo.application.domain.views.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

        for(User user : userFixture) {
            userRepository.save(user);
        }
    }

    @Test
    public void testFindByEmail() {
        User user = userRepository.findByEmail(userFixture.get(0).getEmail());
        assertTrue(user.equals(userFixture.get(0)));
        log.info(user.toString());
    }

    @Test
    public void testFindAllUserSummary() {
        List<UserSummary> userSummaries = userRepository.findAllUserSummary();
        assertTrue(userSummaries.size() == userFixture.size());
        for(UserSummary userSummary : userSummaries) {
            TestUserSummary.testUserSummary(userSummary);
        }
    }

    @Test
    public void testFindUserSummaryById() {
        User user = userFixture.get(0);
        UserSummary userSummary = userRepository.findUserSummaryById(user.getId());

        User summaryUser = new User();
        summaryUser.setId(user.getId());
        summaryUser.setPassword(user.getPassword());

        summaryUser.setNickname(userSummary.getNickname());
        summaryUser.setStatus(userSummary.getStatus());
        summaryUser.setEmail(userSummary.getEmail());
        summaryUser.setPoint(userSummary.getPoint());

        TestUser.testUser(summaryUser,user);
    }


    @Test
    public void testFindTopPointUser() {
        User user = userFixture.stream().filter(u -> {
            return u.getPoint() == userFixture.stream()
                    .mapToDouble(User::getPoint)
                    .max()
                    .getAsDouble();
        }).findFirst().get();
        assertTrue(user == userRepository.findTopPointUser());
    }

    @Test
    public void testFindTop5ByJoinDateEqualsOrderByIdDesc() {
        User user = new User();
        user.setEmail("testing1@email.com");
        userRepository.save(user);
        user = new User();
        user.setEmail("testing2@email.com");
        userRepository.save(user);
        user = new User();
        user.setEmail("testing3@email.com");
        userRepository.save(user);
        user = new User();
        user.setEmail("testing4@email.com");
        userRepository.save(user);
        user = new User();
        user.setEmail("testing5@email.com");
        userRepository.save(user);
        log.info(user.toString());

        List<User> users = userRepository.findTop5ByJoinDateEqualsOrderByIdDesc(LocalDate.now());

        log.info(users.toString());
    }

    @Test
    public void testFindUserWithDocument() {
        List<Document> docFixture = new ArrayList<>();
        ProvidingFixture.documentFixture(docFixture);
        ProvidingFixture.providingDocumentToUser(userFixture,docFixture);
        for(User user: userFixture) {
            userRepository.save(user);
        }

        userRepository.findUserWithDocument().stream().forEach(user -> {
            assertTrue(user.getDocuments().size() > 0);
            log.info(user.getDocuments().toString());
        });
    }

    @Test
    public void testFindNoDocumentUser() {
        List<Document> docFixture = new ArrayList<>();
        ProvidingFixture.documentFixture(docFixture);
        ProvidingFixture.providingDocumentToUser(userFixture,docFixture);
        String targetEmail = userFixture.get(0).getEmail();
        for(User user: userFixture) {
            if(user.getEmail().equals(targetEmail)) {
                user.removeAllDocument();
            }
            userRepository.save(user);
        }

        assertTrue(userFixture.get(0).getDocuments().size() == 0);
    }


}
