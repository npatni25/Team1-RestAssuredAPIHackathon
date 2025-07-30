#Author: npatni25@gmail.com
@tag
Feature: Verify create dieticians sceanrios

  @createDietician
  Scenario: Create Dietician with all the valid informations
    Given Admin user loggedin
    When post condition with valid data from sheet "CreateDietician" with test case no "TC01" is provided
    Then A new dietician should get created
