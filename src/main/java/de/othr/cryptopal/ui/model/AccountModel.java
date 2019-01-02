package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.service.AccountService;
import de.othr.cryptopal.service.TransferService;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

@SessionScoped
@Named
public class AccountModel implements Serializable {

    @Inject
    private AccountService accountService;


    @Inject
    private CredentialsModel credentials;

    @Inject
    private CurrencyDropdownModel currencyDropdownModel;

    @Inject
    private TransferService transferService;

    @Produces
    private Account loggedInAccount;

    public String doRegister() {
        System.out.println("doRegister called with " + credentials.getEmail() + " " + credentials.getPassword() + " "
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

        if(correctInput) {
            loggedInAccount = new Account();
            loggedInAccount.setEmail(credentials.getEmail());
            loggedInAccount.setPassword(credentials.getPassword());
            return "createaccount.faces";
        } else {
            return null;
        }
    }

    public String doLogin() {
        System.out.println("doLogin called with " + credentials.getEmail() + " " + credentials.getPassword());

        boolean correctInput = true;

        if(credentials.getEmail() == null || credentials.getEmail().equals("")) {
            addWarningMessage("fill_out_email_field", "loginform:email");
            correctInput = false;
        }
        if(credentials.getPassword() == null || credentials.getPassword().equals("")) {
            addWarningMessage("fill_out_password_field", "loginform:password");
            correctInput = false;
        }

        if(correctInput) {
            loggedInAccount = accountService.getAccountByCredintials(credentials.getEmail(), credentials.getPassword());

            if(loggedInAccount == null) {
                addWarningMessage("no_account_found", "loginform:email");
            } else {
                System.out.println("Logged in with: " + loggedInAccount.getEmail() + " " + loggedInAccount.getPassword());
                return "home.faces";
            }
        }
        return null;
    }

    public String doCreateAccount() {
        System.out.println("doCreateAccount called with " + loggedInAccount.getFirstname() + " "
                + loggedInAccount.getLastname() + " " + currencyDropdownModel.getSelectedCurrency() + " "
                + loggedInAccount.isBusinessAccount());

        boolean correctInput = true;

        // Check if firstname is filled out
        if(loggedInAccount.getFirstname() == null || loggedInAccount.getFirstname().equals("")) {
            addWarningMessage("fill_out_firstname_field", "registerdetailform:firstname");
            correctInput = false;
        }
        // Check if lastname is filled out
        if(loggedInAccount.getLastname() == null || loggedInAccount.getLastname().equals("")) {
            addWarningMessage("fill_out_lastname_field", "registerdetailform:lastname");
            correctInput = false;
        }
        // Check if defaultCurrency is filled out
        if(currencyDropdownModel.getSelectedCurrency() == null || currencyDropdownModel.getSelectedCurrency().equals("")) {
            addWarningMessage("fill_out_defaultcurrency_field", "registerdetailform:defaultCurrency");
            correctInput = false;
        } else {
            loggedInAccount.setDefaultCurrencyId(currencyDropdownModel.getSelectedCurrencyObject());
            loggedInAccount.setDetails();
        }

        if(correctInput) {

            boolean accountCreated = accountService.createNewAccount(loggedInAccount);

            if(accountCreated) {
                System.out.println("New account created: " + loggedInAccount.toString());

                // Set start money
                //transferService.setStartMoney(loggedInAccount);


                return "registersucess.faces";
            } else {
                System.out.println("Error while creating account");
                addWarningMessage("critical_account_creation_error", null);
            }
        }
        return null;
    }

    public void doLogout() {
        loggedInAccount = null;
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
        if(bundle.containsKey(msgKey)) {
            return bundle.getString(msgKey);
        } else {
            System.out.println("No message found in messages with key: " + msgKey);
            return "Error";
        }
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public CurrencyDropdownModel getCurrencyDropdownModel() {
        return currencyDropdownModel;
    }

    public void setCurrencyDropdownModel(CurrencyDropdownModel currencyDropdownModel) {
        this.currencyDropdownModel = currencyDropdownModel;
    }
}
