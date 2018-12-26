package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApplicationScoped
@ManagedBean
public class AccountService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    //TODO Remove
//    @PostConstruct
//    @Transactional
//    public void createDummies() {
//        Account account1 = new Account("max.mustermann@gmx.de", "123");
//        Account account2 = new Account("manfred.mueller@gmail.com", "321");
//        Account account3 = new Account("maria.meister@freenet.de", "test");
//
//        try {
//            em.persist(account1);
//            em.persist(account2);
//            em.persist(account3);
//        } catch(PersistenceException ex) {
//            ex.printStackTrace();
//        }
//        System.out.println("Dummies created");
//    }

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
