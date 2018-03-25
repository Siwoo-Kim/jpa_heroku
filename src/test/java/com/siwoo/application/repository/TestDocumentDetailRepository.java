package com.siwoo.application.repository;

import com.siwoo.application.ProvidingFixture;
import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.DocumentDetail;
import com.siwoo.application.domain.TestDocumentDetail;
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
public class TestDocumentDetailRepository {

    @Autowired DocumentDetailRepository documentDetailRepository;
    @Autowired DocumentRepository documentRepository;

    private List<Document> docFixtures;
    private List<DocumentDetail> docDetailFixtures;

    @Before
    public void setup() {
        docFixtures = new ArrayList<>();
        docDetailFixtures = new ArrayList<>();
        ProvidingFixture.documentFixture(docFixtures);
        ProvidingFixture.documentDetailFixture(docDetailFixtures);
    }

    @Test
    public void testFindByDocument() {
        Document document = docFixtures.get(0);
        DocumentDetail documentDetail = docDetailFixtures.get(0);

        document.setDocumentDetail(documentDetail);
        documentRepository.save(document);

        documentDetail = documentDetailRepository.findByDocument(document);
        TestDocumentDetail.testDocumentDetail(documentDetail);
    }

}
