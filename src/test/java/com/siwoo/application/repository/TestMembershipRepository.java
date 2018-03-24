package com.siwoo.application.repository;

import com.siwoo.application.domain.Client;
import com.siwoo.application.domain.Membership;
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

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/app-context.dev.xml")
public class TestMembershipRepository {

    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    ClientRepository clientRepository;
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

    @Test @Transactional
    public void testFindByClient() {
        Client client = fixtureClients.get(0);
        client.setMembership(fixtureMemberships.get(0));
        clientRepository.save(client);
        Membership membership = membershipRepository.findByClient(client);
        assertNotNull(membership);
        validateMembership(membership,false);
    }

    @Test
    public void testFindByStatusAS() {
        Membership membership = fixtureMemberships.get(0);
        Client client = fixtureClients.get(0);
        client.setMembership(membership);
        clientRepository.save(client);
        client = fixtureClients.get(1);
        client.setMembership(membership);
        clientRepository.save(client);

        membership = membershipRepository.findByStatusAS(membership.getStatus());
        validateMembership(membership ,true);
    }


    private void validateMembership(Membership membership,boolean hasClients) {
        assertNotNull(membership.getStatus());
        assertTrue(membership.getDiscount() > 0);
        assertNotNull(membership.getId());
        if(hasClients) {
            assertNotNull(membership);
            membership.getClients().stream().map(Client::toString).forEach(log::warn);
        }
    }
}

























