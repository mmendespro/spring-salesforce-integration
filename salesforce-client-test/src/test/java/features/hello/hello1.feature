Feature: Hello World

Background:
  * url baseUrl

Scenario: Hello world
  Given path '/api/hello'
  When method GET
  Then status 200
  And match $ == "Hello world!"

Scenario: Hello with a param
  Given path '/api/hello'
  Given param name = 'Daas'
  When method GET
  Then status 200
  And match $ == "Hello Daas!"