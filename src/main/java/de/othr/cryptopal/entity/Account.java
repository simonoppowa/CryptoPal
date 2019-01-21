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
        @NamedQuery(name = Account.FINDBYID, query = "SELECT a FROM Account a WHERE a.id = :id"),
        @NamedQuery(name = Account.FINDADMINISTRATION, query = "SELECT a FROM Account a WHERE a.accountType = :type")
})
public class Account implements Serializable {

    public static final String FINDALL = "Account.findAll";
    public static final String FINDBYCREDETIALS = "Account.findByCredentials";
    public static final String FINDBYEMAIL = "Account.findByEmail";
    public static final String FINDBYID = "Account.findById";
    public static final String FINDADMINISTRATION = "Account.findAdmin";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Wallet> wallets;
    @OneToOne
    private Wallet paymentWallet;
    @OneToOne
    private Currency defaultCurrency;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Transaction> transactions;
    private boolean isFrozen;
    private AccountType accountType;

    public Account() {
    }

    public Account(String firstname, String lastname, String email, String password, Currency defaultCurrency,
                   AccountType accountType) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.defaultCurrency = defaultCurrency;
        this.accountType = accountType;

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


    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public boolean isBusinessAccount() {
        return accountType == AccountType.BUSINESS || accountType == AccountType.PARTNER_BUSINESS;
    }

    public boolean isPartnerAccount() {
        return accountType == AccountType.PARTNER_BUSINESS;
    }

    @Override
    public String toString() {
        String defaultCurrencyString;
        if(defaultCurrency != null) {
            defaultCurrencyString = defaultCurrency.toString();
        } else {
            defaultCurrencyString = "No default currency defined";
        }
        String paymentWalletString;
        if(paymentWallet != null) {
            paymentWalletString = paymentWallet.toString();
        } else {
            paymentWalletString = "No payment wallet defined";
        }
        return accountId + " " + firstname + " " + lastname + "\n   "
                + email  + "\n   "
                + defaultCurrencyString  + "\n   "
                + "Wallet: " + paymentWalletString;
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
