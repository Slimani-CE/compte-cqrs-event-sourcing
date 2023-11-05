package com.slimanice.comptecqrses.commands.aggregates;

import com.slimanice.comptecqrses.commonapi.commands.CreateAccountCommand;
import com.slimanice.comptecqrses.commonapi.enums.AccountStatus;
import com.slimanice.comptecqrses.commonapi.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        // Required by AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        // Required by AXON
        if(createAccountCommand.getInitialBalance() < 0) throw new RuntimeException("You can't create account with negative initial balance");
        AggregateLifecycle.apply(new AccountCreatedEvent(
            createAccountCommand.getId(),
            createAccountCommand.getInitialBalance(),
            createAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
    }
}
