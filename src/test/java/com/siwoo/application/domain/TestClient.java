package com.siwoo.application.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/app-context.dev.xml")
public class TestClient {

    @PersistenceContext EntityManager entityManager;

    public static List<Client> fixtureClients = new ArrayList<>();

    static {
        fixtureClients = Arrays.asList(
                new Client("client1","1234","client1@email.com",null)
        );
    }

    @Test
    public void setup() {

    }
    @Test
    public void save() {
        Client client = fixtureClients.get(0);
        assertNotNull(client);

        Membership membership = new Membership(Membership.Status.BRONZE,10);
        client.setMembership(membership);
        assertTrue(membership.getClients().contains(client));
        fixtureClients.get(0).setMembership(membership);
        assertTrue(membership.getClients().size() == 1);

    }
}
