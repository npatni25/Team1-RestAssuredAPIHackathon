Feature: Post Operation [create patient]

Background: Set dietician bearer token

#Scenario Outline: Check dietician able to create patient with valid data and token
# Given Dietician sets the bearer token and creates POST request with "<testCase>". (mandatory and additional details)
# When Dietician sends POST HTTP request with endpoint to the create patient with "<testCase>"
# Then Dietician receives 201 Created status and response body with auto-generated patient ID and login password
 
# Examples:
# | testCase|
 #|  t1     |


Scenario: Check dietician able to create patient with valid data and token 
Given Dietician is loggedin
When Dietician send POST http request with endpoint
Then Dietician recieves 201 

#Scenario: Check dietician able to create patient only with valid mandatory details
#Given Dietician creates POST request only by valid mandatory details into the form-data key and value fields
#When Dietician send POST http request with endpoint
#Then Dietician recieves 201 created and with response body. (Auto created dietician ID and login password)