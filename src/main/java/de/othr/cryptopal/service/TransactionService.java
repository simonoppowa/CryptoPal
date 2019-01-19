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
    synchronized void executeTransaction(Transaction transaction, Account receiver) {
        Currency transactionCurrency = transaction.getPaymentCurrency();
        Account sender = transaction.getSenderWallet().getAccount();

        // Attach senderWallet
        Wallet senderWallet = accountService.getAccountWallet(transaction.getSenderWallet()
                .getAccount().getEmail(), transactionCurrency);
        Wallet receiverWallet = accountService.getAccountWallet(receiver.getEmail(), transactionCurrency);
        BigDecimal amount = transaction.getAmount();

        // Create new wallet for currency
        if(receiverWallet == null) {
            receiverWallet = createNewWallet(receiver, transactionCurrency);
            receiver.getWallets().add(receiverWallet);
        }

        // SEND MONEY
        senderWallet.setCredit(senderWallet.getCredit().subtract(amount));
        receiverWallet.setCredit(receiverWallet.getCredit().add(amount));

        em.persist(transaction);

        sender.getTransactions().add(transaction);
        receiver.getTransactions().add(transaction);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    synchronized void executeTwoCurrencyTransaction(Transaction transaction) {
        if(transaction instanceof Exchange) {
            Exchange exchange = (Exchange) transaction;

            BigDecimal fromAmount = exchange.getAmount();
            BigDecimal toAmount  = exchange.getToAmount();

            Account sender = accountService.getAccountByEmail(exchange.getSenderWallet().getAccount().getEmail());
            Account receiver = accountService.getAccountByEmail(exchange.getReceiverWallet().getAccount().getEmail());

            // Attach
            Wallet fromSenderWallet = sender.getWalletByCurrency(exchange.getPaymentCurrency());
            Wallet toSenderWallet = sender.getWalletByCurrency(exchange.getToCurrency());

            Wallet fromReceiverWallet = receiver.getWalletByCurrency(exchange.getPaymentCurrency());
            Wallet toReceiverWallet = receiver.getWalletByCurrency(exchange.getToCurrency());

            if(toSenderWallet == null) {
                toSenderWallet = createNewWallet(sender, exchange.getToCurrency());
                sender.getWallets().add(toSenderWallet);
            }

            // EXCHANGE
            fromSenderWallet.setCredit(fromSenderWallet.getCredit().subtract(fromAmount));
            fromReceiverWallet.setCredit(fromReceiverWallet.getCredit().add(fromAmount));

            toSenderWallet.setCredit(toSenderWallet.getCredit().add(toAmount));
            toReceiverWallet.setCredit(toReceiverWallet.getCredit().subtract(toAmount));

            em.persist(exchange);

            sender.getTransactions().add(exchange);
            receiver.getTransactions().add(exchange);
        }
    }

    private Wallet createNewWallet(Account account, Currency currency) {
        return new Wallet(currency.getCurrencyName(), account, new BigDecimal(0), currency);
    }

}
