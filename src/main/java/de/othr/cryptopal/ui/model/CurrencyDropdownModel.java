package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.service.CurrencyInformationService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class CurrencyDropdownModel implements Serializable {

    @Produces
    private String selectedCurrency;
    @Produces
    private Map<String, String> supportedCurrencies;

    @Inject
    private CurrencyInformationService currencyInformationService;

    @PostConstruct
    public void init() {
        supportedCurrencies = new HashMap<>();
        List<Currency> currencies = currencyInformationService.getAllFiatCurrencies();
        for(Currency currency : currencies) {
            supportedCurrencies.put(currency.getCurrencyId(), currency.getCurrencyName());
        }
    }

    public Currency getSelectedCurrency() {
        return currencyInformationService.getCurrencyFromMap(selectedCurrency);
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public Map<String, String> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public void setSupportedCurrencies(Map<String, String> supportedCurrencies) {
        this.supportedCurrencies = supportedCurrencies;
    }

    public CurrencyInformationService getCurrencyInformationService() {
        return currencyInformationService;
    }

    public void setCurrencyInformationService(CurrencyInformationService currencyInformationService) {
        this.currencyInformationService = currencyInformationService;
    }
}
