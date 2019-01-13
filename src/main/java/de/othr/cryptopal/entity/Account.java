package de.othr.cryptopal.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = Account.FINDALL, query = "SELECT a FROM Account a"),
        @NamedQuery(name = Account.FINDBYCREDETIALS, query = "SELECT a FROM Account a WHERE a.email = :email AND a.password = :password"),
        @NamedQuery(name = Account.FINDBYEMAIL, query = "SELECT a FROM Account a WHERE a.email = :email"),
        @NamedQuery(name = Account.FINDBYID,query = "SELECT a FROM Account a WHERE a.id = :id")
})
public class Account implements Serializable {

    public static final String FINDALL = "Account.findAll";
    public static final String FINDBYCREDETIALS = "Account.findByCredentials";
    public static final String FINDBYEMAIL = "Account.findByEmail";
    public static final String FINDBYID = "Account.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Wallet> wallets;
    @OneToOne
    private Wallet paymentWallet;
    @OneToOne
    private Currency defaultCurrency;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Transaction> transactions;
    private boolean isBusinessAccount;
    private boolean isFrozen;

    public Account() {
    }

    public Account(String firstname, String lastname, String email, String password, Currency defaultCurrency,
                   boolean isBusinessAccount) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.defaultCurrency = defaultCurrency;
        this.isBusinessAccount = isBusinessAccount;

        setDetails();
    }

    public void setDetails() {
        this.wallets = new ArrayList<>();
        Wallet defaultWallet = new Wallet("Default", this, new BigDecimal(0.00), defaultCurrency);
        this.wallets.add(defaultWallet);
        this.paymentWallet = defaultWallet;
        this.transactions = new ArrayList<>();
        isFrozen = false;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public Wallet getWalletByCurrency(Currency currency) {

        for(Wallet tempWallet : wallets) {
            if(tempWallet.getCurrency().equals(currency)) {
                return tempWallet;
            }
        }
        return null;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public Wallet getPaymentWallet() {
        return paymentWallet;
    }

    public void setPaymentWallet(Wallet paymentWallet) {
        this.paymentWallet = paymentWallet;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrencyId(Currency defaultCurrencyId) {
        this.defaultCurrency = defaultCurrencyId;
    }

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    @Override
    public String toString() {
        return accountId + " " + firstname + " " + lastname + "\n   "
                + email + " " + password + "\n   "
                + defaultCurrency.getCurrencyName() + " " + isBusinessAccount + "\n   "
                + "Wallet: " + paymentWallet.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return (int) (accountId ^ (accountId >>> 32));
    }
}
