package de.othr.cryptopal.entity.util;

import de.othr.cryptopal.util.CurrencyPropertiesUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlUtils {

    private static String BASE_CURRENCY_ID = CurrencyPropertiesUtil.getBaseCurrency();

    private static final String EXCHANGE_RATES_BASE_URL = "https://api.exchangeratesapi.io/latest?base=";
    private static final String EXCHANGE_RATES_RATES_SYMBOLS = "&symbols=";

    private static final String CRYPTOCOMPARE_BASE_URL = "https://min-api.cryptocompare.com/data/pricemulti";
    private static final String CRYPTOCOMPARE_FSYM_SYMBOL = "?fsyms=";
    private static final String CRYPTOCOMPARE_TSYM_SYMBOL = "&tsyms=";

    public static URL buildFiatCurrencyUrl(Logger logger, List<String> currenciesToFetch) {

        String urlString = EXCHANGE_RATES_BASE_URL + BASE_CURRENCY_ID + EXCHANGE_RATES_RATES_SYMBOLS;

        Iterator<String> iterator = currenciesToFetch.iterator();
        while (iterator.hasNext()) {
            String currencyString = iterator.next();

            urlString += currencyString;

            if (iterator.hasNext()) {
                urlString += ",";
            }

        }
        logger.log(Level.INFO,"URL built: " + urlString );

        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            logger.log(Level.WARNING, "Error while building ECB URL");
            return null;
        }
    }

    public static URL buildCryptoCurrencyUrl(Logger logger, List<String> currenciesToFetch) {
        String urlString = CRYPTOCOMPARE_BASE_URL.concat(CRYPTOCOMPARE_FSYM_SYMBOL);

        Iterator<String> iterator = currenciesToFetch.iterator();

        //TODO use StringBuffer
        while(iterator.hasNext()) {
            String currencyString = iterator.next();

            urlString+= currencyString;

            if(iterator.hasNext()) {
                urlString += ",";
            }

        }
        urlString += (CRYPTOCOMPARE_TSYM_SYMBOL + BASE_CURRENCY_ID);

        logger.log(Level.INFO, "URL built: " + urlString);

        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            logger.log(Level.INFO, "Error while building CryptoCompare URL");
            return null;
        }
    }
}
