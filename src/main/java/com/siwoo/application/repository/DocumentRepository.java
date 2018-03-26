package com.siwoo.application.repository;

import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Long>{

    List<Document> findByUser(User user);

    @Query("select count(d) from Document d where d.user.id = :userId ")
    long findCountByUser(@Param("userId") Long userId);

    //find the Any Document who has DocumentDetail association
    @Query("select d from Document d where d.documentDetail = any( select dt from DocumentDetail dt )")
    List<Document> findAnyDocumentDetail();
}
