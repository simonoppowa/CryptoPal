package util;

import de.othr.cryptopal.entity.Currency;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CurrencyPropertiesUtil {

    private static final String CURRENCY_PROPERTIES_FILE_PATH = "currency.properties";
    private static final String BASE_CURRENCY_KEY = "BASE_CURRENCY";
    private static final String SUPPORTED_FIAT_CURRENCIES_KEY = "SUPPORTED_FIAT_CURRENCIES";
    private static final String SUPPORTED_CRYPTO_CURRENCIES_KEY = "SUPPORTED_CRYPTO_CURRENCIES";

    private static String baseCurrencyId = setBaseCurrency();
    private static List<Currency> supportedFiatCurrencies = setSupportedFiatCurrencyStrings();
    private static List<Currency> supportedCryptoCurrencyStrings = setSupportedCryptoCurrencyStrings();

    private static String setBaseCurrency() {

        Properties prop = getProperties();

        return prop.getProperty(BASE_CURRENCY_KEY);
    }

    private static List<Currency> setSupportedFiatCurrencyStrings() {

        Properties prop = getProperties();

        String supportedFiatCurrenciesString = prop.getProperty(SUPPORTED_FIAT_CURRENCIES_KEY);

        return getCurrenciesFromPropString(supportedFiatCurrenciesString);
    }

    private static List<Currency> setSupportedCryptoCurrencyStrings() {

        Properties prop = getProperties();

        String supportedCryptoCurrenciesString = prop.getProperty(SUPPORTED_CRYPTO_CURRENCIES_KEY);

        return getCurrenciesFromPropString(supportedCryptoCurrenciesString);
    }

    private static Properties getProperties() {
        Properties prop = new Properties();
        InputStream in = CurrencyPropertiesUtil.class.getClassLoader().getResourceAsStream(CURRENCY_PROPERTIES_FILE_PATH);

        try {
            prop.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    private static List<Currency> getCurrenciesFromPropString(String string) {

        List<Currency> supportedCurrencies = new ArrayList<>();

        // Split by lines
        List<String> lines = new LinkedList<>(Arrays.asList(string.split("\\r?\\n")));

        for(String line : lines) {
            String[] values = line.split(", ");
            Currency newCurrency = new Currency(values[0], values[1], values[2]);
            supportedCurrencies.add(newCurrency);
        }
        return supportedCurrencies;
    }

    public static String getBaseCurrency() {
        return baseCurrencyId;
    }

    public static List<Currency> getSupportedFiatCurrencies() {
        return supportedFiatCurrencies;
    }

    public static List<Currency> getSupportedCryptoCurrencies() {
        return supportedCryptoCurrencyStrings;
    }


}
