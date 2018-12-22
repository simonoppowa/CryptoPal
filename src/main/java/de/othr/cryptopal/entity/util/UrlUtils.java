package de.othr.cryptopal.entity.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class UrlUtils {

    // TODO Change base currency dynamically
    private static final String EXCHANGE_RATES_BASE_URL = "https://api.exchangeratesapi.io/latest?base=USD";
    private static final String EXCHANGE_RATES_RATES_SYMBOLS = "&symbols=";

    private static final String CRYPTOCOMPARE_BASE_URL = "https://min-api.cryptocompare.com/data/pricemulti";
    private static final String CRYPTOCOMPARE_FSYM_SYMBOL = "?fsyms=";
    private static final String CRYPTOCOMPARE_TSYM_SYMBOL = "&tsyms=USD";

    public static URL buildFiatCurrencyUrl(List<String> currenciesToFetch) {

        String urlString = EXCHANGE_RATES_BASE_URL.concat(EXCHANGE_RATES_RATES_SYMBOLS);

        Iterator<String> iterator = currenciesToFetch.iterator();
        while (iterator.hasNext()) {
            String currencyString = iterator.next();

            urlString += currencyString;

            if (iterator.hasNext()) {
                urlString += ",";
            }

        }
        System.out.println("URL built: " + urlString);

        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            System.out.println("Error while building ECB URL");
        }
        return null;
    }

    public static URL buildCryptoCurrencyUrl(List<String> currenciesToFetch) {
        String urlString = CRYPTOCOMPARE_BASE_URL.concat(CRYPTOCOMPARE_FSYM_SYMBOL);

        Iterator<String> iterator = currenciesToFetch.iterator();

        while(iterator.hasNext()) {
            String currencyString = iterator.next();

            urlString+= currencyString;

            if(iterator.hasNext()) {
                urlString += ",";
            }

        }
        urlString += CRYPTOCOMPARE_TSYM_SYMBOL;

        System.out.println("URL built: " + urlString);

        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            System.out.println("Error while building CryptoCompare URL");
        }
        return null;
    }
}
