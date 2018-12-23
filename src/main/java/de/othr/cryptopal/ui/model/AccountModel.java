package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.service.AccountService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
@ManagedBean
public class AccountModel implements Serializable {

    @Inject
    private AccountService accountService;


    @Inject
    private CredentialsModel credentials;

    private Account loggedInAccount;

    public void doRegister() {
        System.out.println("Register called with " + credentials.getEmail() + " " + credentials.getPassword() + " "
                + credentials.getConfirmPassword());
        // TODO Add warning for null or empty
        // TODO hardcoded text

        //
        //

        if(!credentials.getPassword().equals(credentials.getConfirmPassword())) {
            System.out.println("Passwords don't match");
            FacesContext.getCurrentInstance().addMessage("myform:email",
                    new FacesMessage("Passwords don't match", "Passwords don't match"));
            return;
        }

        Account newAccount = new Account(credentials.getEmail(), credentials.getPassword());

        loggedInAccount = accountService.createNewAccount(newAccount, credentials.getConfirmPassword());

        if(loggedInAccount != null) {
            System.out.println("New account created: " + loggedInAccount.getEmail() + " " + loggedInAccount.getPassword());
        } else {
            System.out.println("Error while creating account");
        }

    }
}
