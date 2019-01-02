package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Transfer;
import de.othr.cryptopal.entity.Wallet;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SessionScoped
public class TransferService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AccountService accountService;

    @Inject
    private CurrencyInformationService currencyInformationService;

    @Transactional
    public void setStartMoney(Account account) {
        Transfer start = new Transfer(accountService.getAccountByEmail("administration@cryptopal.com").getWallets().get(0),
                account.getWallets().get(0), new BigDecimal(100), currencyInformationService.getCurrencyFromMap("USD"),
                new Date(System.currentTimeMillis()), "Start Money");

        account.getTransactions().add(start);

        em.persist(start);
    }

    public void sendMoney(Account receiver, Wallet wallet, BigDecimal amount) {
        Wallet receiverWallet = accountService.getAccountWallet(receiver.getEmail(), wallet.getCurrency());

        if(receiverWallet == null) {
            receiverWallet = new Wallet(wallet.getCurrency().getCurrencyName(),
                    accountService.getAccountByEmail(receiver.getEmail()),
                    new BigDecimal(0.00), wallet.getCurrency());
            receiver.getWallets().add(receiverWallet);
        }

        // SEND MONEY
        wallet.setCredit(wallet.getCredit().divide(amount));

        receiverWallet.setCredit(receiverWallet.getCredit().divide(amount));

    }
}
