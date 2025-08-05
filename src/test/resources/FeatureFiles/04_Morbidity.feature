@MorbidityModule @Dietician
Feature: Dietician API Morbidity Module

  Background: 
    Given Admin sets Authorization to Bearer Token for morbidity
   
   @GetallMorbidities 
  Scenario Outline: Admin creates GET all Request for Morbidity Module
    Given Admin creates GETAll request "<requestType>" for Morbidity Module
    When Admin sends HTTPS Request with endpoint GETAll for Morbidity Module "<requestType>"
    Then Admin receives statuscode  "<Status>" for Morbidity Module get all

    Examples: 
      | requestType            | Status |
      | GetAllValidMorbidity     |    200 |
      | GetAllMorbidityInValidEP |    404 |
      
   @GetMorbidityByMorbidityName
  Scenario Outline: Check if admin able to retrieve Morbidity by MorbidityName
    Given Admin creates GET Request by "<requestType>" for Morbidity module
    When Admin sends GET Request by  "<requestType>" MorbidityName for Morbidity Module
    Then Admin gets the Morbidity details of that MorbidityName with status  "<Status>"

    Examples: 
      | requestType                        | Status |
      | GetMorbidityByvalidMorbidityName   |    200 |
      | GetMorbidityByInvalidMorbidityName |    400 |
      |GetMorbidityByNameInValidEP         |    404 |
     