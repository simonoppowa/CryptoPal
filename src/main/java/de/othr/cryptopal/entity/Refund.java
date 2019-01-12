package de.othr.cryptopal.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Refund extends Transaction {

    private String reason;
    @OneToOne
    private Payment payment;

    public Refund() {
    }

    public Refund(Wallet senderWallet, Wallet receiverWallet, BigDecimal amount, Currency paymentCurrency, Date timestamp, String comment, Payment payment) {
        super(senderWallet, receiverWallet, amount, paymentCurrency, timestamp);
        this.reason = comment;
        this.payment = payment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
