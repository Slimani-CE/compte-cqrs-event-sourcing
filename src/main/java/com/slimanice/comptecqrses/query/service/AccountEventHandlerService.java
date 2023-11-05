package com.slimanice.comptecqrses.query.service;

import com.slimanice.comptecqrses.commonapi.events.AccountCreatedEvent;
import com.slimanice.comptecqrses.commonapi.events.AccountCreditedEvent;
import com.slimanice.comptecqrses.query.entities.Account;
import com.slimanice.comptecqrses.query.repositories.AccountRepository;
import com.slimanice.comptecqrses.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AccountEventHandlerService {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage){
//        log.info("Handling a AccountCreatedEvent command with information: {}", event);
//        Account account = new Account();
//        account.setId(event.getId());
//        account.setBalance(event.getBalance());
//        account.setCurrency(event.getCurrency());
//        accountRepository.save(account);
    }
}
