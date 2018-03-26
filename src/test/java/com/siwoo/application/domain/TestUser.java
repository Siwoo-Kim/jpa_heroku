package com.siwoo.application.domain;

import com.siwoo.application.ProvidingFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestUser {


    @PersistenceContext
    EntityManager entityManager;

    private List<Document> docFixtures;
    private List<User> userFixtures;


    @Before
    public void setup() {
        assertNotNull(entityManager);
        docFixtures = new ArrayList<>();
        userFixtures = new ArrayList<>();
        ProvidingFixture.documentFixture(docFixtures);
        ProvidingFixture.userFixture(userFixtures);
    }

    @Test
    public void testAddDocument() {
        Document document = docFixtures.get(0);
        User user = userFixtures.get(0);
        user.addDocument(document);
        assertTrue(user.getDocuments().contains(document));
        assertTrue(document.getUser() == user);
        entityManager.persist(user);

        user = entityManager.find(User.class, user.getId());
        document = entityManager.find(Document.class, document.getId());
        TestDocument.testDocument(document,true);
        testUser(user);
        log.warn(user.toString());
        log.warn(document.toString());
    }

    @Test
    public void testUpgradeStatus() {
        int index1 = 0;
        int index3 = 3;

        userFixtures.get(index1).setStatus(User.Status.BRONZE);
        userFixtures.get(index1).setPoint(User.Status.SILVER.REACHED_POINT+1);

        userFixtures.get(index3).setStatus(User.Status.SILVER);
        userFixtures.get(index3).setPoint(User.Status.GOLD.REACHED_POINT+1);

        for(int i=0; i<=userFixtures.size()-1; i++) {
            if(i == index1 || i == index3) {
                User.Status prevStatus = userFixtures.get(i).getStatus();
                assertTrue(userFixtures.get(i).upgradeStatus());
                assertTrue(prevStatus.NEXT_STATUS == userFixtures.get(i).getStatus());
                log.warn(userFixtures.get(i).toString());
            } else {
                assertFalse(userFixtures.get(i).upgradeStatus());
            }
        }

    }


    public static void testUser(User user) {
        assertNotNull(user);
        assertTrue(StringUtils.hasText(user.getEmail()));
        assertTrue(StringUtils.hasText(user.getNickname()));
        assertTrue(StringUtils.hasText(user.getPassword()));
        assertNotNull(user.getStatus());
    }

    public static void testUser(User user1, User user2) {
        assertTrue(user1.equals(user2));
        assertEquals(user1.getPassword(),user2.getPassword());
        assertEquals(user1.getStatus(),user2.getStatus());
        assertEquals(user1.getPoint(),user2.getPoint());
    }


}
