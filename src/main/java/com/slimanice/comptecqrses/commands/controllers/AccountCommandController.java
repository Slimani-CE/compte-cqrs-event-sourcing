package com.slimanice.comptecqrses.commands.controllers;

import com.slimanice.comptecqrses.commonapi.commands.CreateAccountCommand;
import com.slimanice.comptecqrses.commonapi.dtos.CreateAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/commands/account")
public class AccountCommandController {
    private final CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

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
}
