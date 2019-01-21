package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.AccountType;
import de.othr.cryptopal.service.AccountService;
import de.othr.cryptopal.service.TransferService;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.logging.Level;

@SessionScoped
@Named
public class AccountModel extends AbstractModel {

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

    private boolean isBusinessAccount;

    @Transactional // TODO Remove
    public String doRegister() {
//        logger.log(Level.INFO, "doRegister called with " + credentials.getEmail() + " " + credentials.getPassword() + " "
//                + credentials.getConfirmPassword());

        // TODO Check
        accountService.init();

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

    @Transactional //TODO Remove
    public String doLogin() {
//        logger.log(Level.INFO, "doLogin called with " + credentials.getEmail() + " " + credentials.getPassword());

        accountService.init();

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
            loggedInAccount = accountService.getAccountByCredentials(credentials.getEmail(), credentials.getPassword());

            if(loggedInAccount == null) {
                addWarningMessage("no_account_found", "loginform:email");
            } else {
                logger.log(Level.INFO, "Logged in with: " + loggedInAccount.getEmail() + " " + loggedInAccount.getPassword());
                return "home.faces";
            }
        }
        return null;
    }

    public String doCreateAccount() {
        logger.log(Level.INFO, "doCreateAccount called with " + loggedInAccount.getFirstname() + " "
                + loggedInAccount.getLastname() + " " + currencyDropdownModel.getSelectedCurrency() + " "
                + isBusinessAccount);

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

        // Account Type
        if(isBusinessAccount) {
            loggedInAccount.setAccountType(AccountType.BUSINESS);
        } else {
            loggedInAccount.setAccountType(AccountType.PRIVATE);
        }

        if(correctInput) {

            boolean accountCreated = accountService.createNewAccount(loggedInAccount);

            if(accountCreated) {
                logger.log(Level.INFO, "New account created: " + loggedInAccount.toString());

                // Set start money
                transferService.setStartMoney(loggedInAccount);

                return "registersucess.faces";
            } else {
                logger.log(Level.WARNING, "Error while creating account");
                addWarningMessage("critical_account_creation_error", null);
            }
        }
        return null;
    }

    public void doLogout() {
        logger.log(Level.INFO, "doLogout called");
        loggedInAccount = null;
    }

    public Account getLoggedInAccount() {
        if(loggedInAccount.getWallets() != null) {
            loggedInAccount = accountService.getAccountByEmail(loggedInAccount.getEmail());
        }
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

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }
}
