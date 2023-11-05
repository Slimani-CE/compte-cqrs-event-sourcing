package com.slimanice.comptecqrses.commonapi.events;

import com.slimanice.comptecqrses.commonapi.enums.AccountStatus;
import lombok.Getter;

import java.util.Date;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {
    private AccountStatus status;
    public AccountActivatedEvent(String id, Date date, AccountStatus status) {
        super(id, date);
        this.status = status;
    }
}
