# Spring Boot "Microservice" Example Project Intra bank Api

This is a sample api for getting account balance, get last 20 transactions & to transfer money

## How to Run

This application is packaged as a jar which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.
This application uses H2 database as in memory database, so on restarts your changes will get wiped out.

* Clone this repository
* Make sure you are using JDK above 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:

```
        java -jar  application/target/application-1.0.0.jar
or
        mvn spring-boot:run
``` 

Once the application runs you should see something like this

```
2023-04-21T02:39:11.943+02:00  INFO 33860 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path '/accounts'
2023-04-21T02:39:11.952+02:00  INFO 33860 --- [           main] c.m.IntraMoneyTransferApiApplication     : Started IntraMoneyTransferApiApplication in 6.89 seconds (process running for 7.582)
```

### Get information about account, transactions and do transfers

```
http://localhost:8080/accounts/1/balance
http://localhost:8080/accounts/1/statements/mini
http://localhost:8080/accounts/transfer
```

### get account details

```
GET http://localhost:8080/accounts/1/balance
Accept: application/json
Content-Type: application/json

{
    "account-Id": 1,
    "balance": 1000,
    "currency": "EUR"
}

```

### get last 20 transaction details

```
GET http://localhost:8080/accounts/1/statements/mini
Accept: application/json
Content-Type: application/json

[
    {
        "account-Id": 4,
        "amount": 2500,
        "currency": "EUR",
        "type": "Debit",
        "transaction-date": "2022-04-02"
    },
    {
        "account-Id": 3,
        "amount": 1500,
        "currency": "EUR",
        "type": "Debit",
        "transaction-date": "2022-03-29"
    },
    {
        "account-Id": 2,
        "amount": 500,
        "currency": "EUR",
        "type": "Debit",
        "transaction-date": "2022-03-25"
    }
]

```

### transfer money

```
POST http://localhost:8080/accounts/transfer
Accept: application/json
Content-Type: application/json
Request-body: 
{
    "fromAccount" : "1",
    "toAccount": "2",
    "amount": 10000

}

Response:
{
    "account-Id": 1,
    "balance": 1000,
    "currency": "EUR"
}

```

## Test Report

### Jacoco plugin is used to generate test report and you can see the test report under "target/site" folder.



