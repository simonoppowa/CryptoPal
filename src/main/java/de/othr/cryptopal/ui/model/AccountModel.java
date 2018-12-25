package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.service.AccountService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

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

        boolean correctInput = true;

        // Check if email field is filled out
        if(credentials.getEmail() == null || credentials.getEmail().equals("")) {
            addWarningMessage("fill_out_email_field", "inputform:email");
            correctInput = false;
        }
        // Check if password field is filled out
        if(credentials.getPassword() == null || credentials.getPassword().equals("")) {
            addWarningMessage("fill_out_password_field", "inputform:password");
            correctInput = false;
        }

        // Check if confirmPassword field is filled out
        if(credentials.getConfirmPassword() == null || credentials.getConfirmPassword().equals("")) {
            addWarningMessage("fill_out_confirm_password_field", "inputform:confirmPassword");
            correctInput = false;
        }

        // Check if email is already used
        if(correctInput && accountService.checkIfAccountAlreadyExists(credentials.getEmail())) {
            addWarningMessage("login_already_exists", "inputform:email");
            correctInput = false;
        }

        // Check if passwords match
        if(correctInput && !credentials.getPassword().equals(credentials.getConfirmPassword())) {
            addWarningMessage("passwords_don't_match", "inputform:confirmPassword");
            correctInput = false;
        }

        if(!correctInput) {
            return null;
        }

        Account newAccount = new Account(credentials.getEmail(), credentials.getPassword());

        loggedInAccount = accountService.createNewAccount(newAccount, credentials.getConfirmPassword());

        if(loggedInAccount != null) {
            System.out.println("New account created: " + loggedInAccount.getEmail() + " " + loggedInAccount.getPassword());
        } else {
            System.out.println("Error while creating account");
            addWarningMessage("critical_account_creation_error", null);
        }

        return "createaccount.faces";
    }

    private void addWarningMessage(String message, String component) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "INFO", getMessage(context, message)));
    }

    private String getMessage(FacesContext facesContext, String msgKey) {
        Locale locale = facesContext.getViewRoot().getLocale();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale, classLoader);
        String msg = bundle.getString(msgKey);
        return msg;
    }
}
