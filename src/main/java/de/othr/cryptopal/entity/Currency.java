package de.othr.cryptopal.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

    @Id
    private String currencyId;

    @JsonProperty("EUR")
    private double exchangeRate;

    public Currency() {
    }

    public Currency(String currencyId) {
        this.currencyId = currencyId;
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

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return currencyId + currencyId;
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
