package com.slimanice.comptecqrses.commonapi.events;

import lombok.Getter;

import java.util.Date;

@Getter
public class AccountDebitedEvent extends BaseEvent<String> {
    private double amount;
    private String currency;

    public AccountDebitedEvent(String id, Date date, double amount, String currency) {
        super(id, date);
        this.amount = amount;
        this.currency = currency;
    }
}
