package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApplicationScoped
public class AccountService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    //TODO Remove
    @Transactional
    public void createDummies() {
        Account account1 = new Account("Max", "Mustermann", "max.mustermann@gmx.de", "123", "EUR", false);
        Account account2 = new Account("Manfred", "Mueller", "manfred.mueller@gmail.de", "321", "EUR", false);
        Account account3 = new Account("Maria", "Meister", "maria.meister@freenet.de", "test", "USD", true);

        try {
            em.persist(account1);
            em.persist(account2);
            em.persist(account3);
        } catch(PersistenceException ex) {
            ex.printStackTrace();
        }
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

        TypedQuery<Account> typedQuery = em.createNamedQuery(Account.FINDBYEMAIL, Account.class);
        typedQuery.setParameter("email", email);

        try {
            typedQuery.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    // TODO
    private void checkIfAccountNameIsBanned() {

    }
}
