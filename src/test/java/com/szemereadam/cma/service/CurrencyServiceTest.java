package com.szemereadam.cma.service;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyServiceTest {

    @Autowired
    private CurrencyService service;

    @Test
    void testFilterDataWithWrongJSONInput() {
        String content = "{ foo : foo }";
        assertThrows(JSONException.class, () -> service.persistObjects(content));
    }

    @Test
    void testFilterDataWithEmptyJSONInput() {
        String content = "";
        assertThrows(JSONException.class, () -> service.persistObjects(content));
    }
}