package com.siwoo.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Getter @ToString
@EqualsAndHashCode(of={"city","postalCode","street"})
@Embeddable
public class Address implements Serializable{

    private String city;
    private String postalCode;
    private String street;

    protected Address(){ }

    public Address(String city, String postalCode, String street) {
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

}
