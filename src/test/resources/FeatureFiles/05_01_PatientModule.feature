Feature: Verify create patient as a dieticiation sceanrios


  
  Scenario: Create Dietician with all the valid informations
    Given Admin user loggedin1
    When post condition with valid data from sheet "CreateDietician" with test case no "TC01" is provided1
    Then A new dietician should get created1
    
    

  Scenario: Check dietician able to create patient with valid data and token
    Given Dietician is logged in
    When Dietician sends POST http request with valid data and token
    Then Dietician recieves 201 created and with response body.
    
   
   Scenario: Check dietician able to add new reports for existing patient only with valid mandatory details
   Given Dietician creates PUT request by entering valid data ( Mandatory only) into the form-data key and value fields and valid patient ID
   When Dietician send PUT http request with endpoint
   Then Dietician recieves 200 ok and with updated response body. 
   
   Scenario: Check dietician able to create patient only with valid additional details
  Given Dietician creates POST request only with valid additional details in the form-data fields
  When Dietician sends POST http request with endpoint
  Then Dietician receives 400 Bad Request
 
 Scenario: Check dietician able to create patient with invalid data (mandatory details)
  Given Dietician creates POST request only by invalid mandatory details into the form-data key and value fields
  When Dietician send POST http request with endpoint
  Then Dietician receives 400 Bad Request

  Scenario: Check dietician able to create patient with valid mandatory fields and invalid data (additional details)
    Given Dietician creates POST request only by invalid additional details into the form-data key and value fields
    When Dietician sends POST http request with endpoint
    Then Dietician receives 400 Bad Request

  Scenario: Check dietician able to create patient with valid data and invalid method
    Given Dietician creates Post request by entering valid data into the form-data key and value fields.
    When Dietician sends Post http request with endpoint
    Then Dietician receives 405 Method Not Allowed
    
   Scenario: Check dietician able to create patient with valid data and invalid endpoint
  Given Dietician creates POST request by entering valid data into the form-data key and value fields.
  When Dietician sent POST http request with invalid endpoint
  Then Dietician receives 404 not found
   
   Scenario: Check dietician able to create patient with valid data and invalid content type
  Given Dietician creates POST request by entering valid data into the form-data key and value fields and invalid content type
  When Dietician send POST http request with endpoint
  Then Dietician recieves 415 unsupported media type
    
  
   Scenario: Check dietician able to add new reports for existing patient only with valid additional details
   Given Dietician creates PUT request by entering valid data ( Additional details only) into the form-data key and value fields and valid patient ID
   When Dietician send PUT http request with endpoint
   Then Dietician recieves 200 ok and with updated response body. 
   
    
   Scenario: Check dietician able to add new reports  for existing patient with invalid data
   Given Dietician creates PUT request by entering invalid data ( Additional details only) into the form-data key and value fields and valid patient ID
   When Dietician send PUT http request with endpoint
   Then Dietician recieves 400 Bad request
   
   Scenario: Check dietician able to add new reports for existing patient with valid data and invalid patient id as path parameter
   Given Dietician creates PUT request by entering invalid data ( Additional details only) into the form-data key and value fields and invalid patient ID
   When Dietician send PUT http request with endpoint
   Then Dietician recieves 404 not found
   
   Scenario: Check dietician able to add new reports for existing patient  with valid data and invalid method
   Given Dietician creates POST request by entering valid data into the form-data key and value fields and valid patient ID
   When Dietician send POST http request with endpoint
   Then Dietician recieves 405 method not allowed
   
   Scenario: Check dietician able to add new reports for existing patient with valid data and invalid endpoint
   Given Dietician creates POST request by entering valid data into the form-data key and value fields and valid patient ID
   When Dietician sent PUT http request with invalid endpoint
   Then Dietician recieves 404 not found
   
      
   Scenario: Check dietician able to add new reports for existing patient  with valid data and invalid content type
   Given Dietician creates PUT request by entering valid data into the form-data key and value fields and valid patient ID with invalid content type
   When Dietician send PUT http request with endpoint
   Then Dietician recieves 415 unsupported media type