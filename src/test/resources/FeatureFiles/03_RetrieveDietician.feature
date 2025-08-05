#Author: npatni25@gmail.com
	@tag
	Feature: Verify retrieve dieticians sceanrios
	
	  Background: 
	    Given Admin user loggedin

  @retrieveDietician
  Scenario: Verify create dietician with valid data manually
    When Retrieving all dietician

  @retrieveDietician
  Scenario Outline: Verify retrieve all dietician
    Given User is setting auth as "<Scenario>"
    When Retrieving all dietician reading from JSON data file
    Then User should see <Expected>

    Examples: 
      | Scenario                           | Expected           |
      | RetrieveDietician_ValidAuth        | Admin recieves 200 |
      | RetrieveDietician_NoAuth           | Admin recieves 401 |
      | RetrieveDietician_InvaliMethod_PUT | Admin recieves 405 |
      | RetrieveDietician_InvaliEndPoint   | Admin recieves 404 |

  @retrieveDietician
  Scenario Outline: Verify retrieve dietician by ID
    Given Dietician already created
    And User is setting auth as "<Scenario>"
    When Retrieving dietician by ID reading from JSON data file
    Then User should see <Expected>

    Examples: 
      | Scenario                            | Expected           |
      | RetrieveDietician_ValidAuth         | Admin recieves 200 |
      | RetrieveDietician_NoAuth            | Admin recieves 401 |
      | RetrieveDietician_InvaliMethod_POST | Admin recieves 405 |
      | RetrieveDietician_InvaliEndPoint    | Admin recieves 404 |

  @retrieveDietician
  Scenario Outline: Verify retrieve dietician by ID
    Given Dietician already created
    And User is setting auth as "<Scenario>"
    When Retrieving dietician by ID reading invalid ID from JSON data file
    Then User should see <Expected>

    Examples: 
      | Scenario                            | Expected           |
      | RetrieveDietician_InvaliDieticianID | Admin recieves 404 |
