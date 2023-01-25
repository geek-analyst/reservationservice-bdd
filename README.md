# RestAssuredSerenityCucumber Framework

This Rest API test solution for sample endpoints available in http://restful-booker.herokuapp.com/apidoc/index.html
 The published APIs represent a booking system where user can create, retrieve ,update and delete bookings.

Tests are written using a combination of SerenityBDD, RestAssured, Cucumber, Junit & Maven.

## Technology Stack

- Java
- Serenity BDD
- Maven
- RestAssured

## Prerequisites

* [Java 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java Dev Kit
* [Maven](https://maven.apache.org/download.cgi) - Dependency Manager

## Application Under Test

We are using Restful-Booker APIs as the Application Under Test.

* URL : https://restful-booker.herokuapp.com/

## The project directory structure
The project follows the standard directory structure used in most Serenity projects:

```Gherkin
src
  + main
    + java                          
      + com.payq.reservation                         
        + dto                                        pojos of all endpoints
             + Booking
             + Booking Dates
             + BookingDetails                     
        + utilities                                  utility methods and constants
             + Constants
             +  ReservationProperties
  + test
    + java                          
      + com.payq.reservation                      endpoints of the services
           + BookingService
           + BookingServiceImpl                  
        + runner 
             + CucumberTestSuite                  test runner(senerity runner/trigger configurations
        + stepdefs                              Step definition files
             + StepDefinitions
             + TestContext                 
  + resources
     +com.payq.reservation.utilities 
         application-test.properties             AUT properties files
      + features                                 Feature files
         Booking.feature               
```
Following instructions will help you running the project. First, clone this project locally on your machine from the master branch.

### Installation and Test Execution

Open the project in any IDE Eclipse/IntelliJ. Run the following command in Terminal and build the project. It will automatically download all the required dependencies.

```sh
$ mvn clean install
```

### Execute Tests

Run the following command in Terminal to execute tests.

```sh
$ mvn clean verify
```

### Test Report

You can find the Serenity reports in the following directory of the Project.

```sh
\target\site\serenity\
```

In the serenity directory, open 'index.html' file to view the report.

If we make any push/pull/any change to code, will automatically trigger the builds in gitlab
