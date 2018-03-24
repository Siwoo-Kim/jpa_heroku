package com.siwoo.application.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class TestMembership {
    public static List<Client> fixtureClients;
    public static List<Membership> fixtureMemberships;

    @Before
    public void setup() {
        fixtureClients = Arrays.asList(
                new Client("client1","1234","client1@email.com",null),
                new Client("client2","1234","client2@email.com",null),
                new Client("client3","1234","client3@email.com",null),
                new Client("client4","1234","client4@email.com",null));

        fixtureMemberships = Arrays.asList(
                new Membership(Membership.Status.BRONZE,5),
                new Membership(Membership.Status.SILVER,10),
                new Membership(Membership.Status.GOLD,15));
    }

    @Test
    public void removeClient() {
        Membership membership = fixtureMemberships.get(0);
        for(Client client: fixtureClients) {
            client.setMembership(membership);
        }
        assertTrue(membership.getClients().size() == fixtureClients.size());
        membership.removeClient(fixtureClients.get(0));
        assertTrue(membership.getClients().size() == fixtureClients.size()-1);
        assertFalse( membership.getClients().contains(fixtureClients.get(0)) );
        assertTrue(fixtureClients.get(0).getMembership() == null);
    }
}
