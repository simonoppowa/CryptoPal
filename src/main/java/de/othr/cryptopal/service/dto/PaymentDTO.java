package de.othr.cryptopal.service.dto;

import de.othr.cryptopal.entity.Payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * A Payment class to share with PaymentService to not expose entity classes
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentDTO {

    private long transactionId;
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private String currencyId;
    private Date transactionTime;
    private long taxInPercentage;
    private long paymentFeeInPercentage;
    private String transactionComment;


    public PaymentDTO(Payment payment) {
        this.transactionId = payment.getTransactionId();
        this.senderEmail = payment.getSenderWallet().getAccount().getEmail();
        this.receiverEmail = payment.getReceiverWallet().getAccount().getEmail();
        this.amount = payment.getAmount();
        this.currencyId = payment.getPaymentCurrency().getCurrencyId();
        this.transactionTime = payment.getTimestamp();
        this.taxInPercentage = payment.getTax();
        this.paymentFeeInPercentage = payment.getPaymentFee();
        this.transactionComment = payment.getComment();
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public long getTaxInPercentage() {
        return taxInPercentage;
    }

    public void setTaxInPercentage(long taxInPercentage) {
        this.taxInPercentage = taxInPercentage;
    }

    public long getPaymentFeeInPercentage() {
        return paymentFeeInPercentage;
    }

    public void setPaymentFeeInPercentage(long paymentFeeInPercentage) {
        this.paymentFeeInPercentage = paymentFeeInPercentage;
    }

    public String getTransactionComment() {
        return transactionComment;
    }

    public void setTransactionComment(String transactionComment) {
        this.transactionComment = transactionComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentDTO that = (PaymentDTO) o;

        return transactionId == that.transactionId;
    }

    @Override
    public int hashCode() {
        return (int) (transactionId ^ (transactionId >>> 32));
    }
}
