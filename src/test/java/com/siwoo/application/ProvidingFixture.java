package com.siwoo.application;

import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.DocumentDetail;
import com.siwoo.application.domain.User;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProvidingFixture {

    public static void userFixture(List<User> userFixture) {
        User user = new User();
        user.setEmail("user1@email.com");
        user.setNickname("user1");
        user.setPassword("1234");
        user.setStatus(User.Status.BRONZE);
        user.setPoint(5);
        userFixture.add(user);

        user = new User();
        user.setEmail("user2@email.com");
        user.setNickname("user2");
        user.setPassword("4321");
        user.setStatus(User.Status.BRONZE);
        user.setPoint(10);
        userFixture.add(user);

        user = new User();
        user.setEmail("user3@email.com");
        user.setNickname("user3");
        user.setPassword("54311");
        user.setStatus(User.Status.BRONZE);
        user.setPoint(20);
        userFixture.add(user);

        user = new User();
        user.setEmail("user4@email.com");
        user.setNickname("user4");
        user.setPassword("1234");
        user.setStatus(User.Status.SILVER);
        user.setPoint(User.Status.SILVER.REACHED_POINT);
        userFixture.add(user);

        user = new User();
        user.setEmail("user5@email.com");
        user.setNickname("user5");
        user.setPassword("123224");
        user.setStatus(User.Status.GOLD);
        user.setPoint(User.Status.GOLD.REACHED_POINT);
        userFixture.add(user);
    }

    public static void documentFixture(List<Document> documentFixture) {
        Document document = new Document();
        document.setTitle("title1");
        document.setContent("long long content1");
        documentFixture.add(document);

        document = new Document();
        document.setTitle("title2");
        document.setContent("long long content2");
        documentFixture.add(document);
        document = new Document();
        document.setTitle("title3");
        document.setContent("long long content3");
        documentFixture.add(document);
        document = new Document();
        document.setTitle("title4");
        document.setContent("long long content4");
        documentFixture.add(document);
        document = new Document();
        document.setTitle("title5");
        document.setContent("long long content5");
        documentFixture.add(document);
    }


    public static void documentDetailFixture(List<DocumentDetail> docDetailFixtures) {
        DocumentDetail documentDetail = new DocumentDetail();
        documentDetail.setOFileName("origin1");
        documentDetail.setDataType(DocumentDetail.DataType.TEXT);
        docDetailFixtures.add(documentDetail);

        documentDetail = new DocumentDetail();
        documentDetail.setOFileName("origin2");
        documentDetail.setDataType(DocumentDetail.DataType.JAVA);
        docDetailFixtures.add(documentDetail);

        documentDetail = new DocumentDetail();
        documentDetail.setOFileName("origin3");
        documentDetail.setDataType(DocumentDetail.DataType.JAVASCRIPT);
        docDetailFixtures.add(documentDetail);

        documentDetail = new DocumentDetail();
        documentDetail.setOFileName("origin4");
        documentDetail.setDataType(DocumentDetail.DataType.TYPESCRIPT);
        docDetailFixtures.add(documentDetail);
    }
}
