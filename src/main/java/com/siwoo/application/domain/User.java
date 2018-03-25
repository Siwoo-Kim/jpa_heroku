package com.siwoo.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter @Setter @ToString(exclude = "documents")
@EqualsAndHashCode(of={"id","email"})
@Entity @Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private LocalDate joinDate;

    private int point;

    public enum Status {
        GOLD(30,300,null), SILVER(15,100,GOLD), BRONZE(5,0,SILVER);
        public final int DISCOUNT;
        public final int REACHED_POINT;
        public final Status NEXT_STATUS;

        Status(int DISCOUNT, int REACHED_POINT, Status NEXT_STATUS) {
            this.DISCOUNT = DISCOUNT;
            this.REACHED_POINT = REACHED_POINT;
            this.NEXT_STATUS = NEXT_STATUS;
        }
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents = new HashSet<>();

    public void addDocument(Document document) {
        documents.add(document);
        if(document != null && document.getUser() != this) {
            document.setUser(this);
        }
    }

    public boolean upgradeStatus() {
        Status nextStatus = status.NEXT_STATUS;
        if(nextStatus != null && point >= nextStatus.REACHED_POINT) {
            this.status = nextStatus;
            return true;
        } else {
            return false;
        }
    }

}
