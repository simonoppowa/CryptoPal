package de.othr.cryptopal.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long walletId;
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2") // TODO generate address
    private String walletAddress;
    private String walletName;
    @ManyToOne
    private Account account;
    private BigDecimal credit;
    @OneToOne(cascade= CascadeType.ALL)
    private Currency currency;

    public Wallet() {
    }

    public Wallet(String walletName, Account account, BigDecimal credit, Currency currency) {
        this.walletName = walletName;
        this.account = account;
        this.credit = credit;
        this.currency = currency;
        this.walletAddress = "0x345kj3245k234k";
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
    public String toString() {
        return walletId + " " + walletName + " " + walletAddress + " " + currency.getCurrencyName();
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
