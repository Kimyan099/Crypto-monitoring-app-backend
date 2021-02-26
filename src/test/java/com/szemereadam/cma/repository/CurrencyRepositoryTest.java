package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.Currency;
import com.szemereadam.cma.model.MarketData;
import com.szemereadam.cma.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository repository;

    @Test
    public void saveOneSimpleShouldReturnSizeOne() {

        repository.deleteAll();

        Currency currency = Currency.builder()
                                .symbol("TEST")
                                .name("testName")
                                .build();

        repository.save(currency);

        List<Currency> currencies = repository.findAll();

        assertEquals(1, currencies.size());

    }

    @Test
    public void persistedDataShouldReturnWithValidFields() {

        repository.deleteAll();

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

        repository.save(currency);

        List<Currency> currencies = repository.findAll();

        //Currency repository assertions
        assertEquals("testName", currencies.get(0).getName());
        assertEquals("TEST", currencies.get(0).getSymbol());

        // Profile repository assertions
        assertEquals("test background", currencies.get(0).getProfile().getBackground());
        assertEquals("test technology", currencies.get(0).getProfile().getTechnology());
        assertEquals("test overview", currencies.get(0).getProfile().getOverview());
        assertEquals("test description", currencies.get(0).getProfile().getTokenDistributionDescription());

        // MarketData repository assertions
        assertEquals(-1.531, currencies.get(0).getMarketData().getPercentageChangeLast24Hours());
        assertEquals(0.12243, currencies.get(0).getMarketData().getPercentageChangeLastHour());
        assertEquals(1.3, currencies.get(0).getMarketData().getPrice());
    }
}