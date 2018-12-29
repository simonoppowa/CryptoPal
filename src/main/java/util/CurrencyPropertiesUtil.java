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
    private static List<String> supportedCryptoCurrencyStrings = setSupportedCryptoCurrencyStrings();

    private static String setBaseCurrency() {

        Properties prop = getProperties();

        String currency = prop.getProperty(BASE_CURRENCY_KEY);

        System.out.println("Loaded base currency from properties: " + currency);

        return currency;
    }

    private static List<Currency> setSupportedFiatCurrencyStrings() {

        List<Currency> supportedCurrencies = new ArrayList<>();

        Properties prop = getProperties();

        String supportedFiatCurrenciesString = prop.getProperty(SUPPORTED_FIAT_CURRENCIES_KEY);

        // Split by lines
        List<String> lines = new LinkedList<>(Arrays.asList(supportedFiatCurrenciesString.split("\\r?\\n")));

        for(String line : lines) {
            System.out.println(line);
            String[] values = line.split(", ");
            Currency newCurrency = new Currency(values[0], values[1], values[2]);
            supportedCurrencies.add(newCurrency);
        }

        return supportedCurrencies;
    }

    private static List<String> setSupportedCryptoCurrencyStrings() {

        Properties prop = getProperties();

        String supportedFiatCurrenciesString = prop.getProperty(SUPPORTED_CRYPTO_CURRENCIES_KEY);

        return Arrays.asList(supportedFiatCurrenciesString.split(", "));
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

    public static String getBaseCurrency() {
        return baseCurrencyId;
    }

    public static List<Currency> getSupportedFiatCurrencies() {
        return supportedFiatCurrencies;
    }

    public static List<String> getSupportedCryptoCurrencyStrings() {
        return supportedCryptoCurrencyStrings;
    }


}
