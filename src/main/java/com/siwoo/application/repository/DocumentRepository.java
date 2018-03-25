package com.siwoo.application.repository;

import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Long>{

    List<Document> findByUser(User user);

}
