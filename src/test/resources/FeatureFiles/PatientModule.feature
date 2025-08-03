Feature: Verify create dieticians sceanrios

  Background: 

  
  Scenario: Create Dietician with all the valid informations
    Given Admin user loggedin
    When post condition with valid data from sheet "CreateDietician" with test case no "TC01" is provided
    Then A new dietician should get created

  Scenario: Check dietician able to create patient with valid data and token
    Given Dietician is logged in
    When Dietician sends POST http request with valid data and token
    Then Dietician recieves 201 created and with response body.
    
  
  