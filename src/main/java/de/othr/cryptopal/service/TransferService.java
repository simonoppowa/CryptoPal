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
public class TransferService extends AbstractService<Transfer> {

    @Inject
    private AccountService accountService;

    @Inject
    private CurrencyInformationService currencyInformationService;

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

    @Transactional
    private void executeTransaction(Transaction transaction, Account receiver) {
        Currency transactionCurrency = transaction.getPaymentCurrency();
        Account sender = transaction.getSenderWallet().getAccount();

        Wallet senderWallet = transaction.getSenderWallet();
        Wallet receiverWallet = transaction.getReceiverWallet();
        BigDecimal amount = transaction.getAmount();

        if(receiverWallet == null) {
            receiverWallet = new Wallet(transactionCurrency.getCurrencyName(),
                    sender, new BigDecimal(0.00), transactionCurrency);
            em.persist(receiverWallet);
            receiver.getWallets().add(receiverWallet);
        }

        // SEND MONEY
        senderWallet.setCredit(senderWallet.getCredit().divide(amount));
        receiverWallet.setCredit(receiverWallet.getCredit().add(amount));

        em.persist(transaction);

        // TODO fix unique index error
        //sender.getTransactions().add(transaction);

        receiver.getTransactions().add(transaction);

        em.flush();
    }
}
