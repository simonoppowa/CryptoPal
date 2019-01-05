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
import java.io.Serializable;
import java.math.BigDecimal;

@SessionScoped
@Named
public class TransferModel implements Serializable {

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
        System.out.println("transferMoney called with: " + receiverEmail + " " + currencyString
                + " " + amount.toPlainString() + " " + transferMessage);

        Currency senderCurrency = currencyInformationService.getCurrencyFromMap(currencyString);

        if(senderCurrency == null) {
            System.out.println("Currency not found: " + currencyString);
            return;
        }

        Wallet senderWallet = accountModel.getLoggedInAccount().getWalletByCurrency(senderCurrency);

        Account receiver = accountService.getAccountByEmail(receiverEmail);

        if(receiver == null) {
            System.out.println("Account not found with email: " + receiverEmail);
            return;
        }


        transferService.sendMoney(senderWallet, receiverEmail, amount, transferMessage);

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
