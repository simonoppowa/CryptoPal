package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Wallet;
import de.othr.cryptopal.entity.util.JsonUtils;
import de.othr.cryptopal.entity.util.UrlUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import util.CurrencyPropertiesUtil;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Singleton
public class CurrencyInformationService extends AbstractService<Currency> {

    private List<Currency> fiatCurrencies;
    private List<Currency> cryptoCurrencies;

    private Map<String, Currency> currencyMap = new HashMap<>();

    public CurrencyInformationService() {
        super(Currency.class);
    }

    @PostConstruct
    public void init() {
        setAllCurrencies();
        fetchAllPrices();
    }

    // TODO make 24h

    /**
     * Fetches new currency prices every interval
     */
    @Schedule(
            second = "*",
            minute = "*",
            hour = "*/10",
            persistent = false
    )
    private void fetchNewPrices() {
        fetchAllPrices();
        logger.log(Level.INFO, "Currency prices updated");
    }

    public void setAllCurrencies() {
        List<Currency> currenciesFromDB = loadCurrenciesFromDB();
        // Check if currencies already in DB
        if(currenciesFromDB == null || currenciesFromDB.isEmpty()) {
            fiatCurrencies = CurrencyPropertiesUtil.getSupportedFiatCurrencies();
            cryptoCurrencies = CurrencyPropertiesUtil.getSupportedCryptoCurrencies();
            putCurrenciesInMap(fiatCurrencies);
            putCurrenciesInMap(cryptoCurrencies);
            persistCurrencies(fiatCurrencies, cryptoCurrencies);
        } else {
            putCurrenciesInMap(currenciesFromDB);
        }
    }


    private void fetchAllPrices() {
        fetchAllFiatCurrencyPrices();
        fetchAllCryptoCurrencyPrices();


    }

    public Currency getCurrencyFromMap(String key) {
        return currencyMap.get(key);
    }

    public List<Currency> getAllFiatCurrencies() {

        if(currencyMap.isEmpty()) {
            fetchAllFiatCurrencyPrices();
        }
        return fiatCurrencies;
    }

    private void fetchAllFiatCurrencyPrices() {
        List<String> currencyIds = getListOfCurrencyIds(fiatCurrencies);

        URL url = UrlUtils.buildFiatCurrencyUrl(logger, currencyIds);
        JSONObject jsonObject = fetchFromURL(url);
        List<Currency> currencies = JsonUtils.getFiatCurrenciesFromResponse(jsonObject, fiatCurrencies);

        fiatCurrencies = currencies;
        for(Currency currency : currencies) {
            currencyMap.get(currency.getCurrencyId()).setExchangeRate(currency.getExchangeRate());
        }
    }

    private void fetchAllCryptoCurrencyPrices() {

        List<String> currencyIds = getListOfCurrencyIds(cryptoCurrencies);

        URL url = UrlUtils.buildCryptoCurrencyUrl(logger, currencyIds);
        JSONObject jsonObject = fetchFromURL(url);
        List<Currency> currencies = JsonUtils.getCryptoCurrenciesFromResponse(jsonObject, cryptoCurrencies);

        for(Currency currency : currencies) {
            currencyMap.get(currency.getCurrencyId()).setExchangeRate(currency.getExchangeRate());
        }
    }

    public void putCurrenciesInMap(List<Currency> currencies) {
        for(Currency currency : currencies) {
            currencyMap.put(currency.getCurrencyId(), currency);
        }
    }

    @TransactionAttribute
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

    @TransactionAttribute
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

    public BigDecimal calcualteFullPortfolioPrice(Account account) {
        List<Wallet> wallets = account.getWallets();

        BigDecimal fullAmount = new BigDecimal(0);
        Currency baseCurrency = getCurrencyFromMap(CurrencyPropertiesUtil.getBaseCurrency());


        for(Wallet wallet : wallets) {
            BigDecimal exchangeRate = new BigDecimal(getCurrencyFromMap(baseCurrency.getCurrencyId()).getExchangeRate());
            fullAmount.add(wallet.getCredit().multiply(exchangeRate));
        }
        return fullAmount;
    }

    public List<Currency> getFiatCurrencies() {
        return fiatCurrencies;
    }

    public void setFiatCurrencies(List<Currency> fiatCurrencies) {
        this.fiatCurrencies = fiatCurrencies;
    }

    public List<Currency> getCryptoCurrencies() {
        return cryptoCurrencies;
    }

    public void setCryptoCurrencies(List<Currency> cryptoCurrencies) {
        this.cryptoCurrencies = cryptoCurrencies;
    }
}
