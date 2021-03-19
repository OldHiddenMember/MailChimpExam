Feature: Create account
  I want to create an account with MailChimp

  Scenario Outline: Account creation
    Given I have opened up the MailChimp account user registration page
    When I enter <email> in the Email field
    And I enter <username> in the Username field
    And I enter <password> in the Password field
    And I press the Sign-up button
    Then I get a <feedback> and verify the result

    Examples: 
      | email       | username          | password       | feedback                                        |
     # | valid email | valid username    | valid password | new confirmation page                           |
      #| valid email | too long username | valid password | error message in the user field - too long      |
      | valid email | existing username | valid password | error message in the user field - already taken |
      #| no email    | valid username    | valid password | error message in the email field - none entered |
