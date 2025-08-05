Feature: Dietician User Login feature

Scenario: Verify login valid functionality
Given Admin creates Login Valid request
When Admin sends HTTP Post login request 
Then Admin receives 200 staus code with auto generated token

 Scenario Outline: Verify DieticianLogin invalid functionality
    Given Admin creates invalid Login "<Scenario>" Request
    When Admin sends invalid "<Scenario>" HTTPS Login Request
    Then Admin receives status "<Code>" for Login request for failed test case
    Examples: 
      | Scenario     		       |   Code     |
      |  LoginInvalidCreds     |   401      |
      |  LoginInvalidEndpoint  |   401      |
			|  LoginEmptyCredential  |   401      |
			|  LoginEmptyUserEmail   |   401      |
			|  LoginEmptyPassword    |   401      |
			|LoginInvalidEmailFormat |   400      |
			|LoginIncorrectPassword  |   401      |
      