package de.othr.cryptopal.entity.util;

import de.othr.cryptopal.entity.Currency;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom JSON deserializer
 */

public class JsonUtils {

    private static String ECB_RATES_STRING = "rates";

    // TODO change dynamically
    private static String BASE_CURRENCY_STRING = "USD";

    public static List<Currency> getFiatCurrenciesFromResponse(JSONObject jsonObject, List<String> currencies) {

        List<Currency> fetchedCurrencies = new ArrayList<>();
        try {
            JSONObject rates = jsonObject.getJSONObject(ECB_RATES_STRING);

            for(String currencyString : currencies) {
                double exchangeRate = Double.parseDouble(rates.getString(currencyString));

                Currency currency = new Currency(currencyString, exchangeRate);
                fetchedCurrencies.add(currency);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return fetchedCurrencies;
    }

    public static List<Currency> getCryptoCurrenciesFromResponse(JSONObject jsonObject, List<String> currencies) {
        List<Currency> fetchedCryptoCurrencies = new ArrayList<>();

        try {
            for(String currencyString : currencies) {
                JSONObject cryptoCurrencyJsonObject = jsonObject.getJSONObject(currencyString);

                double exchangeRate = Double.parseDouble(cryptoCurrencyJsonObject.getString(BASE_CURRENCY_STRING));

                Currency currency = new Currency(currencyString, exchangeRate);
                fetchedCryptoCurrencies.add(currency);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return fetchedCryptoCurrencies;
    }

}
