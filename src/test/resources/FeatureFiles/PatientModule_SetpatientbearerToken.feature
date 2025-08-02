Feature: Post Operation [create patient]

Background: Set patient bearer token

Scenario: Check patient able to create patient with valid data and patient token
Given Patient creates POST request by entering valid data into the form-data key and value fields
When Patient send POST http request with endpoint
Then Patient recieves 403 forbidden