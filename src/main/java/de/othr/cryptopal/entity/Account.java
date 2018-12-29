package de.othr.cryptopal.entity;

import javax.persistence.*;
import java.io.Serializable;
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
    @OneToMany(mappedBy = "account")
    private List<Wallet> wallets;
    @OneToOne
    private Wallet paymentWallet;
    private String defaultCurrencyId;
    @ManyToOne
    private Transaction transaction;
    private boolean isBusinessAccount;
    private boolean isFrozen;

    public Account() {
    }

    public Account(String firstname, String lastname, String email, String password, String defaultCurrencyId,
                   boolean isBusinessAccount) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.defaultCurrencyId = defaultCurrencyId;
        this.isBusinessAccount = isBusinessAccount;
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

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public Wallet getPaymentWallet() {
        return paymentWallet;
    }

    public void setPaymentWallet(Wallet paymentWallet) {
        this.paymentWallet = paymentWallet;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getDefaultCurrencyId() {
        return defaultCurrencyId;
    }

    public void setDefaultCurrencyId(String defaultCurrencyId) {
        this.defaultCurrencyId = defaultCurrencyId;
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
        return accountId + " " + firstname + " " + firstname + " " + email + " " + password + " " + defaultCurrencyId +
                " " + isBusinessAccount;
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
