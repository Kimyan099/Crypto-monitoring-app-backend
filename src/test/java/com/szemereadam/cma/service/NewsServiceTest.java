package com.szemereadam.cma.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceTest {

    @Autowired
    private NewsService service;

    @Test
    void testFilterDataWithWrongJSONInput() {
        String content = "{ foo : foo }";
        assertThrows(IllegalArgumentException.class, () -> service.persistObjects(content));
    }

    @Test
    void testFilterDataWithEmptyJSONInput() {
        String content = "";
        assertThrows(IllegalArgumentException.class, () -> service.persistObjects(content));
    }

}