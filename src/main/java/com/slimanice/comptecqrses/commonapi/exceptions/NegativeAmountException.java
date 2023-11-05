package com.slimanice.comptecqrses.commonapi.exceptions;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(String s) {
        super(s);
    }
}
