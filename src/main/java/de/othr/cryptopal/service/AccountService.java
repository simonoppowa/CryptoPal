package de.othr.cryptopal.service;

import de.othr.cryptopal.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class AccountService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Account createNewAccount(@NotNull Account account, String confirmPassword) {

        // Check if password matches confirmPassword
        if(!account.getPassword().equals(confirmPassword)) {
            return null;
        }

        try {
            em.persist(account);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return account;
    }
}
