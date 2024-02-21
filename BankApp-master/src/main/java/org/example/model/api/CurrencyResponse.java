package org.example.model.api;

import java.util.Map;

public class CurrencyResponse {

    private Map<String, Double> rates;

    public CurrencyResponse() {
        //
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "rates=" + rates +
                '}';
    }
}