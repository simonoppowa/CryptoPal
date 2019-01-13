package de.othr.cryptopal.service.dto;

import de.othr.cryptopal.entity.Refund;

/**
 * A Refund class to share with PaymentService to not expose entity classes
 */
public class RefundDTO {

    private long transactionId;
    private PaymentDTO refundedPayment;
    private String reason;

    public RefundDTO(Refund refund) {
        this.transactionId = refund.getTransactionId();
        this.refundedPayment = new PaymentDTO(refund.getPayment());
        this.reason = refund.getReason();
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentDTO getRefundedPayment() {
        return refundedPayment;
    }

    public void setRefundedPayment(PaymentDTO refundedPayment) {
        this.refundedPayment = refundedPayment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundDTO refundDTO = (RefundDTO) o;

        return transactionId == refundDTO.transactionId;
    }

    @Override
    public int hashCode() {
        return (int) (transactionId ^ (transactionId >>> 32));
    }
}
