package com.szemereadam.cma.controller;

import com.szemereadam.cma.Logger.ExceptionLog;
import com.szemereadam.cma.model.Client;
import com.szemereadam.cma.model.Currency;
import com.szemereadam.cma.repository.ClientRepository;
import com.szemereadam.cma.service.ClientService;
import com.szemereadam.cma.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    //private static final String ORIGIN = "http://localhost:3000";
    private static final String ORIGIN = "*";

    @Autowired
    private ClientService clientService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private ExceptionLog exceptionLog;

    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = "/add")
    public void addUser(@RequestBody Client client, HttpServletResponse response) {
        try {
            clientService.registerUser(client);
            response.setStatus(200);

        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Invalid user");

        } catch (NullPointerException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new NullPointerException("User not created");
        }
    }

    @GetMapping("/users")
    public List<Client> getUsers(HttpServletResponse response){
        try {
            List<Client> users = clientService.getUsers();
            response.setStatus(200);
            return users;
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Illegal arguments in user list");

        } catch (IndexOutOfBoundsException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = "/user")
    public boolean isUserExist(String email, HttpServletResponse response) {
        try {
            boolean statement = clientService.isUserExist(email);
            response.setStatus(200);
            return statement;

        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Email in invalid format");
        }
    }

    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = "/active/set-password")
    public void setPassword(String password, HttpServletResponse response) {
        try {
            Client client = clientService.getLoggedInUser();
            client.setPassword(password);
            clientService.updateClient(client);
            response.setStatus(200);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Password format is invalid format");
        }
    }

    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = "/addToWishList/{symbol}")
    public void addToWishList(@PathVariable String symbol, HttpServletResponse response) {
        try {
            Client client = clientService.getLoggedInUser();
            Currency currency = currencyService.getCurrencyBySymbol(symbol);

            //set client wishlist
            Set<Currency> wishList = client.getWishList();
            wishList.add(currency);
            client.setWishList(wishList);

            // add client to currency client list
            Set<Client> clients =  currency.getClientList();
            clients.add(client);
            currency.setClientList(clients);

            //Persist data
            currencyService.updateCurrency(currency);
            clientService.updateClient(client);
            response.setStatus(200);

        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Mobile number in invalid format");
        }
    }


    @CrossOrigin(origins = ORIGIN)
    @PostMapping(value = "/login")
    public Client update(String email, String password, HttpServletResponse response) {
        try {
            Client client = clientService.checkIfCanLogIn(email, password);
            response.setStatus(200);
            return client;
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
