
Feature: Post Operation [create patient]

Background: Set admin bearer token

Scenario: Check admin able to create patient with valid data and admin token
Given Admin creates POST request by entering valid data into the form-data key and values
When Admin send POST http request with endpoint
Then Admin recieves 403 forbidden