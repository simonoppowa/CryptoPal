package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Wallet;
import de.othr.cryptopal.service.AccountService;
import de.othr.cryptopal.service.CurrencyInformationService;
import de.othr.cryptopal.service.TransferService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.logging.Level;

@SessionScoped
@Named
public class TransferModel extends AbstractModel {

    @Inject
    private AccountModel accountModel;

    @Inject
    private AccountService accountService;

    @Inject
    private TransferService transferService;

    @Inject
    private CurrencyInformationService currencyInformationService;

    private String receiverEmail;
    private String currencyString;
    private BigDecimal amount;
    private String transferMessage;

    public TransferModel() {
    }

    public void transferMoney() {
        String amountString = "0.00";
        if(amount != null) {
            amountString = amount.toPlainString();
        }
        logger.log(Level.INFO, "transferMoney called with: " + receiverEmail + " " + currencyString
                + " " + transferMessage + " " + amountString);

        boolean correctInput = true;

        // Check if receiver email field is filled out
        if(receiverEmail == null || receiverEmail.equals("")) {
            addWarningMessage("fill_out_email_field", "sendform:email");
            correctInput = false;
        }
        Account receiver = accountService.getAccountByEmail(receiverEmail);

        if(receiver == null) {
            addWarningMessage("no_receiver_account_found", "sendform:email");
            correctInput = false;
        }

        // Check wallet
        Currency senderCurrency = currencyInformationService.getCurrencyFromMap(currencyString);

        if(senderCurrency == null) {
            addWarningMessage("currency_not_found", "sendform:wallet");
            correctInput = false;
        }

        Wallet senderWallet = accountModel.getLoggedInAccount().getWalletByCurrency(senderCurrency);

        // Check if amount input is valid
        if(amount == null) {
            addWarningMessage("fill_out_amount_field", "sendform:amount");
            correctInput = false;

        } else if(senderWallet.getCredit().subtract(amount).longValue() < 0){
            addWarningMessage("not_enough_credit", "sendform:amount");
            correctInput = false;
        }

        // Check if message input is valid
        if(transferMessage == null || transferMessage.equals("")) {
            addWarningMessage("fill_out_transfer_message", "sendform:message");
            correctInput = false;
        }

        if(correctInput) {
            transferService.sendMoney(senderWallet, receiverEmail, amount, transferMessage);
        }

    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getCurrencyString() {
        return currencyString;
    }

    public void setCurrencyString(String currencyString) {
        this.currencyString = currencyString;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransferMessage() {
        return transferMessage;
    }

    public void setTransferMessage(String transferMessage) {
        this.transferMessage = transferMessage;
    }
}
