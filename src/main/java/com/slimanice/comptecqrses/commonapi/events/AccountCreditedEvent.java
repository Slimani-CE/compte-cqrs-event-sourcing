package com.slimanice.comptecqrses.commonapi.events;

import lombok.Getter;

@Getter
public class AccountCreditedEvent extends BaseEvent<String> {
    private double amount;
    private String currency;

    public AccountCreditedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
