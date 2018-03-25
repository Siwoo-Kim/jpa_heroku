package com.siwoo.application.domain;

import com.siwoo.application.ProvidingFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestDocument {

    @PersistenceContext
    EntityManager entityManager;

    private List<Document> docFixtures;
    private List<User> userFixtures;

    @Before
    public void setup() {
        assertNotNull(entityManager);
        userFixtures = new ArrayList<>();
        docFixtures = new ArrayList<>();
        ProvidingFixture.userFixture(userFixtures);
        ProvidingFixture.documentFixture(docFixtures);
    }

    @Test
    public void testSave() {
        Document document = docFixtures.get(0);
        entityManager.persist(document);
        document = entityManager.find(Document.class,document.getId());
        testDocument(document,false);
        //log.warn(document.toString());
    }

    @Test
    public void testSetUser() {
        Document document = docFixtures.get(0);
        User user = userFixtures.get(0);
        entityManager.persist(user);
        //when
        document.setUser(user);
        //then
        assertTrue(user.getDocuments().contains(document));
        entityManager.persist(document);
        document = entityManager.find(Document.class,document.getId());
        testDocument(document,true);
        log.warn(document.toString());
    }

    public static void testDocument(Document document,boolean hasUser) {
        assertNotNull(document.getId());
        assertTrue(StringUtils.hasText(document.getTitle()));
        assertTrue(StringUtils.hasText(document.getContent()));
        if(hasUser){
            assertNotNull(document.getUser());
        }
    }
}
