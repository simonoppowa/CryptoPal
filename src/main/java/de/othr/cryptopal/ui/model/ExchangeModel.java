package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Wallet;
import de.othr.cryptopal.service.AccountService;
import de.othr.cryptopal.service.CurrencyInformationService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@SessionScoped
@Named
public class ExchangeModel extends AbstractModel {

    @Inject
    private AccountModel accountModel;

    @Inject
    private CurrencyInformationService currencyInformationService;

    private BigDecimal amount;
    private BigDecimal outputAmount;
    private List<Currency> availableCurrencies;
    private List<Currency> allCurrencies;
    private Currency selectedCurrency;
    private Currency outputCurrency;

    @PostConstruct
    private void setCurrencies() {
        // TODO use java streams
        List<Currency> accountCurrencies = new ArrayList<>();
        for(Wallet wallet : accountModel.getLoggedInAccount().getWallets()) {
            accountCurrencies.add(currencyInformationService.getCurrencyFromMap(wallet.getCurrency().getCurrencyId()));
        }
        allCurrencies = currencyInformationService.getAllCurrencies();
        availableCurrencies = accountCurrencies;
        selectedCurrency = allCurrencies.get(0);
        outputCurrency = accountCurrencies.get(0);
        outputAmount = new BigDecimal(0);
    }

    public ExchangeModel() {
    }

    public void calculateOutputAmount() {
        logger.log(Level.INFO, "Calculating new outputAmount " + amount
                + " " + selectedCurrency.getCurrencyId() + " " + outputCurrency.getCurrencyId());

        outputAmount = amount.multiply(new BigDecimal(outputCurrency.getExchangeRate()));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOutputAmount() {
        return outputAmount;
    }

    public void setOutputAmount(BigDecimal outputAmount) {
        this.outputAmount = outputAmount;
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
        calculateOutputAmount();
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
        calculateOutputAmount();
    }

    public CurrencyInformationService getCurrencyInformationService() {
        return currencyInformationService;
    }

    public void setCurrencyInformationService(CurrencyInformationService currencyInformationService) {
        this.currencyInformationService = currencyInformationService;
    }
}
