@Logout @Dietician

Feature: Dietician API Logout

Background: Authorization SetUp after login
	Given Admin sets authorization to Bearer Token after logged in

  @InvalidValid   
  Scenario Outline: Verify dietician Logout functionality
    Given Admin creates Logout "<Scenario>" Request
    When Admin sends GET HTTPS "<Scenario>" Logout Request
    Then Admin receives status "<Code>" for Logout request
    Examples: 
      | Scenario          |   Code     |
      |  LogoutValid      |   200      |
      |  LogoutInvalidEP  |   404      |
      
