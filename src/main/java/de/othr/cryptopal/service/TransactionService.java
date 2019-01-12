package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.*;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;

public abstract class TransactionService<T> extends AbstractService<Transaction> {

    @Inject
    protected AccountService accountService;

    @Inject
    protected CurrencyInformationService currencyInformationService;

    public TransactionService() {
    }

    public TransactionService(Class<T> entityClass) {
        super(Transaction.class);
    }

    @Transactional
    Transaction findTransactionById(long transactionId) {
        TypedQuery<Transaction> typedQuery = em.createNamedQuery(Transaction.FINDBYID, Transaction.class);
        typedQuery.setParameter("id", transactionId);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    void executeTransaction(Transaction transaction, Account receiver) {
        Currency transactionCurrency = transaction.getPaymentCurrency();
        Account sender = transaction.getSenderWallet().getAccount();

        // Attach senderWallet
        Wallet senderWallet = accountService.getAccountWallet(transaction.getSenderWallet()
                .getAccount().getEmail(), transactionCurrency);
        Wallet receiverWallet = transaction.getReceiverWallet();
        BigDecimal amount = transaction.getAmount();

        if(receiverWallet == null) {
            receiverWallet = new Wallet(transactionCurrency.getCurrencyName(),
                    receiver, new BigDecimal(0.00), transactionCurrency);
            em.persist(receiverWallet);
            receiver.getWallets().add(receiverWallet);
        }

        // SEND MONEY
        senderWallet.setCredit(senderWallet.getCredit().subtract(amount));
        receiverWallet.setCredit(receiverWallet.getCredit().add(amount));

        em.persist(transaction);

        // TODO fix unique index error
        //sender.getTransactions().add(transaction);

        receiver.getTransactions().add(transaction);

        em.flush();
    }

}
