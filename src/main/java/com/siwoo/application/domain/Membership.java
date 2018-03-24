package com.siwoo.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uniq_membership_status",
        columnNames = "status"))
@EqualsAndHashCode(of={"status"})
@NamedQueries({
        @NamedQuery(name="Membership.find_by_client",
                    query = "select ms from Membership ms left join ms.clients c where c.id = :id "),
        @NamedQuery(name="Membership.find_with_associations_by_status",
                    query ="select distinct ms from Membership ms inner join fetch ms.clients where ms.status =:status ")
})
public class Membership {
    public static final String FIND_BY_CLIENT = "Membership.find_by_client";
    public static final String FIND_WITH_ASSOCIATIONS_BY_STATUS = "Membership.find_with_associations_by_status";

    @Id @GeneratedValue
    @Column(name =" membership_id")
    private Long id;

    public enum Status {
        BRONZE(1), SILVER(2), GOLD(3);
        private double value;
        Status(double value) {
            this.value = value;
        }
        public double getValue() {
            return value;
        }
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.BRONZE;

    private double discount;

    @OneToMany(mappedBy = "membership") //연관관계의 주인(FK없음)
    private Set<Client> clients = new HashSet<>();

    protected Membership() { };

    public Membership(Status status) {
        this.status = status;
    }

    public Membership(Status status, double discount) {
        this.status = status;
        this.discount = discount;
    }

    public boolean removeClient(Client client) {
        if(clients.contains(client)) {
            this.clients.remove(this);
            client.removeMembership();
            return true;
        } else {
            return false;
        }
    }
}
