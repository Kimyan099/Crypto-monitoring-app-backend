package com.szemereadam.cma.controller;

import com.szemereadam.cma.Logger.ExceptionLog;
import com.szemereadam.cma.model.Currency;
import com.szemereadam.cma.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CurrencyController {

    private static final String ORIGIN = "http://localhost:3000";

    @Autowired
    private ExceptionLog exceptionLog;

    @Autowired
    private CurrencyRepository repository;

    @CrossOrigin(origins = ORIGIN)
    @GetMapping("/currencies")
    public List<Currency> getCurrencies() {
        return repository.findAll();
    }

    @CrossOrigin(origins = ORIGIN)
    @GetMapping("/currencies/{symbol}")
    public Currency getCurrencyBySymbol(@PathVariable String symbol, HttpServletResponse response) {
        try {
            Currency currency = repository.getCurrencyBySymbol(symbol);
            response.setStatus(200);
            return currency;

        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            exceptionLog.log(e);
            throw new IllegalArgumentException("Invalid id!");
        }
    }
}
