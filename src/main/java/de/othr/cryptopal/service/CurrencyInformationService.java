package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.util.JsonUtils;
import de.othr.cryptopal.entity.util.UrlUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import util.CurrencyPropertiesUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

@ApplicationScoped
public class CurrencyInformationService extends AbstractService<Currency> {

    private List<Currency> fiatCurrenciesToFetch = CurrencyPropertiesUtil.getSupportedFiatCurrencies();
    private List<Currency> cryptoCurrenciesToFetch = CurrencyPropertiesUtil.getSupportedCryptoCurrencies();

    private Map<String, Currency> currencyMap = new HashMap<>();

    public CurrencyInformationService() {
        super(Currency.class);
    }

    @PostConstruct
    public void init() {
        setAllCurrencies();
    }

    public void setAllCurrencies() {
        List<Currency> currenciesFromDB = loadCurrenciesFromDB();
        // Check if currencies already in DB
        if(currenciesFromDB == null || currenciesFromDB.isEmpty()) {
            List<Currency> fiatCurrencies = getAllFiatCurrencies();
            List<Currency> cryptoCurrencies = getAllCryptoCurrencies();
            persistCurrencies(fiatCurrencies, cryptoCurrencies);
        } else {
            putCurrenciesInMap(currenciesFromDB);
        }
    }

    public Currency getCurrencyFromMap(String key) {
        return currencyMap.get(key);
    }

    public List<Currency> getAllFiatCurrencies() {

        if(currencyMap.isEmpty()) {
            List<String> currencyIds = getListOfCurrencyIds(fiatCurrenciesToFetch);

            URL url = UrlUtils.buildFiatCurrencyUrl(logger, currencyIds);
            JSONObject jsonObject = fetchFromURL(url);
            List<Currency> currencies = JsonUtils.getFiatCurrenciesFromResponse(jsonObject, fiatCurrenciesToFetch);

            fiatCurrenciesToFetch = currencies;
            putCurrenciesInMap(currencies);

            return currencies;
        } else {
            return fiatCurrenciesToFetch;
        }
    }

    public void putCurrenciesInMap(List<Currency> currencies) {
        for(Currency currency : currencies) {
            currencyMap.put(currency.getCurrencyId(), currency);
        }
    }

    public List<Currency> getAllCryptoCurrencies() {

        List<String> currencyIds = getListOfCurrencyIds(cryptoCurrenciesToFetch);

        URL url = UrlUtils.buildCryptoCurrencyUrl(logger, currencyIds);
        JSONObject jsonObject = fetchFromURL(url);
        List<Currency> currencies = JsonUtils.getCryptoCurrenciesFromResponse(jsonObject, cryptoCurrenciesToFetch);

        putCurrenciesInMap(currencies);

        return currencies;
    }

    @Transactional
    private void persistCurrencies(List<Currency>... currencies) {

        for(List<Currency> currencyList : currencies) {
            for(Currency currency : currencyList) {
                em.persist(currency);
            }
        }
        em.flush();

        // Put currencies in map
        for(List<Currency> currencyList : currencies) {
            putCurrenciesInMap(currencyList);
        }
    }

    @Transactional
    private List<Currency> loadCurrenciesFromDB() {
        TypedQuery<Currency> typedQuery = em.createNamedQuery(Currency.FINDALL, Currency.class);

        try {
            return typedQuery.getResultList();
        } catch(NoResultException ex) {
            return null;
        }
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

                logger.log(Level.INFO, "Fetched: " + jsonObject);

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
