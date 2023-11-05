package com.slimanice.comptecqrses.commonapi.events;

import com.slimanice.comptecqrses.commonapi.enums.AccountStatus;
import lombok.Getter;

import java.util.Date;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {
    private double initialBalance;
    private String currency;
    private AccountStatus status;

    public AccountCreatedEvent(String id, Date date, double initialBalance, String currency, AccountStatus status) {
        super(id, date);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
