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

import static org.junit.Assert.assertEquals;
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
    private List<DocumentDetail> docDeatilFixtures;
    @Before
    public void setup() {
        docFixtures = new ArrayList<>();
        userFixtures = new ArrayList<>();
        docDeatilFixtures = new ArrayList<>();
        ProvidingFixture.documentFixture(docFixtures);
        ProvidingFixture.userFixture(userFixtures);
        ProvidingFixture.documentDetailFixture(docDeatilFixtures);

        for(User user : userFixtures) {
            userRepository.save(user);
            for(Document document : docFixtures) {
                Document newDocument = new Document(document.getTitle(),document.getContent());
                newDocument.setUser(user);
                documentRepository.save(newDocument);
            }
        }
    }

    @Test
    public void testFindByUser() {
        User user = userFixtures.get(0);
        List<Document> documents = documentRepository.findByUser(user);
        assertTrue(documents.size() == docFixtures.size());
        for(Document document : documents) {
            TestDocument.testDocument(document,true);
            log.warn(document.toString());
        }
    }

    @Test
    public void testFindCountByUser() {
        User user = userFixtures.get(0);
        int size = user.getDocuments().size();
        assertEquals(size, documentRepository.findCountByUser(user.getId()));
    }

    @Test
    public void testFindAnyDocumentDetail() {
        DocumentDetail documentDetail = docDeatilFixtures.get(0);
        Document document = docFixtures.get(0);
        docFixtures.get(0).setDocumentDetail(documentDetail);
        documentRepository.save(document);

        List<Document> documents = documentRepository.findAnyDocumentDetail();
        assertTrue(documents.contains(document));
        log.info(documents.toString());
    }



}
