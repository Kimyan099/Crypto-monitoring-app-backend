package com.szemereadam.cma.controller;

import com.szemereadam.cma.DTO.UserCredentials;
import com.szemereadam.cma.Logger.ExceptionLog;
import com.szemereadam.cma.model.Client;
import com.szemereadam.cma.repository.ClientRepository;
import com.szemereadam.cma.repository.CurrencyRepository;
import com.szemereadam.cma.security.JwtTokenService;
import com.szemereadam.cma.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.szemereadam.cma.model.ApplicationUserRole.CLIENT;

@RestController
public class ClientController {

    private static final String ORIGIN = "http://localhost:3000";

    @Autowired
    private ClientService clientService;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private ExceptionLog exceptionLog;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    public ClientController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    //Todo: ChangePassword, ChangeEmail, addCurrencyToWishList, RemoveCurrencyFromWishList

    @CrossOrigin(origins = ORIGIN, allowCredentials = "true")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserCredentials data, HttpServletResponse response) {
        try {
            String email = data.getEmail();
            String password = data.getPassword();
            Client client = clientService.checkIfCanLogIn(email, password);

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            List<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenService.createToken(password, authorities);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("authorities", authorities);
            model.put("token", token);
            response.setStatus(200);

            return ResponseEntity.ok(model);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    @CrossOrigin(origins = ORIGIN, allowCredentials = "true")
    @PostMapping(value = "/add")
    public void registerClient(@RequestBody Client client, HttpServletResponse response) {
        try {
            client.setAuthorities(CLIENT.getGrantedAuthorities());
            clientService.registerUser(client);
            response.setStatus(200);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Invalid user");

        } catch (NullPointerException e) {
            response.setStatus(400);
            throw new NullPointerException("User not created");
        }
    }

    @GetMapping("/users")
    public List<Client> getUsers(HttpServletResponse response){
        try {
            response.setStatus(200);
            return clientService.getUsers();
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Illegal arguments in user list");
        }
    }

    @CrossOrigin(origins = ORIGIN,  allowCredentials = "true")
    @PostMapping(value = "/user")
    public boolean isUserExist(String email, HttpServletResponse response) {
        try {
            response.setStatus(200);
            return clientService.isUserExist(email);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Email in invalid format");
        }
    }
}
