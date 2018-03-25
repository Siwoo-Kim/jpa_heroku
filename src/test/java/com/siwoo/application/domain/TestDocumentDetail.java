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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestDocumentDetail {

    @PersistenceContext
    EntityManager entityManager;

    private List<Document> docFixtures;
    private List<DocumentDetail> docDetailFixtures;

    @Before
    public void setup() {
        assertNotNull(entityManager);
        docDetailFixtures = new ArrayList<>();
        docFixtures = new ArrayList<>();
        ProvidingFixture.documentDetailFixture(docDetailFixtures);
        ProvidingFixture.documentFixture(docFixtures);
    }

    @Test
    public void testSave() {
        DocumentDetail documentDetail = docDetailFixtures.get(0);
        Document document = docFixtures.get(0);
        documentDetail.setDocument(document);
        entityManager.persist(document);

        documentDetail = entityManager.find(DocumentDetail.class,documentDetail.getId());
        log.warn(documentDetail.toString());
    }


    public static void testDocumentDetail(DocumentDetail documentDetail) {
        assertNotNull(documentDetail);
        assertTrue(StringUtils.hasText(documentDetail.getOFileName()));
        assertNotNull(documentDetail.getDocument());
        assertNotNull(documentDetail.getDataType());
        assertTrue(documentDetail.getId()==documentDetail.getDocument().getId());
    }
}
