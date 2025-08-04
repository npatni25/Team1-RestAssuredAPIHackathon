#Author: npatni25@gmail.com
@tag
Feature: Verify delete dieticians sceanrios

  Background: 
    Given Admin user loggedin

  @deleteDietician
  Scenario Outline: Verify delete dietician
    Given User is setting auth to verify "<Scenario>" after creating dietician
    When Delete dietician reading from JSON data file
    Then User should see <Expected>

    Examples: 
      | Scenario                                        | Expected           |
      | DeleteDietician_NoAuth                          | Admin recieves 401 |
      | DeleteDietician_Admin_ByValidID                 | Admin recieves 200 |
      | DeleteDietician_Admin_ByValidID_InvalidMethod   | Admin recieves 405 |
      | DeleteDietician_Admin_By_inValidID              | Admin recieves 404 |
      | DeleteDietician_Admin_ByValidID_InvalidEndPoint | Admin recieves 404 |
