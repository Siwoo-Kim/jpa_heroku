package com.siwoo.application.repository;

import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.DocumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDetailRepository extends JpaRepository<DocumentDetail,Long> {

    DocumentDetail findByDocument(Document document);

}
