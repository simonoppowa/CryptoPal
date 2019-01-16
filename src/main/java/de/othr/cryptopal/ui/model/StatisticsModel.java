package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.service.CurrencyInformationService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@SessionScoped
@Named
public class StatisticsModel extends AbstractModel{


    @Inject
    private AccountModel accountModel;

    @Inject
    private CurrencyInformationService currencyInformationService;


    public BigDecimal getFullAccountPrice() {
        return currencyInformationService.calculateFullPortfolioPrice(accountModel.getLoggedInAccount());
    }

}
