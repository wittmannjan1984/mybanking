package org.example.service;

import org.example.model.api.CurrencyResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyApiServiceImpl implements CurrencyApiService {

    private static final String CURRENCY_API_URL = "https://api.exchangerate-api.com/v4/latest";

    @Override
    public Map<String, Double> getCurrencyMap(String currency) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyResponse> exchange = restTemplate.exchange(CURRENCY_API_URL + "/" + currency, HttpMethod.GET, null,
                CurrencyResponse.class);

        return exchange.getBody().getRates();
    }
}