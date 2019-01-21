package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.*;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SessionScoped
public class TransferService extends TransactionService<Transfer> {

    public TransferService() {
        super(Transfer.class);
    }

    @Transactional
    public void setStartMoney(Account account) {
        Currency currency = currencyInformationService.getCurrencyFromMap("USD");
        sendMoney(accountService.getAccountByEmail("administration@cryptopal.com").getWalletByCurrency(currency),
                account.getEmail(), new BigDecimal(200), "Start money");
    }

    @Transactional
    public void sendMoney(Wallet senderWallet, String receiverEmail, BigDecimal amount, String message) {
        Currency currency = senderWallet.getCurrency();
        Wallet receiverWallet = accountService.getAccountByEmail(receiverEmail).getWalletByCurrency(currency);

        Transfer transfer = new Transfer(senderWallet,
                receiverWallet, amount, currency,
                new Date(System.currentTimeMillis()), message);

        executeTransaction(transfer, accountService.getAccountByEmail(receiverEmail));
    }

}
