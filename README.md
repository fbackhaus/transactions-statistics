# Transactions Statistics
## Summary
API that allows you to add transactions, and generate statistics based on them

## Build and Run Tests
```shell
mvn clean install
```

## Run Integration Tests only
```shell
mvn clean integration-test
```

## Run App Locally
```shell
mvn spring-boot:run
```
_Or, use your favourite IDE_
(The app runs on port 8080 by default)
## Swagger UI
Contains all endpoints and data about request/responses.
First, [run the app locally](#run-app-locally)

Then, access http://localhost:8080/swagger-ui.html

## Maintaining only necessary transactions
This app does not only consist of a REST API that allows you to create transactions and generate statistics.

There is also a [cron job](./src/main/java/com/n26/cron/TransactionCleanerCronJob.java), that runs every 60 seconds and removes the old transactions from the repository, not considered for statistics.