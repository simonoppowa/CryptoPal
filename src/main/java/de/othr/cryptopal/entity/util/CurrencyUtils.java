package de.othr.cryptopal.entity.util;

import de.othr.cryptopal.entity.Currency;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom JSON deserializer
 */

public class CurrencyUtils {

    private static String EXCHANGE_RATE_RATES_STRING = "rates";

    public static List<Currency> getFiatCurrenciesFromResponse(JSONObject jsonObject, List<String> currencies) throws Exception{

        List<Currency> fetchedCurrencies = new ArrayList<>();

        JSONObject rates = jsonObject.getJSONObject(EXCHANGE_RATE_RATES_STRING);

        for(String currencyString : currencies) {
            double exchangeRate = Double.parseDouble(rates.getString(currencyString));

            Currency currency = new Currency(currencyString, exchangeRate);
            fetchedCurrencies.add(currency);
        }

        return fetchedCurrencies;
    }

}
