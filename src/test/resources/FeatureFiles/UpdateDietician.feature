#Author: npatni25@gmail.com
@tag
Feature: Verify retrieve dieticians sceanrios

  Background: 
    Given Admin user loggedin

  @updateDietician
  Scenario Outline: Verify update dietician
    Given User is setting auth to verify "<Scenario>" after creating dietician
    When Updating dietician reading from JSON data file
    Then User should see <Expected>

    Examples: 
      | Scenario                                                             | Expected           |
      | UpdateDietician_NoAuth                                               | Admin recieves 401 |
      | UpdateDietician_withDieticianToken                                   | Admin recieves 403 |
      | UpdateDietician_withPatientToken                                     | Admin recieves 403 |
      | UpdateDietician_ValidAuth                                            | Admin recieves 200 |
      | UpdateDietician_ValidAuth_update only mandatory fields               | Admin recieves 200 |
      | UpdateDietician_ValidAuth_update only non-mandatory fields           | Admin recieves 400 |
      | UpdateDietician_ValidAuth_invalid data and invalid dieticianID       | Admin recieves 400 |
      | UpdateDietician_ValidAuth_valid data and invalid dieticianID         | Admin recieves 400 |
      | UpdateDietician_ValidAuth_validData_validDieticianID_InvalidMethod   | Admin recieves 405 |
      | UpdateDietician_ValidAuth_validData_validDieticianID_InvalidEndPoint | Admin recieves 404 |
