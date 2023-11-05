package com.slimanice.comptecqrses.query.controllers;

import com.slimanice.comptecqrses.commonapi.queries.GetAccountByIdQuery;
import com.slimanice.comptecqrses.commonapi.queries.GetAllAccountsQuery;
import com.slimanice.comptecqrses.query.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> accounts() {
        log.info("Handling a AccountCreatedEvent command with information:");
        return queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
    }

    @GetMapping("byId/{id}")
    public Account accountById(@PathVariable String id) {
        log.info("Handling a AccountCreatedEvent command with information:");
        return queryGateway.query(new GetAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class)).join();
    }
}
