package de.othr.cryptopal.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries(
        @NamedQuery(name = Currency.FINDALL, query = "SELECT c FROM Currency c")
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

    public static final String FINDALL = "Currency.findAll";

    @Id
    private String currencyId;

    private String currencyName;

    private String symbol;

    @Transient
    private double exchangeRate;

    //TODO set currency type

    public Currency() {
    }

    public Currency(String currencyId) {
        this.currencyId = currencyId;
    }

    public Currency(String currencyId, String currencyName, String symbol) {
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.symbol = symbol;
    }

    public Currency(String currencyId, double exchangeRate) {
        this.currencyId = currencyId;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return currencyId + "\n   Exchange rate: " + exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return currencyId != null ? currencyId.equals(currency.currencyId) : currency.currencyId == null;
    }

    @Override
    public int hashCode() {
        return currencyId != null ? currencyId.hashCode() : 0;
    }
}
