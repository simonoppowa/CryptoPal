package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApplicationScoped
public class AccountService implements Serializable {

    @Inject
    private CurrencyInformationService currencyInformationService;

    @PersistenceContext
    private EntityManager em;

    //TODO Remove
    @Transactional
    public void createDummies() {
        Account account1 = new Account("Max", "Mustermann", "max.mustermann@gmx.de", "123",
                currencyInformationService.getCurrencyFromMap("EUR"), false);

//        Account account3 = new Account("Maria", "Meister", "maria.meister@freenet.de", "test", "USD", true);

        createNewAccount(account1);

        Account account2 = new Account("Manfred", "Mueller", "manfred.mueller@gmail.de", "321",
                currencyInformationService.getCurrencyFromMap("USD"), false);

        createNewAccount(account2);

        Account testAccount1 = getAccountByEmail("max.mustermann@gmx.de");
        Account testAccount2 = getAccountByEmail("manfred.mueller@gmail.de");


        System.out.println(testAccount1.toString());
        System.out.println(testAccount2.toString());

        System.out.println("Dummies created");
    }

    @Transactional
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

    public boolean checkIfAccountAlreadyExists(String email){
        Account account = getAccountByEmail(email);

        return account != null;
    }

    // TODO
    private void checkIfAccountNameIsBanned() {

    }
}
