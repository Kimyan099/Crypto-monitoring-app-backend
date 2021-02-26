package com.szemereadam.cma.service;

import com.szemereadam.cma.Logger.ExceptionLog;
import com.szemereadam.cma.model.Client;
import com.szemereadam.cma.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ExceptionLog exceptionLog;

    @Autowired
    private ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    private Client loggedInClient;

    public ClientService() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public void registerUser(Client client){
        String password = client.getPassword();

        client.setPassword(passwordEncoder.encode(password));

        clientRepository.save(client);
    }

    public void updateClient(Client client){
        clientRepository.saveAndFlush(client);
    }

    public List<Client> getUsers() {
        return clientRepository.findAll();
    }

    public Client getLoggedInUser() {
        return loggedInClient;
    }

    public void setLoggedInUser(Client loggedInClient) {
        this.loggedInClient = loggedInClient;
    }

    public Client getUserByEmail(String email) {
        for (Client client : getUsers()) {
            if(client.getEmail().equals(email)) {
                return client;
            }
        }
        return new Client();
    }

    public boolean isUserExist(String email) {
        for (Client client : getUsers()) {
            if (client.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Client checkIfCanLogIn(String email, String password) {
        try {
            for(Client client : getUsers()) {
                if (client.getEmail().equals(email)) {
                    if (passwordEncoder.matches(password, client.getPassword())){
                        setLoggedInUser(client);
                        return client;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            exceptionLog.log(e);
            throw new IllegalArgumentException("Email or password in wrong format");
        }
        return null;
    }
}
