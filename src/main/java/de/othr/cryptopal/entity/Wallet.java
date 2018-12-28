package de.othr.cryptopal.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Wallet implements Serializable {

    @Id
    private long walletId;
    private String walletAddress;
    private String walletName;
    @ManyToOne
    private Account account;
    private BigDecimal credit;
    @OneToOne
    private Currency currency;

    public Wallet() {
    }

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAdress) {
        this.walletAddress = walletAdress;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wallet wallet = (Wallet) o;

        return walletId == wallet.walletId;
    }

    @Override
    public int hashCode() {
        return (int) (walletId ^ (walletId >>> 32));
    }
}
