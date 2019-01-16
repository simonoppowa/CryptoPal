package de.othr.cryptopal.entity;

import de.othr.cryptopal.entity.util.WalletAddressGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name = Wallet.FINDWALLETBYACCOUNTCURRENCY, query = "SELECT w FROM Wallet w WHERE w.account.email = :email AND w.currency.currencyId = :currency")
})
public class Wallet implements Serializable {

    public static final String FINDWALLETBYACCOUNTCURRENCY = "Account.findAccountCurrency";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long walletId;
//    @GenericGenerator(name = "wallet_address", strategy = "de.othr.cryptopal.entity.util.WalletAddressGenerator")
//    @GeneratedValue(generator = "wallet_address") // Doesn't work with non id attributes
    private String walletAddress;
    private String walletName;
    @ManyToOne
    private Account account;
    private BigDecimal credit;
    @OneToOne
    private Currency currency;

    public Wallet() {
    }

    public Wallet(String walletName, Account account, BigDecimal credit, Currency currency) {
        this.walletAddress = WalletAddressGenerator.generate();
        this.walletName = walletName;
        this.account = account;
        this.credit = credit;
        this.currency = currency;
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
