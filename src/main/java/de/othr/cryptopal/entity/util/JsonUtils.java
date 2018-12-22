package de.othr.cryptopal.entity.util;

import de.othr.cryptopal.entity.Currency;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom JSON deserializer
 */

public class JsonUtils {

    private static String EXCHANGE_RATE_RATES_STRING = "rates";

    // TODO change dynamically
    private static String BASE_CURRENCY_STRING = "USD";

    public static List<Currency> getFiatCurrenciesFromResponse(JSONObject jsonObject, List<String> currencies) throws Exception {

        List<Currency> fetchedCurrencies = new ArrayList<>();

        JSONObject rates = jsonObject.getJSONObject(EXCHANGE_RATE_RATES_STRING);

        for(String currencyString : currencies) {
            double exchangeRate = Double.parseDouble(rates.getString(currencyString));

            Currency currency = new Currency(currencyString, exchangeRate);
            fetchedCurrencies.add(currency);
        }

        return fetchedCurrencies;
    }

    public static List<Currency> getCryptoCurrenciesFromResponse(JSONObject jsonObject, List<String> currencies) throws Exception {
        List<Currency> fetchedCryptoCurrencies = new ArrayList<>();

        for(String currencyString : currencies) {
            JSONObject cryptoCurrencyJsonObject = jsonObject.getJSONObject(currencyString);

            double exchangeRate = Double.parseDouble(cryptoCurrencyJsonObject.getString(BASE_CURRENCY_STRING));

            Currency currency = new Currency(currencyString, exchangeRate);
            fetchedCryptoCurrencies.add(currency);
        }

        return fetchedCryptoCurrencies;
    }

}
