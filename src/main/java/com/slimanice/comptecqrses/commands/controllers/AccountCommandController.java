package com.slimanice.comptecqrses.commands.controllers;

import com.slimanice.comptecqrses.commonapi.commands.CreateAccountCommand;
import com.slimanice.comptecqrses.commonapi.dtos.CreateAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
            UUID.randomUUID().toString(),
            request.getInitialBalance(),
            request.getCurrency()
        ));

        return commandResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        ResponseEntity<String> entity = new ResponseEntity<>(
            exception.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;
    }

    // Fetching event from event store by account id
    @GetMapping(path = "/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }
}
