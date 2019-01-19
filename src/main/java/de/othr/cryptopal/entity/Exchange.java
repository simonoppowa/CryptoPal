package de.othr.cryptopal.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Exchange extends Transaction{

    @Override
    public String getTransactionType() {
        return null;
    }

    @Override
    public String getDetails() {
        return null;
    }
}
