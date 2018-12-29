package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.util.JsonUtils;
import de.othr.cryptopal.entity.util.UrlUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import util.CurrencyPropertiesUtil;

import javax.faces.bean.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CurrencyInformationService implements Serializable {

    private List<Currency> fiatCurrenciesToFetch = CurrencyPropertiesUtil.getSupportedFiatCurrencies();
    private List<Currency> cryptoCurrenciesToFetch = CurrencyPropertiesUtil.getSupportedCryptoCurrencies();

    public List<Currency> getAllFiatCurrencies() {

        List<String> currencyIds = getListOfCurrencyIds(fiatCurrenciesToFetch);

        URL url = UrlUtils.buildFiatCurrencyUrl(currencyIds);
        JSONObject jsonObject = fetchFromURL(url);
        List<Currency> currencies = JsonUtils.getFiatCurrenciesFromResponse(jsonObject, fiatCurrenciesToFetch);

        for(Currency currency : currencies) {
            System.out.println(currency.toString());
        }

        return null;
    }

    public List<Currency> getAllCryptoCurrencies() {

        List<String> currencyIds = getListOfCurrencyIds(cryptoCurrenciesToFetch);


        URL url = UrlUtils.buildCryptoCurrencyUrl(currencyIds);
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

    private List<String> getListOfCurrencyIds(List<Currency> currencies) {
        // TODO optimize
        List<String> currencyIds = new ArrayList<>();
        for(Currency currency : currencies) {
            currencyIds.add(currency.getCurrencyId());
        }
        // Remove base currency (always 1.00)
        currencyIds.remove(CurrencyPropertiesUtil.getBaseCurrency());

        return currencyIds;
    }

    public List<Currency> getFiatCurrenciesToFetch() {
        return fiatCurrenciesToFetch;
    }

    public void setFiatCurrenciesToFetch(List<Currency> fiatCurrenciesToFetch) {
        this.fiatCurrenciesToFetch = fiatCurrenciesToFetch;
    }

    public List<Currency> getCryptoCurrenciesToFetch() {
        return cryptoCurrenciesToFetch;
    }

    public void setCryptoCurrenciesToFetch(List<Currency> cryptoCurrenciesToFetch) {
        this.cryptoCurrenciesToFetch = cryptoCurrenciesToFetch;
    }
}
