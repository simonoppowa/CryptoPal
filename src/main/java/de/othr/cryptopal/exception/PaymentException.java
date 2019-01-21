package de.othr.cryptopal.exception;

import javax.xml.ws.WebFault;

@WebFault
public class PaymentException extends Exception {

    public static final int errorCode = 201;

    public PaymentException(String message) {
        super(message);
    }
}
