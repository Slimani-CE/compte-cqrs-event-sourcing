package com.slimanice.comptecqrses.query.service;

import com.slimanice.comptecqrses.commonapi.enums.OperationType;
import com.slimanice.comptecqrses.commonapi.events.AccountActivatedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountCreatedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountCreditedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountDebitedEvent;
import com.slimanice.comptecqrses.commonapi.queries.GetAccountByIdQuery;
import com.slimanice.comptecqrses.commonapi.queries.GetAllAccountsQuery;
import com.slimanice.comptecqrses.query.entities.Account;
import com.slimanice.comptecqrses.query.entities.Operation;
import com.slimanice.comptecqrses.query.repositories.AccountRepository;
import com.slimanice.comptecqrses.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountEventHandlerService {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("Handling a AccountCreatedEvent command with information: {}", event);
        Account account = new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        account.setBalance(event.getInitialBalance());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("Handling a AccountCreditedEvent command with information: {}", event);
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(event.getAmount());
        operation.setDate(event.getDate());
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("Handling a AccountActivatedEvent command with information: {}", event);
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("Handling a AccountDebitedEvent command with information: {}", event);
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(event.getAmount());
        operation.setDate(event.getDate());
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        log.info("Handling a GetAllAccountsQuery command with information: {}", query);
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        log.info("Handling a GetAccountByIdQuery command with information: {}", query);
        return accountRepository.findById(query.getId()).get();
    }
}
