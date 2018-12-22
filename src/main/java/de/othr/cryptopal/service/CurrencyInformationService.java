package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.util.JsonUtils;
import de.othr.cryptopal.entity.util.UrlUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.faces.bean.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class CurrencyInformationService implements Serializable {

    // TODO Change currencies dynamically
    private List<String> fiatCurrenciesToFetch = new ArrayList<>(Arrays.asList("EUR", "GBP", "JPY"));
    private List<String> cryptoCurrenciesToFetch = new ArrayList<>(Arrays.asList("BTC", "ETH", "XRP"));


    public List<Currency> getAllFiatCurrencies() {
        URL url = UrlUtils.buildFiatCurrencyUrl(fiatCurrenciesToFetch);
        JSONObject jsonObject = fetchFromURL(url);
        List<Currency> currencies = JsonUtils.getFiatCurrenciesFromResponse(jsonObject, fiatCurrenciesToFetch);

        for(Currency currency : currencies) {
            System.out.println(currency.toString());
        }

        return null;

    }

    public List<Currency> getAllCryptoCurrencies() {
        URL url = UrlUtils.buildCryptoCurrencyUrl(cryptoCurrenciesToFetch);
        JSONObject jsonObject = fetchFromURL(url);
        List<Currency> currencies = JsonUtils.getCryptoCurrenciesFromResponse(jsonObject, cryptoCurrenciesToFetch);

        for(Currency currency : currencies) {
            System.out.println(currency.toString());
        }

        return null;
    }

    private JSONObject fetchFromURL(URL url) {

        try {
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

                return jsonObject;
            }

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }



}
