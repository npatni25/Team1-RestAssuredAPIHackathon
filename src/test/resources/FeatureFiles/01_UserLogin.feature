Feature: Dietician User Login feature.

Scenario: Verify Whether login valid functionality is working fine or not
Given Admin creates Login Valid requests.
When Admin sends HTTP Post login request. 
Then Admin receives 200 staus code with auto generated token.
  
  Scenario Outline: Verify DieticianLogin invalid functionality
    Given Admin creates invalid Login "<Scenario>" Request
    When Admin sends invalid "<Scenario>" HTTPS Login Request
    Then Admin receives status "<Code>" for Login request for failed test case
    Examples: 
      | Scenario     		       |   Code     |
      |  LoginInvalidCreds     |   400      |