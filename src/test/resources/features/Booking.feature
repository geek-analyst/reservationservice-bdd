Feature: Booking API

@regression
Scenario: Create a new booking
    Given Booking API is active
    When I POST a create booking
    Then I see response has 200 status code
    And I verify booking request response as per booking model
   
@regression
Scenario: Create a booking for multiple customers
    Given Booking API is active
    When I POST a create booking API for multiple users
      | firstname | lastname | totalprice | depositpaid | additionalneeds |
      | Jope      | Craig    |        309 | false       | Lunch           |
      | Kasse     | Thomass  |        523 | true        | Dinner          |
    Then the response has following response code
      | code |
      |  200 |
      |  200 |
     
 
@regression
Scenario: Get all booking IDs
    Given Booking API is active
    When I retrieve all the booking Ids
    Then I verify booking details response has booking ids
       
  
@regression
Scenario: Successfully retrieve booking by ID
    Given Booking API is active
    When I retrieve valid booking with ID
    Then I verify booking request response as per booking model
  
  
@regression
Scenario: Successfully retrieve booking by params
    Given Booking API is active
    When I retrieve valid booking with ID and Param
    Then I verify bookings


@regression
Scenario: Delete a booking
    Given Booking API is active
    When I delete a booking by id
    Then I see response has 201 status code
    When getting the same booking with Id
    Then I see response has 404 status code

@regression
Scenario Outline: Update a booking in the API
    Given Booking API is active
    When I update the booking with
    |firstname|lastname|totalprice|depositpaid|additionalneeds|
    |<firstname>|<lastname>|<totalprice>|<depositpaid>|<additionalneeds>|
    Then I see response has <code> status code
    And I verify booking update response
    Examples: 
      | firstname | lastname | totalprice | depositpaid | additionalneeds | code |
      | upfirst      | uplast    |        100 | true        | Lunch           | 200  |