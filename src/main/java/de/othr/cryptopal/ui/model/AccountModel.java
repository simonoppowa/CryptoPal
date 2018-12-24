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

    public String doRegister() {
        System.out.println("Register called with " + credentials.getEmail() + " " + credentials.getPassword() + " "
                + credentials.getConfirmPassword());

        // Check if email field is filled out
        if(credentials.getEmail() == null || credentials.getEmail().equals("")) {
            addWarningMessage("fill_out_email_field");
            return null;
        }
        // Check if password field is filled out
        if(credentials.getPassword() == null || credentials.getPassword().equals("")) {
            addWarningMessage("fill_out_password_field");
            return null;
        }

        // Check if email is already used
        if(accountService.checkIfAccountAlreadyExists(credentials.getEmail())) {
            addWarningMessage("login_already_exists");
            return null;
        }

        // Check if passwords match
        if(!credentials.getPassword().equals(credentials.getConfirmPassword())) {
            addWarningMessage("passwords_don't_match");
            return null;
        }

        Account newAccount = new Account(credentials.getEmail(), credentials.getPassword());

        loggedInAccount = accountService.createNewAccount(newAccount, credentials.getConfirmPassword());

        if(loggedInAccount != null) {
            System.out.println("New account created: " + loggedInAccount.getEmail() + " " + loggedInAccount.getPassword());
        } else {
            System.out.println("Error while creating account");
            addWarningMessage("critical_account_creation_error");
        }

        return "createaccount.faces";
    }

    private void addWarningMessage(String message, Object... args) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("registerform", new FacesMessage(FacesMessage.SEVERITY_WARN,
                getMessage(context, message, args), null));
    }

    private String getMessage(FacesContext facesContext, String msgKey, Object... args) {
        // TODO load message from properties
        return "Error 404: " + msgKey;
    }
}
