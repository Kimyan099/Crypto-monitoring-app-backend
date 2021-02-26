package com.szemereadam.cma.HttpConnection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HttpConnectionTest {

    @Autowired
    private HttpConnection connection;

    @Test
    public void getContentShouldThrowExceptionWithWrongInput() {
        String content = "https://NOT_EXISTING_PAGE";
        assertThrows(RestClientException.class, () -> connection.getContent(content));
    }

    @Test
    void testGetContentWithCorrectUrlInput() {
        String content = "https://data.messari.io/api/v1/news";
        assertDoesNotThrow(() -> connection.getContent(content));
    }

}