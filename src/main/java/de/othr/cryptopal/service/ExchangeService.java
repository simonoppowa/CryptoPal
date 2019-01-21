package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Exchange;
import de.othr.cryptopal.entity.Wallet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@SessionScoped
public class ExchangeService extends TransactionService<Exchange> {

    private Account administration;

    @PostConstruct
    private void getAdministration() {
        administration = accountService.getAdministrationAccount();
    }


    public ExchangeService() {
        super(Exchange.class);
    }

    @Transactional
    public void exchangeCurrency(Wallet senderWallet, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        Exchange exchange = new Exchange(senderWallet, administration.getWalletByCurrency(toCurrency),
                fromAmount, senderWallet.getCurrency(), new Date(System.currentTimeMillis()), toAmount, toCurrency);

        executeTwoCurrencyTransaction(exchange);
    }
}
