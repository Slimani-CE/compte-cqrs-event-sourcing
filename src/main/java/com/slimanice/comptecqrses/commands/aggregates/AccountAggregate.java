package com.slimanice.comptecqrses.commands.aggregates;

import com.slimanice.comptecqrses.commonapi.commands.CreateAccountCommand;
import com.slimanice.comptecqrses.commonapi.commands.CreditAccountCommand;
import com.slimanice.comptecqrses.commonapi.commands.DebitAccountCommand;
import com.slimanice.comptecqrses.commonapi.enums.AccountStatus;
import com.slimanice.comptecqrses.commonapi.events.AccountActivatedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountCreatedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountCreditedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountDebitedEvent;
import com.slimanice.comptecqrses.commonapi.exceptions.BalanceNotSufficientException;
import com.slimanice.comptecqrses.commonapi.exceptions.NegativeAmountException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

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
            new Date(),
            createAccountCommand.getInitialBalance(),
            createAccountCommand.getCurrency(),
            AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getStatus();
        AggregateLifecycle.apply(new AccountActivatedEvent(
            event.getId(),
            new Date(),
            AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if(command.getAmount() < 0) throw new NegativeAmountException("You can't credit account with negative amount");

        AggregateLifecycle.apply(new AccountCreditedEvent(
            command.getId(),
            new Date(),
            command.getAmount(),
            command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if(command.getAmount() < 0) throw new NegativeAmountException("You can't debit account with negative amount");
        if(command.getAmount() > this.balance) throw new BalanceNotSufficientException("You can't debit account with amount greater than balance: " + this.balance);

        AggregateLifecycle.apply(new AccountDebitedEvent(
            command.getId(),
            new Date(),
            command.getAmount(),
            command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }
}
