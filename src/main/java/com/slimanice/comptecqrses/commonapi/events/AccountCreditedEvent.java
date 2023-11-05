package com.slimanice.comptecqrses.commonapi.events;

import lombok.Getter;

import java.util.Date;

@Getter
public class AccountCreditedEvent extends BaseEvent<String> {
    private double amount;
    private String currency;

    public AccountCreditedEvent(String id, Date date, double amount, String currency) {
        super(id, date);
        this.amount = amount;
        this.currency = currency;
    }
}
