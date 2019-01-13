package de.othr.cryptopal.entity;

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
    public String getDetails() {
        return comment;
    }
}
