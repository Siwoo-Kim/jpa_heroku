package com.siwoo.application.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.time.LocalDate;

@Getter @Setter @ToString(exclude = "membership")
@EqualsAndHashCode(of={"email","joinDate"})
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
            name="uniq_client_email",
            columnNames = "email"))
public class Client {

    @Id @GeneratedValue
    @Column(name="client_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    private LocalDate joinDate;

    private double points = 0.0;

    protected Client() { }

    public Client(String name, String password, String email, LocalDate joinDate) {
        this.membership = membership;
        this.name = name;
        this.password = password;
        this.email = email;
        if(joinDate == null) {
            this.joinDate = LocalDate.now();
        } else {
            this.joinDate = joinDate;
        }
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
        if(membership != null && !membership.getClients().contains(this)) {
            membership.getClients().add(this);
        }
    }

    public void removeMembership() {
        if(membership != null) {
            membership.getClients().remove(this);
        }
        membership = null;
    }

}
