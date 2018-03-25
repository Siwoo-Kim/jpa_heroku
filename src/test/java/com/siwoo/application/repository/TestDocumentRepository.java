package com.siwoo.application.repository;


import com.siwoo.application.ProvidingFixture;
import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.DocumentDetail;
import com.siwoo.application.domain.TestDocument;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestDocumentRepository {

    @Autowired DocumentRepository documentRepository;
    @Autowired UserRepository userRepository;

    private List<Document> docFixtures;
    private List<User> userFixtures;

    @Before
    public void setup() {
        docFixtures = new ArrayList<>();
        userFixtures = new ArrayList<>();
        ProvidingFixture.documentFixture(docFixtures);
        ProvidingFixture.userFixture(userFixtures);
    }

    @Test
    public void testFindByUser() {
        User user = userFixtures.get(0);
        userRepository.save(user);
        for(Document document : docFixtures) {
            document.setUser(user);
            documentRepository.save(document);
        }

        List<Document> documents = documentRepository.findByUser(user);
        assertTrue(documents.size() == docFixtures.size());
        for(Document document : documents) {
            TestDocument.testDocument(document,true);
            log.warn(document.toString());
        }

    }



}
