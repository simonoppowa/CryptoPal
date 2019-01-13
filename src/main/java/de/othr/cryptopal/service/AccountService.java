package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;
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

@ApplicationScoped
public class AccountService extends AbstractService<Account> {

    @Inject
    private CurrencyInformationService currencyInformationService;

    public AccountService() {
        super(Account.class);
    }

    @PostConstruct
    @Transactional
    public void createDummies() {
        Account cryptoPalAccount = new Account("CryptoPal", "Account",
                "administration@cryptopal.com", "123",
                currencyInformationService.getCurrencyFromMap("USD"), true);

        cryptoPalAccount.getWalletByCurrency(currencyInformationService.getCurrencyFromMap("USD")).setCredit(new BigDecimal(10000));
        cryptoPalAccount.getWallets().add(new Wallet("EUR", cryptoPalAccount, new BigDecimal(100000),
                currencyInformationService.getCurrencyFromMap("EUR")));
        //cryptoPalAccount.getTransactions().add(new Transfer(cryptoPalAccount.getWallets().get(0), new Wallet("Test", cryptoPalAccount, new BigDecimal(2), currencyInformationService.getCurrencyFromMap("USD")), new BigDecimal(2), currencyInformationService.getCurrencyFromMap("USD"), new Date(System.currentTimeMillis()), "Message"));

        //em.persist(new Transfer(cryptoPalAccount.getWallets().get(0), new Wallet("Test", cryptoPalAccount, new BigDecimal(2), currencyInformationService.getCurrencyFromMap("USD")), new BigDecimal(2), currencyInformationService.getCurrencyFromMap("USD"), new Date(System.currentTimeMillis()), "Message"));


        createNewAccount(cryptoPalAccount);

        Account account1 = new Account("Max", "Mustermann", "max.mustermann@gmx.de", "123",
                currencyInformationService.getCurrencyFromMap("EUR"), false);

//        Account account3 = new Account("Maria", "Meister", "maria.meister@freenet.de", "test", "USD", true);

        createNewAccount(account1);

        Account account2 = new Account("Manfred", "Mueller", "manfred.mueller@gmail.de", "321",
                currencyInformationService.getCurrencyFromMap("USD"), false);

        createNewAccount(account2);

//        Account testAccount1 = getAccountByEmail("max.mustermann@gmx.de");
//        Account testAccount2 = getAccountByEmail("manfred.mueller@gmail.de");
//
//
//        System.out.println(testAccount1.toString());
//        System.out.println(testAccount2.toString());

        System.out.println("Dummies created");
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

    // TODO
    private void checkIfAccountNameIsBanned() {

    }
}
