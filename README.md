Writing the README.md file

# CQRS Event Sourcing Application with Spring Boot and Axon Framework 🍃

  
## 📝 Table of Contents

- [📖 Overview](#-overview)
- [📦 Dependencies](#-dependencies)
- [🛠️ Project Structure](#-project-structure)
- [🚀 Code Snippets](#-code-snippets)
  - [📁 AccountAggregate Class](#-accountaggregate-class)
  - [📁 AccountCommand Controller](#-accountcommand-controller)
- [✅ Screenshots](#-screenshots)

## 📖 Overview
This is a simple application that demonstrates the use of CQRS and Event Sourcing. The application is a simple REST API that allows you to create a bank account, deposit and withdraw money from it. The application is written in Java using the Spring Boot framework and the Axon Framework library. 

## 📦 Dependencies
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)  ![AXON](https://img.shields.io/badge/AXON-Framework-%23EE2E.svg?style=for-the-badge) ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

## 🛠️ Project Structure
```
C:.                                                          
├───main                                                     
│   ├───java                                                 
│   │   └───com                                              
│   │       └───slimanice                                    
│   │           └───comptecqrses                             
│   │               │   CompteCqrsEsApplication.java         
│   │               │                                        
│   │               ├───commands                             
│   │               │   ├───aggregates                       
│   │               │   │       AccountAggregate.java        
│   │               │   │                                    
│   │               │   └───controllers                      
│   │               │           AccountCommandController.java
│   │               │                                        
│   │               ├───commonapi                            
│   │               │   ├───commands                         
│   │               │   │       BaseCommand.java            
│   │               │   │       CreateAccountCommand.java   
│   │               │   │       CreditAccountCommand.java   
│   │               │   │       DebitAccountCommand.java    
│   │               │   │                                   
│   │               │   ├───dtos                            
│   │               │   │       CreateAccountRequestDTO.java
│   │               │   │                                   
│   │               │   ├───enums                           
│   │               │   │       AccountStatus.java
│   │               │   │
│   │               │   └───events
│   │               │           AccountActivatedEvent
│   │               │           AccountCreatedEvent.java
│   │               │           AccountCreditedEvent.java
│   │               │           AccountDebitedEvent.java
│   │               │           BaseEvent.java
│   │               │
│   │               └───query
```

## 🚀 Code Snippets

### 📁 AccountAggregate Class
- The aggregate is the main component of the application. It is the one that will be handling the commands and will be applying the events.
```java
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
    AggregateLifecycle.apply(new AccountActivatedEvent(
            event.getId(),
            AccountStatus.ACTIVATED
    ));
  }

  @EventSourcingHandler
  public void on(AccountActivatedEvent event) {
    this.status = event.getStatus();
  }
}
```

### 📁 AccountCommand Controller
- The controller will be handling the HTTP requests and will be sending the commands to the command gateway. 
```java
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
    
    // Fetching event from event store by account id
    @GetMapping(path = "/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
      return eventStore.readEvents(accountId).asStream();
    }
}

```

## ✅ Screenshots
1. Testing the creation of an account
![Testing the creation of an account](assets/screenshot1.png)


2. Testing the creation of an account with negative initial balance
![Testing the creation of an account with negative initial balance](assets/screenshot2.png)


3. Created events in MySQL database
![Created events in MySQL database](assets/screenshot3.png)


4. Fetching event from event store
![Fetching event by account id from event store](assets/screenshot4.png)


5. Testing the credit of an account
![Testing the credit of an account](assets/screenshot5.png)


6. Testing the debit of an account with insufficient balance
![Testing the debit of an account with insufficient balance](assets/screenshot6.png)

7. Testing the querying service (Creating an account , crediting it and debiting it)
![Testing the querying service](assets/screenshot7.png)