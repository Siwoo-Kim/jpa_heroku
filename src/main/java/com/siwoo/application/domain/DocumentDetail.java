package com.siwoo.application.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@EqualsAndHashCode(of={"document","id"})
@Entity @Table(name = "document_detail")
public class DocumentDetail {

    /*@Id @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    @Id
    private Long id;

    public enum  DataType {
        TEXT("txt"), JAVASCRIPT("js"), TYPESCRIPT("ts"), JAVA("java");
        private String extension;
        DataType(String extension) {
            this.extension = extension;
        }
        public String getExtension() {
            return extension;
        }
    }

    @Column(name = "data_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @MapsId
    @OneToOne
    @JoinColumn(name="document_id")
    private Document document;

    private String oFileName;

    private String gFileName;

    public void setDocument(Document document) {
        this.document = document;
        if(document.getDocumentDetail() != this) {
           document.setDocumentDetail(this);
        }
    }

}
