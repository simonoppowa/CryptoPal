package de.othr.cryptopal.entity;

import de.othr.blackcastle.OrderService;
import de.othr.blackcastle.OrderServiceService;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment extends Transaction{

    private String comment;
    private long tax;
    private long paymentFee;

    public Payment() {
    }

    public Payment(Wallet senderWallet, Wallet receiverWallet, BigDecimal amount, Currency paymentCurrency,
                   Date timestamp, String comment, long tax, long paymentFee) {
        super(senderWallet, receiverWallet, amount, paymentCurrency, timestamp);
        this.comment = comment;
        this.tax = tax;
        this.paymentFee = paymentFee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
        this.tax = tax;
    }

    public long getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(long paymentFee) {
        this.paymentFee = paymentFee;
    }

    @Override
    public String getTransactionType() {
        return "Payment";
    }

    @Override
    public String getDetails() {
        // If the payment is made to a partner business, the exact article name is returned
        if(getReceiverWallet().getAccount().isPartnerAccount()) {
            long gameId;
            try {
                gameId = Long.parseLong(String.valueOf(comment));
            } catch (Exception ex) {
                return comment;
            }
            OrderServiceService service = new OrderServiceService();
            OrderService stub = service.getOrderServicePort();
            return stub.getGameTitel(gameId);
        }
        return comment;
    }
}
