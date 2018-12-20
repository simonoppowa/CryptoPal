package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.util.CurrencyUtils;
import org.codehaus.jettison.json.JSONObject;

import javax.faces.bean.ApplicationScoped;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class CurrencyInformationService implements Serializable {

    // TODO Change base currency dynamically
    private static final String EXCHANGE_RATE_BASE_URL = "https://api.exchangeratesapi.io/latest?base=USD";
    private static final String EXCHANGE_RATE_SYMBOLS = "&symbols=";


    public List<Currency> getAllFiatCurrencies() {

        // TODO Change currencies dynamically
        List<String> currenciesToFetch = new ArrayList<>(Arrays.asList("EUR", "GBP", "JPY"));

        try {
            String urlString = EXCHANGE_RATE_BASE_URL.concat(EXCHANGE_RATE_SYMBOLS);

            Iterator<String> iterator = currenciesToFetch.iterator();
            while (iterator.hasNext()) {
                String currencyString = iterator.next();

                urlString += currencyString;

                if (iterator.hasNext()) {
                    urlString += ",";
                }
            }

            System.out.println("Fetching from Url: " + urlString);


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {

                JSONObject jsonObject = new JSONObject(output);

                System.out.println("Fetched: " + jsonObject);

                List<Currency> fetchesCurrencies = CurrencyUtils.getFiatCurrenciesFromResponse(jsonObject, currenciesToFetch);

                for(Currency currency : fetchesCurrencies) {
                    System.out.println("Fetches currency: " + currency.getCurrencyId() + " " + currency.getExchangeRate());
                }

            }



        } catch (Exception ex) {

        }

        return null;

    }



}
