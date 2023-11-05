package com.slimanice.comptecqrses.query.service;

import com.slimanice.comptecqrses.commonapi.events.AccountActivatedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountCreatedEvent;
import com.slimanice.comptecqrses.query.entities.Account;
import com.slimanice.comptecqrses.query.repositories.AccountRepository;
import com.slimanice.comptecqrses.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public void on(AccountActivatedEvent event){
        log.info("Handling a AccountActivatedEvent command with information: {}", event);
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
}

