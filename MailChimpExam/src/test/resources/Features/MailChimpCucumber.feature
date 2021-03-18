
Feature: Create account
  I want to create an account with MailChimp

  Scenario Outline: Title of your scenario outline
    Given I have opened up the MailChimp account user registration page
    When I enter <email> in the Email field
    And I enter <username> in the Username field
    And I enter <password> in the Password field
    Then 

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
