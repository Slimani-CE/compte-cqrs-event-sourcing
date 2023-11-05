package com.slimanice.comptecqrses.commonapi.events;

import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private double initialBalance;
    private String currency;

    public AccountCreatedEvent(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
