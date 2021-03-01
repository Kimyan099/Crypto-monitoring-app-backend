package com.szemereadam.cma.service;


import com.szemereadam.cma.Logger.ExceptionLog;
import com.szemereadam.cma.model.Currency;
import com.szemereadam.cma.model.MarketData;
import com.szemereadam.cma.model.Profile;
import com.szemereadam.cma.repository.CurrencyRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    private final ExceptionLog exceptionLog = new ExceptionLog();

    public void persistObjects(String content) throws JSONException {

        try {
            JSONObject jsonContent = new JSONObject(content);
            JSONArray currContent = jsonContent.getJSONArray("data");

            for (int i = 0; i < currContent.length(); i++) {

                JSONObject currentObj = currContent.getJSONObject(i);

                // get string doubles
                String usdLast1Hour = getMarketDataInString(currentObj, "percent_change_usd_last_1_hour");
                String usdLast24Hours = getMarketDataInString(currentObj, "percent_change_usd_last_24_hours");
                String usdPrice = getMarketDataInString(currentObj, "price_usd");

                // convert string double to double
                Double percent_change_usd_last_1_hour = convertValue(usdLast1Hour);
                Double percent_change_usd_last_24_hours = convertValue(usdLast24Hours);
                Double price = convertValue(usdPrice);

                //Build object from data
                MarketData marketData = MarketData.builder()
                        .price(price)
                        .percentageChangeLastHour(percent_change_usd_last_1_hour)
                        .percentageChangeLast24Hours(percent_change_usd_last_24_hours)
                        .build();

                Profile profile = Profile.builder()
                                    .overview(currentObj.getJSONObject("profile").getString("overview"))
                                    .background(currentObj.getJSONObject("profile").getString("background"))
                                    .technology(currentObj.getJSONObject("profile").getString("technology"))
                                    .tokenDistributionDescription(currentObj.getJSONObject("profile")
                                                                        .getJSONObject("token_distribution")
                                                                        .getString("description"))
                                    .build();


                Currency currency = Currency.builder()
                                            .name(currentObj.getString("name"))
                                            .symbol(currentObj.getString("symbol"))
                                            .marketData(marketData)
                                            .profile(profile)
                                            .build();

                repository.save(currency);
            }

            repository.flush();

        } catch (JSONException e) {
            exceptionLog.log(e);
            throw new JSONException("Content format is not valid");
        }
    }

    public void updateCurrency(Currency currency){
        repository.saveAndFlush(currency);
    }

    public Currency getCurrencyBySymbol(String symbol) {
        return repository.getCurrencyBySymbol(symbol);
    }

    private String getMarketDataInString(JSONObject currentObj, String line) throws JSONException {
        return currentObj
                .getJSONObject("metrics")
                .getJSONObject("market_data")
                .getString(line);
    }

    private Double convertValue(String aDouble) {

        double val = 0.0;
        if (!aDouble.equals("null"))  {
            val = round(aDouble);
        }
        return val;
    }

    private double round(String number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal
                .setScale(15, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
