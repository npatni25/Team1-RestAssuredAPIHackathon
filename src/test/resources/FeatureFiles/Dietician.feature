#Author: npatni25@gmail.com
@tag
Feature: Verify create dieticians sceanrios

  Background: 
    Given Admin user loggedin

  #//////////////////	Create Dietician by reading data from json data file//////////////
  #@createDietician
  #Scenario Outline: Create Dietician with all the valid informations
    #Given User is creating dietician "<Scenario>"
    #When post condition with valid data from json data file
    #Then A new dietician should get created
#
    #Examples: 
      #| Scenario                  | Expected           |
      #| CreateDietician_ValidAuth | Admin recieves 201 |

  #//////////////////	Create Dietician with auto generated unique fields - Not reading from json data file//////////////
  @createDietician
  Scenario: Create Dietician with all the valid informations
    Given Admin user loggedin
    When post condition with auto generate valid data
    Then A new dietician should get created

  @createDietician
  Scenario Outline: Verify invalid authentication Scenarios
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with No Auth after providing valid data from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario                    | Expected                        |
      | CreateDietician_InvalidAuth | Admin recieves 401 unauthorized |

  @createDietician
  Scenario Outline: Verify invalid authentication Scenarios
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with Dietician token after providing valid data from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario               | Expected                                                |
      | Dietician bearer token | Admin recieves 403 forbidden for Dietician bearer token |

  @createDietician
  Scenario Outline: Verify invalid authentication Scenarios
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with Patient token after providing valid data from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario             | Expected                                              |
      | Patient bearer token | Admin recieves 403 forbidden for Patient bearer token |

  @createDietician
  Scenario Outline: Verify create dietician with invalid data
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with invalid data from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario                     | Expected                             |
      | CreateDietician_Invalid Data | Admin recieves 400 Bad Request error |

  @createDietician
  Scenario Outline: Verify create dietician with invalid PinCode
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with invalid PinCode from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario                        | Expected                                               |
      | CreateDietician_Invalid PinCode | Admin recieves "Pincode should contain 6 digits" error |

  @createDietician
  Scenario Outline: Verify create dietician with exisiting contact and DOB in combination
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with invalid PinCode from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario                             | Expected                                                                                                        |
      | CreateDietician_Same Contact and DOB | Admin recieves same cont & dob error "Dietician user with given date of birth and contact already exists" error |

  @createDietician
  Scenario Outline: Verify create dietician with invalid API request_PUT
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with invalid API request
    Then User should see <Expected>

    Examples: 
      | Scenario                             | Expected                                    |
      | CreateDietician_InvaliAPIRequest_PUT | Admin recieves 405 Method Not Allowed error |

  @createDietician
  Scenario Outline: Verify create dietician with valid data and invalid endpoint
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with invalid End Point
    Then User should see <Expected>

    Examples: 
      | Scenario                                  | Expected                           |
      | CreateDietician_ValidData_InvalidEndPoint | Admin recieves 404 Not Found error |

  @createDietician
  Scenario Outline: Verify create dietician with valid data and invalid content type
    Given User is setting auth as "<Scenario>"
    When Post condition to create dietician with invalid data from json data file
    Then User should see <Expected>

    Examples: 
      | Scenario                                     | Expected                                        |
      | CreateDietician_ValidData_InvalidContentType | Admin recieves 415 unsupported media type error |

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
