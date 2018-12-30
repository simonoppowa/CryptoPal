package de.othr.cryptopal.service;


import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@ApplicationScoped
@ManagedBean
public class InitService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CurrencyInformationService currencyInformationService;

    @Inject
    private AccountService accountService;

    public void init() {
        System.out.println("InitService called");

        accountService.createDummies();

//        currencyInformationService.getAllFiatCurrencies();
//        currencyInformationService.getAllCryptoCurrencies();
    }
}
