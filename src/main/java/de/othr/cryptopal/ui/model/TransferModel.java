package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.entity.Account;
import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.entity.Wallet;
import de.othr.cryptopal.service.AccountService;
import de.othr.cryptopal.service.CurrencyInformationService;
import de.othr.cryptopal.service.TransferService;
import de.othr.cryptopal.service.qualifier.TransferServiceQualifier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@SessionScoped
@Named
public class TransferModel extends AbstractModel {

    @Inject
    private AccountModel accountModel;

    @Inject
    private AccountService accountService;

    @Inject @TransferServiceQualifier
    private TransferService transferService;

    @Inject
    private CurrencyInformationService currencyInformationService;

    private Map<String, Currency> currencyMap;

    private String receiverEmail;
    private Currency currency;
    private BigDecimal amount;
    private String transferMessage;

    @PostConstruct
    private void getAvailableCurrencies() {
        currencyMap = new HashMap<>();
        // Populate map
        for(Wallet wallet : accountModel.getLoggedInAccount().getWallets()) {
            currencyMap.put(wallet.getCurrency().getCurrencyId(), wallet.getCurrency());
        }
    }

    public TransferModel() {
    }

    public void transferMoney() {
        String amountString = "0.00";
        if(amount != null) {
            amountString = amount.toPlainString();
        }
        logger.log(Level.INFO, "transferMoney called with: " + receiverEmail + " " + currency.getCurrencyId()
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

        if(currency == null) {
            addWarningMessage("currency_not_found", "sendform:currency");
            correctInput = false;
        }

        Wallet senderWallet = accountModel.getLoggedInAccount().getWalletByCurrency(currency);

        // Check if amount input is valid
        if(amount == null) {
            addWarningMessage("fill_out_amount_field", "sendform:amount");
            correctInput = false;

        } else if(senderWallet.getCredit().subtract(amount).longValue() < 0){
            addWarningMessage("not_enough_credit_send", "sendform:amount");
            correctInput = false;
        }

        // Check if message input is valid
        if(transferMessage == null || transferMessage.equals("")) {
            addWarningMessage("fill_out_transfer_message", "sendform:message");
            correctInput = false;
        }

        if(correctInput) {
            transferService.sendMoney(senderWallet, receiverEmail, amount, transferMessage);
            addInfoMessage("success_transfer", "sendform");
        }

    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public Map<String, Currency> getCurrencyMap() {
        List<Wallet> walletList = accountModel.getLoggedInAccount().getWallets();

        for(Wallet wallet : walletList) {
            if(!currencyMap.containsKey(wallet.getCurrency().getCurrencyId())) {
                currencyMap.put(wallet.getCurrency().getCurrencyId(), wallet.getCurrency());
            }
        }

        return currencyMap;
    }

    public void setCurrencyMap(Map<String, Currency> currencyMap) {
        this.currencyMap = currencyMap;
    }
}
