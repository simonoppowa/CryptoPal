package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Payment;
import de.othr.cryptopal.entity.Transaction;
import de.othr.cryptopal.exception.PaymentException;
import de.othr.cryptopal.service.dto.PaymentDTO;

import javax.enterprise.context.SessionScoped;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@WebService
@SessionScoped
public class PaymentService extends TransactionService<Payment> {

    @WebMethod
    @Transactional
    public PaymentDTO pay(@WebParam(name = "senderEmail") String senderEmail,
                          @WebParam(name = "senderPassword") String senderPassword,
                          @WebParam(name = "receiverEmail") String receiverEmail,
                          @WebParam(name = "amount") BigDecimal amount,
                          @WebParam(name = "currencyId") String currencyCode,
                          @WebParam(name = "paymentComment") String comment,
                          @WebParam(name = "taxInPercentage") double tax) throws PaymentException {

        Account sender = accountService.getAccountByCredintials(senderEmail, senderPassword);

        if(sender == null) {
            // Return account not found
            throw new PaymentException("No sender with email and password combination found");
        }

        Account receiver = accountService.getAccountByEmail(receiverEmail);

        if(receiver == null) {
            throw new PaymentException("No receiver with email found");
        }

        // Check currency
        Currency paymentCurrency = currencyInformationService.getCurrencyFromMap(currencyCode);

        if(paymentCurrency == null) {
            throw new PaymentException("No currency with id " + currencyCode + " found");
        }

        // Check sender wallet
        if(sender.getWalletByCurrency(paymentCurrency) == null) {
            throw new PaymentException("Sender has no wallet with currency " + currencyCode);
        }

        // Check amount
        BigDecimal newCredit = sender.getWalletByCurrency(paymentCurrency).getCredit().subtract(amount);
        if(newCredit == null) {
            throw new PaymentException("Not a valid amount input");
        } else if(newCredit.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException("Not enough credit to pay");
        }

        // TODO tax and paymentFee
        Payment payment = new Payment(sender.getWalletByCurrency(paymentCurrency),
                receiver.getWalletByCurrency(paymentCurrency),
                amount, paymentCurrency, new Date(System.currentTimeMillis()), comment, 0, 0);

        executeTransaction(payment, receiver);

        // Assign newly created wallet if necessary
        if(payment.getReceiverWallet() == null) {
            payment.setReceiverWallet(receiver.getWalletByCurrency(paymentCurrency));
        }

        return new PaymentDTO(payment);
    }

    @Transactional
    public PaymentDTO checkPaymentInfo(@WebParam(name = "transactionId") long transactionId,
                                       @WebParam(name = "email") String email,
                                       @WebParam(name = "password") String password) throws PaymentException {

        Account account = accountService.getAccountByCredintials(email, password);

        if(account == null) {
            throw new PaymentException("No account with email password combination found");
        }

        Transaction transaction = findTransactionById(transactionId);

        if(transaction instanceof Payment && (transaction.getSenderWallet().getAccount() == account
                || transaction.getReceiverWallet().getAccount() == account)) {
            return new PaymentDTO((Payment) transaction);
        } else {
            throw new PaymentException("No payment with id found");
        }
    }
}
