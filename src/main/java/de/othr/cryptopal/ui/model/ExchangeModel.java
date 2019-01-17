package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Wallet;
import de.othr.cryptopal.service.AccountService;
import de.othr.cryptopal.service.CurrencyInformationService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named
public class ExchangeModel extends AbstractModel {

    @Inject
    private AccountModel accountModel;

    @Inject
    private CurrencyInformationService currencyInformationService;

    private BigDecimal amount;
    private List<Currency> availableCurrencies;
    private List<Currency> allCurrencies;
    private Currency selectedCurrency;
    private Currency outputCurrency;

    @PostConstruct
    private void setCurrencies() {
        // TODO use java streams
        List<Currency> accountCurrencies = new ArrayList<>();
        for(Wallet wallet : accountModel.getLoggedInAccount().getWallets()) {
            accountCurrencies.add(wallet.getCurrency());
        }
        availableCurrencies = accountCurrencies;
        allCurrencies = currencyInformationService.getAllCurrencies();
        selectedCurrency = allCurrencies.get(0);
        outputCurrency = accountCurrencies.get(0);

    }

    public ExchangeModel() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<Currency> getAllCurrencies() {
        return allCurrencies;
    }

    public void setAllCurrencies(List<Currency> allCurrencies) {
        this.allCurrencies = allCurrencies;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(Currency selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public List<Currency> getAvailableCurrencies() {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(List<Currency> availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public Currency getOutputCurrency() {
        return outputCurrency;
    }

    public void setOutputCurrency(Currency outputCurrency) {
        this.outputCurrency = outputCurrency;
    }

    public CurrencyInformationService getCurrencyInformationService() {
        return currencyInformationService;
    }

    public void setCurrencyInformationService(CurrencyInformationService currencyInformationService) {
        this.currencyInformationService = currencyInformationService;
    }
}
