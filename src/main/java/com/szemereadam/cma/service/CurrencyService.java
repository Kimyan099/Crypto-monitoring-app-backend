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

                MarketData marketData = MarketData.builder()
                        .price(currentObj.getJSONObject("metrics").
                                getJSONObject("market_data").
                                getDouble("price_usd"))
                        .percentageChangeLastHour(currentObj
                                .getJSONObject("metrics")
                                .getJSONObject("market_data")
                                .getDouble("percent_change_usd_last_1_hour"))
                        .percentageChangeLast24Hours(currentObj
                                .getJSONObject("metrics")
                                .getJSONObject("market_data")
                                .getDouble("percent_change_usd_last_24_hours"))
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
}
