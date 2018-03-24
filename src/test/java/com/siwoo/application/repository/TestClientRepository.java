package com.siwoo.application.repository;

import com.siwoo.application.domain.Client;
import com.siwoo.application.domain.Membership;
import com.siwoo.application.domain.TestClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestClientRepository  {

    @Autowired ClientRepository clientRepository;
    @Autowired MembershipRepository membershipRepository;
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

        for(Membership membership: fixtureMemberships) {
            membershipRepository.save(membership);
        }
    }

    @Test
    public void testSave() {
        Client client = TestClient.fixtureClients.get(0);
        clientRepository.save(client);
        Client foundClient = clientRepository.findById(client.getId());
        validateClient(foundClient, false);
        foundClient.setMembership(fixtureMemberships.get(0));
        clientRepository.save(foundClient);
        foundClient = clientRepository.findById(client.getId());
        validateClient(foundClient, true);
        log.warn(foundClient.toString());
        Membership membership = fixtureMemberships.get(0);
        for(Client client1: membership.getClients()) {
            log.info(client1.toString());
        }
    }

    @Test @Transactional
    public void testUpgradeMembership() {
        Client client = TestClient.fixtureClients.get(0);
        clientRepository.save(client);
        Client foundClient = clientRepository.findById(client.getId());
        foundClient.setMembership(fixtureMemberships.get(1));
        clientRepository.save(foundClient);
        foundClient = clientRepository.findById(client.getId());
        log.info(foundClient.getMembership().toString());
        //foundClient.setMembership(null);
        foundClient.removeMembership();
        foundClient = clientRepository.findById(client.getId());
        assertNull(foundClient.getMembership());
    }

    public void validateClient(Client client, boolean hasMembership) {
        assertNotNull(client);
        assertNotNull(client.getId());
        assertNotNull(client.getName());
        assertNotNull(client.getEmail());
        assertNotNull(client.getPassword());
        assertNotNull(client.getJoinDate());
        if(hasMembership) {
            assertNotNull(client.getMembership());
        }
    }

}
