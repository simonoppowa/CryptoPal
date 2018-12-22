package de.othr.cryptopal.service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
@ManagedBean
public class InitService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CurrencyInformationService currencyInformationService;

    public void init() throws Exception {
        System.out.println("InitService called");
        currencyInformationService.getAllFiatCurrencies();
        currencyInformationService.getAllCryptoCurrencies();
    }
}
