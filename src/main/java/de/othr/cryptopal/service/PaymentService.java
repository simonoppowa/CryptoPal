package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Payment;

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
    public Payment pay(@WebParam(name = "senderEmail") String senderEmail,
                    @WebParam(name = "senderPassword") String senderPassword,
                    @WebParam(name = "receiverEmail") String receiverEmail,
                    @WebParam(name = "amount") BigDecimal amount,
                    @WebParam(name = "currencyId") String currencyCode,
                    @WebParam(name = "paymentComment") String comment,
                    @WebParam(name = "taxInPercentage") double tax) {

        Account sender = accountService.getAccountByCredintials(senderEmail, senderPassword);

        if(sender == null) {
            // Return account not found
            System.out.println("Sender not found");
            return null;
        }

        Account receiver = accountService.getAccountByEmail(receiverEmail);

        if(receiver == null) {
            System.out.println("Receiver not found");
            return null;
        }

        // Check amount


        // Check currency
        Currency paymentCurrency = currencyInformationService.getCurrencyFromMap(currencyCode);

        if(paymentCurrency == null) {
            System.out.println("Currency not found");
            return null;
        }

        // TODO tax and paymentFee
        Payment payment = new Payment(sender.getWalletByCurrency(paymentCurrency), receiver.getWalletByCurrency(paymentCurrency),
                amount, paymentCurrency, new Date(System.currentTimeMillis()), comment, 0, 0);

        executeTransaction(payment, receiver);

        return payment;
    }
}
