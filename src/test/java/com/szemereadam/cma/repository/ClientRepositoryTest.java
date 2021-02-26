package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.Client;
import com.szemereadam.cma.model.Currency;
import com.szemereadam.cma.model.MarketData;
import com.szemereadam.cma.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CurrencyRepository currencyRepository;


    @Test
    public void saveOneSimple(){

        clientRepository.deleteAll();

        Client test = Client.builder()
                .email("test@codecool.com")
                .password("1234")
                .build();

        clientRepository.save(test);

        List<Client> userList = clientRepository.findAll();

        assertEquals(1, userList.size());
    }

    @Test
    public void saveUniqueTwice(){

        clientRepository.deleteAll();

        assertThrows(DataIntegrityViolationException.class, () -> {
            Client test = Client.builder()
                    .email("test@codecool.com")
                    .password("1234")
                    .build();

            Client test2 = Client.builder()
                    .email("test@codecool.com")
                    .password("12345")
                    .build();

            clientRepository.saveAll(Arrays.asList(test, test2));
        });
    }

    @Test
    public void passwordShouldBeNotNull(){

        clientRepository.deleteAll();

        assertThrows(DataIntegrityViolationException.class, () -> {
            Client test = Client.builder()
                    .email("test@codecool.com")
                    .build();

            clientRepository.save(test);
        });
    }

    @Test
    public void emailShouldBeNotNull(){

        clientRepository.deleteAll();

        assertThrows(DataIntegrityViolationException.class, () -> {
            Client test = Client.builder()
                    .password("123")
                    .build();

            clientRepository.save(test);
        });
    }

    @Test
    public void addCurrencyToClientWishList(){

        clientRepository.deleteAll();
        currencyRepository.deleteAll();

        Profile profile = Profile.builder()
                .background("test background")
                .technology("test technology")
                .overview("test overview")
                .tokenDistributionDescription("test description")
                .build();


        MarketData marketData = MarketData.builder()
                .price(1.3)
                .percentageChangeLastHour(0.12243)
                .percentageChangeLast24Hours(-1.531)
                .build();

        Currency currency = Currency.builder()
                .symbol("TEST")
                .name("testName")
                .marketData(marketData)
                .profile(profile)
                .build();


        Set<Currency> wishList = new HashSet<>(Arrays.asList(currency));

        Client client = Client.builder()
                .email("test@codecool.com")
                .password("1234")
                .build();

        currencyRepository.save(currency);
        currency.setClientList(Set.of(client));
        client.setWishList(wishList);
        clientRepository.save(client);

        List<Currency> currencies = currencyRepository.findAll();

        assertEquals(1,currencies.size());
    }
}