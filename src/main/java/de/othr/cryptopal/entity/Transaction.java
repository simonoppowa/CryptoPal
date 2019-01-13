package de.othr.cryptopal.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity // Cannot be set as MappedSuperclass
@NamedQueries({
        @NamedQuery(name = Transaction.FINDALL, query = "SELECT t FROM Transaction t"),
        @NamedQuery(name = Transaction.FINDBYID, query = "SELECT t FROM Transaction t WHERE t.id = :id")
})
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction implements Serializable {

    public static final String FINDALL = "Transaction.findAll";
    public static final String FINDBYID = "Transaction.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    @OneToOne
    private Wallet senderWallet;
    @OneToOne
    private Wallet receiverWallet;
    private BigDecimal amount;
    @OneToOne
    private Currency paymentCurrency;
    private Date timestamp;

    public Transaction() {
    }

    public Transaction(Wallet senderWallet, Wallet receiverWallet, BigDecimal amount, Currency paymentCurrency, Date timestamp) {
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
        this.amount = amount;
        this.paymentCurrency = paymentCurrency;
        this.timestamp = timestamp;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Wallet getSenderWallet() {
        return senderWallet;
    }

    public void setSenderWallet(Wallet senderWallet) {
        this.senderWallet = senderWallet;
    }

    public Wallet getReceiverWallet() {
        return receiverWallet;
    }

    public void setReceiverWallet(Wallet receiverWallet) {
        this.receiverWallet = receiverWallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(Currency paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public abstract String getDetails();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return transactionId == that.transactionId;
    }

    @Override
    public int hashCode() {
        return (int) (transactionId ^ (transactionId >>> 32));
    }
}
