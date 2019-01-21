package de.othr.cryptopal.exception;

public class RefundException extends Exception {

    public static final int errorCode = 202;

    public RefundException(String message) {
        super(message);
    }
}
