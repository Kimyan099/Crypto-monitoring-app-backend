package com.szemereadam.cma.service;

import com.szemereadam.cma.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    ClientService clientService;


    @Test
    void testIsUserExistAfterRegistering() {
        Client client = Client.builder().email("email").password("ps").build();
        clientService.registerUser(client);
        assertEquals(1, clientService.getUsers().size());
    }
}