## Project Structure
For this project i have used Spring Boot best practices: Maven, REST API, Microservices, communication over http with JSON.

## Installation
The project uses Maven for build, package, and test workflow.
The project requires the following dependencies be installed on the host machine:
* Java Development Kit 8 or later
* Apache Maven 3 or later

## Project Build & Run with Spring Tools 4
1- Spring Tools 4 --> import the project

2- Run As Maven Build, Maven Goals: clean install, skipTest

3- Run As Java Application

4- Run As JUnit test

* CMD

**cd project path

**mvn clean install -DskipTests

**mvn spring-boot:run

## Use Cases Test (Postman)

1-create transaction

###Method: POST

###http://localhost:8080/generate-plan

###JSON : {
 "loanAmount": "5000",
 "nominalRate": "5.0",
 "duration": 24,
 "startDate": "2018-01-01T00:00:01Z"
}
