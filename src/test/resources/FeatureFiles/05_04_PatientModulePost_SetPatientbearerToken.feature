Feature: Post Operation [create patient]


Scenario: Check admin able to create patient with valid data and admin token
  Given Admin creates POST request by entering valid data into the form-data key and value fields.
  When Admin send POST http request with endpoint
  Then Admin receives 403 Forbidden