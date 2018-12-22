package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class CurrencyPropertiesUtil {

    private static final String CURRENCY_PROPERTIES_FILE_PATH = "currency.properties";
    private static final String BASE_CURRENCY_KEY = "BASE_CURRENCY";
    private static final String SUPPORTED_FIAT_CURRENCIES_KEY = "SUPPORTED_FIAT_CURRENCIES";
    private static final String SUPPORTED_CRYPTO_CURRENCIES_KEY = "SUPPORTED_CRYPTO_CURRENCIES";

    private static String baseCurrencyId = setBaseCurrency();
    private static List<String> supportedFiatCurrencyStrings = setSupportedFiatCurrencyStrings();
    private static List<String> supportedCryptoCurrencyStrings = setSupportedCryptoCurrencyStrings();

    private static String setBaseCurrency() {

        Properties prop = getProperties();

        String currency = prop.getProperty(BASE_CURRENCY_KEY);

        System.out.println("Loaded base currency from properties: " + currency);

        return currency;
    }

    private static List<String> setSupportedFiatCurrencyStrings() {

        Properties prop = getProperties();

        String supportedFiatCurrenciesString = prop.getProperty(SUPPORTED_FIAT_CURRENCIES_KEY);

        List<String> strings = new LinkedList<>(Arrays.asList(supportedFiatCurrenciesString.split(", ")));
        // Remove base currency (always 1.00)
        strings.remove(baseCurrencyId);

        return strings;
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

    public static List<String> getSupportedFiatCurrencyStrings() {
        return supportedFiatCurrencyStrings;
    }

    public static List<String> getSupportedCryptoCurrencyStrings() {
        return supportedCryptoCurrencyStrings;
    }


}
