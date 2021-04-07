Feature: Create account
  As a user, I want to create an account with MailChimp so that I can access its features.
  - User needs to input a valid and unique username
  - User needs to input a valid and unique email address
  - User needs to input a valid password
  - Feedback should be returned upon incorrect user input
  - Feedback should be returned upon successful account creation

  Scenario Outline: Account creation
    Given I have opened up the MailChimp account user registration page
    And I enter <email> in the Email field
    And I enter <username> in the Username field
    And I enter <password> in the Password field
    When I press the Sign-up button
    Then I get a <feedback> and verify the result

    Examples: 
      | email   | username   | password | feedback                             |
      | "valid" | "valid"    | "valid"  | "confirmation"                       |
      | "valid" | "too long" | "valid"  | "error message - too long user"      |
      | "valid" | "existing" | "valid"  | "error message - already taken user" |
      | "none"  | "valid"    | "valid"  | "error message - none entered email" |
