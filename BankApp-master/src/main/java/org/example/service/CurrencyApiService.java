package org.example.service;

import java.util.Map;

public interface CurrencyApiService {

    Map<String, Double> getCurrencyMap(String currency);
}