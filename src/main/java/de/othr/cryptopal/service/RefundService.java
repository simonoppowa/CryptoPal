package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Payment;
import de.othr.cryptopal.entity.Refund;
import de.othr.cryptopal.entity.Transaction;
import de.othr.cryptopal.exception.RefundException;
import de.othr.cryptopal.service.dto.RefundDTO;

import javax.enterprise.context.SessionScoped;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.Date;

@WebService
@SessionScoped
public class RefundService extends TransactionService<Refund> {

    @WebMethod
    @Transactional
    public RefundDTO refund(@WebParam(name = "email") String email,
                            @WebParam(name = "password") String password,
                            @WebParam(name = "transactionId") long transactionId,
                            @WebParam(name = "reason") String reason) throws RefundException {

        Account sender = accountService.getAccountByCredintials(email, password);

        if(sender == null) {
            throw new RefundException("No sender with email and password combination found");
        }

        Transaction transaction = findTransactionById(transactionId);

        // Check if already refunded

        // TODO to TransactionService
        if(!(transaction instanceof Payment) || !(transaction.getSenderWallet().getAccount() == sender)) {
            throw new RefundException("No payment with id found");
        } else {
            Refund refund = new Refund(transaction.getSenderWallet(), transaction.getReceiverWallet(),
                    transaction.getAmount(), transaction.getPaymentCurrency(), new Date(System.currentTimeMillis()),
                    reason, (Payment) transaction);

            executeTransaction(refund, transaction.getSenderWallet().getAccount());

            return new RefundDTO(refund);
        }
    }
}
