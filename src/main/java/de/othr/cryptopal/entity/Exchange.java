package de.othr.cryptopal.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Exchange extends Transaction {

    private BigDecimal toAmount;
    @OneToOne
    private Currency toCurrency;
    private String exchangeDetails;

    public Exchange() {
    }

    public Exchange(Wallet senderWallet, Wallet receiverWallet, BigDecimal amount, Currency paymentCurrency,
                    Date timestamp, BigDecimal toAmount, Currency toCurrency) {
        super(senderWallet, receiverWallet, amount, paymentCurrency, timestamp);
        this.toAmount = toAmount;
        this.toCurrency = toCurrency;
        this.exchangeDetails = senderWallet.getCurrency().getCurrencyId() + " -> "
                + receiverWallet.getCurrency().getCurrencyId();
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public String getExchangeDetails() {
        return exchangeDetails;
    }

    public void setExchangeDetails(String exchangeDetails) {
        this.exchangeDetails = exchangeDetails;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Override
    public String getTransactionType() {
        return "Exchange";
    }

    @Override
    public String getDetails() {
        return exchangeDetails;
    }
}
