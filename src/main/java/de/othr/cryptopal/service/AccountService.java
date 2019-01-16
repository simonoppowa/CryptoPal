package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.AccountType;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Wallet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.logging.Level;

@ApplicationScoped
public class AccountService extends AbstractService<Account> {

    @Inject
    private CurrencyInformationService currencyInformationService;

    public AccountService() {
        super(Account.class);
    }

    @PostConstruct
    @Transactional
    public void init() {
        logger.log(Level.INFO, "Initializing AccountService");
        if(getAccountByEmail("administration@cryptopal.com") == null) {
            createInitAccounts();
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public boolean createNewAccount(@NotNull Account account) {

        try {
            em.persist(account);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Transactional
    public Account getAccountByEmail(String email) {
        TypedQuery<Account> typedQuery = em.createNamedQuery(Account.FINDBYEMAIL, Account.class);
        typedQuery.setParameter("email", email);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public Account getAccountByCredintials(String email, String password) {

        TypedQuery<Account> typedQuery = em.createNamedQuery(Account.FINDBYCREDETIALS, Account.class);
        typedQuery.setParameter("email", email);
        typedQuery.setParameter("password", password);

        try {
            return typedQuery.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public Wallet getAccountWallet(String email, Currency currency) {
        TypedQuery<Wallet> typedQuery = em.createNamedQuery(Wallet.FINDWALLETBYACCOUNTCURRENCY, Wallet.class);
        typedQuery.setParameter("email", email);
        typedQuery.setParameter("currency" , currency.getCurrencyId());

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }

    @Transactional
    public void updateAccount(Account account) {
        merge(account);
    }

    public boolean checkIfAccountAlreadyExists(String email){
        Account account = getAccountByEmail(email);

        return account != null;
    }

    @Transactional
    public void createInitAccounts() {

        // CREATE ADMINISTRATION ACCOUNT
        Account cryptoPalAccount = new Account("CryptoPal", "Account",
                "administration@cryptopal.com", "123",
                currencyInformationService.getCurrencyFromMap("USD"), AccountType.ADMINISTRATION);

        cryptoPalAccount.getWalletByCurrency(currencyInformationService.getCurrencyFromMap("USD")).setCredit(new BigDecimal(10000));
        cryptoPalAccount.getWallets().add(new Wallet("EUR", cryptoPalAccount, new BigDecimal(100000),
                currencyInformationService.getCurrencyFromMap("EUR")));
        cryptoPalAccount.getWallets().add(new Wallet("JPY", cryptoPalAccount, new BigDecimal(100000),
                currencyInformationService.getCurrencyFromMap("JPY")));

        createNewAccount(cryptoPalAccount);

        // CREATE DUMMY ACCOUNTS
        Account dummy1 = new Account("Max", "Mustermann", "max.mustermann@gmx.de", "123",
                currencyInformationService.getCurrencyFromMap("EUR"), AccountType.PRIVATE);

        createNewAccount(dummy1);

        Account dummy2 = new Account("Manfred", "Mueller", "manfred.mueller@gmail.de", "321",
                currencyInformationService.getCurrencyFromMap("USD"), AccountType.PRIVATE);

        createNewAccount(dummy2);

        // CREATE PARTNER ACCOUNTS
        Account partner1 = new Account("BlueBox", "Business", "business@blueboxgames.de", "12345",
                currencyInformationService.getCurrencyFromMap("USD"), AccountType.PARTNER_BUSINESS);
        partner1.getWallets().add(new Wallet("EUR", cryptoPalAccount, new BigDecimal(500),
                currencyInformationService.getCurrencyFromMap("EUR")));

        createNewAccount(partner1);


        Account partner2 = new Account("BlackCaste", "Business", "business@blackcastle.de", "12345",
                currencyInformationService.getCurrencyFromMap("USD"), AccountType.PARTNER_BUSINESS);
        partner2.getWallets().add(new Wallet("EUR", cryptoPalAccount, new BigDecimal(500),
                currencyInformationService.getCurrencyFromMap("EUR")));

        createNewAccount(partner2);

        logger.log(Level.INFO, "Init Accounts created");
    }

    private void checkIfAccountNameIsBanned() {
        // TODO ban account names with "admin", "support"...
    }
}
