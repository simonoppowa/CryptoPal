package de.othr.cryptopal.service;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;

@ApplicationScoped
public class InitService extends AbstractService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CurrencyInformationService currencyInformationService;

    @Inject
    private AccountService accountService;

    public void init() {
        logger.log(Level.INFO, "InitService called");

        //accountService.createDummies();

//        currencyInformationService.getAllFiatCurrencies();
//        currencyInformationService.getAllCryptoCurrencies();
    }
}
