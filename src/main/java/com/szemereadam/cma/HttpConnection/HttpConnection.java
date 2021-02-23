package com.szemereadam.cma.HttpConnection;

import com.szemereadam.cma.Logger.ExceptionLog;
import org.json.JSONException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpConnection {
    private final ExceptionLog exceptionLog = new ExceptionLog();

    public String getContent(String api) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(api, String.class);

        } catch (RestClientException e) {
            exceptionLog.log(e);
            throw new RestClientException("Request fails because of server response error");

        } catch (IllegalArgumentException e) {
            exceptionLog.log(e);
            throw new IllegalArgumentException("Invalid URL");
        }
    }
}
