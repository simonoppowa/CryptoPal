package de.othr.cryptopal.entity;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Transfer extends Transaction {
    private String transferMessage;

    public Transfer() {
    }

    public Transfer(Wallet senderWallet, Wallet receiverWallet, BigDecimal amount, Currency paymentCurrency,
                    Date timestamp, String transferMessage) {
        super(senderWallet, receiverWallet, amount, paymentCurrency, timestamp);
        this.transferMessage = transferMessage;
    }

    public String getTransferMessage() {
        return transferMessage;
    }

    public void setTransferMessage(String transferMessage) {
        this.transferMessage = transferMessage;
    }

    @Override
    public String getDetails() {
        return transferMessage;
    }
}
