package com.siwoo.application.domain;

import javax.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter @Setter @ToString(exclude = "documentDetail")
@EqualsAndHashCode(of={"id","title","user"})
@Entity @Table(name = "documents")
public class Document implements Comparable<Document>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDate createDate = LocalDate.now();

    private LocalDate updateDate;

    public Document() { }

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //Owner of FK
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        if(this.user != null && !user.getDocuments().contains(this)) {
            this.user.getDocuments().add(this);
        }
    }

    @OneToOne(mappedBy="document",cascade = CascadeType.ALL, orphanRemoval = true)
    DocumentDetail documentDetail;

    public void setDocumentDetail(DocumentDetail documentDetail) {
        this.documentDetail = documentDetail;
        if(this.documentDetail != null && documentDetail.getDocument() != this) {
            documentDetail.setDocument(this);
        }
    }

    @Override
    public int compareTo(Document document) {
        //recent document is must be first
        if(getId() != null && document.getId() != null) {
            return Long.compare(id,document.getId());
        } else if (getCreateDate() != null && document.getCreateDate() != null) {
            return getCreateDate().compareTo(document.getCreateDate());
        } else {
            return title.compareToIgnoreCase(document.getTitle());
        }
    }
}
