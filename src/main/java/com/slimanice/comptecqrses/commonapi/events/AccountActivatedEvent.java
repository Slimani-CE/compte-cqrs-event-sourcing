package com.slimanice.comptecqrses.commonapi.events;

import com.slimanice.comptecqrses.commonapi.enums.AccountStatus;
import lombok.Getter;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {
    private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
