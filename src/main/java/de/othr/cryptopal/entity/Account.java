package de.othr.cryptopal.entity;

import javax.persistence.*;
import java.io.Serializable;

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
    private String defaultCurrencyId;
    private boolean isBusinessAccount;

    public Account() {
    }

    // TODO remove
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
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
