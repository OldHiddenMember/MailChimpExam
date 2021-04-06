package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.security.SecureRandom;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private WebDriver driver;

	private String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private SecureRandom rnd = new SecureRandom();

	@Before
	public void select_chrome() {
		DriveCreator browser = new DriveCreator();
		driver = browser.createBrowser("chrome");
	}

	@Given("I have opened up the MailChimp account user registration page")
	public void i_have_opened_up_the_mail_chimp_account_user_registration_page() {
		driver.get("https://login.mailchimp.com/signup/");
	}

	@When("I enter valid email in the Email field")
	public void i_enter_valid_email_in_the_email_field() {

		WebElement emailField = driver.findElement(By.id("email"));
		StringBuilder sb = new StringBuilder(12);
		for (int i = 0; i < 12; i++)
			sb.append(charList.charAt(rnd.nextInt(charList.length())));
		String email = sb + "@" + sb + ".cx";
		emailField.sendKeys(email);
		assertEquals(email, emailField.getAttribute("value"));
	}

	@When("I enter valid username in the Username field")
	public void i_enter_valid_username_in_the_username_field() {

		WebElement userField = driver.findElement(By.id("new_username"));
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < 16; i++)
			sb.append(charList.charAt(rnd.nextInt(charList.length())));
		userField.sendKeys(sb);
	}

	@When("I enter valid password in the Password field")
	public void i_enter_valid_password_in_the_password_field() {

		WebElement userPassword = driver.findElement(By.id("new_password"));
		userPassword.sendKeys("SamePasswordForEvery1!");

		List<WebElement> elements = driver.findElements(By.tagName("li"));
		for (WebElement e : elements) {
			assertEquals(false, e.isDisplayed());
		}
	}

	@When("I enter existing username in the Username field")
	public void i_enter_existing_username_in_the_username_field() {

		WebElement userField = driver.findElement(By.id("new_username"));
		userField.sendKeys("user");
	}

	@When("I enter no email in the Email field")
	public void i_enter_no_email_in_the_email_field() {
		// leaves the email field blank
	}

	@When("I enter too long username in the Username field")
	public void i_enter_too_long_username_in_the_username_field() {

		WebElement userField = driver.findElement(By.id("new_username"));
		StringBuilder sb = new StringBuilder(101);
		for (int i = 0; i < 101; i++)
			sb.append(charList.charAt(rnd.nextInt(charList.length())));
		userField.sendKeys(sb);

	}

	@When("I press the Sign-up button")
	public void i_press_the_sign_up_button() throws InterruptedException {

		WebElement userPassword = driver.findElement(By.id("new_password"));
		userPassword.sendKeys(Keys.ENTER);

	}

	@Then("I get a new confirmation page and verify the result")
	public void i_get_a_new_confirmation_page_and_verify_the_result() throws InterruptedException {
		WebElement confirmation = driver
				.findElement(By.cssSelector("h1[class='!margin-bottom--lv3 no-transform center-on-medium']"));
		assertEquals("Check your email", confirmation.getText());
	}

	@Then("I get a error message in the email field - none entered and verify the result")
	public void i_get_a_error_message_in_the_email_field_none_entered_and_verify_the_result()
			throws InterruptedException {
		WebElement errorEmail = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
		assertEquals("Please enter a value", errorEmail.getText());
		WebElement errorMessage = driver.findElement(By.id("av-flash-errors"));
		assertEquals("Please check your entry and try again.", errorMessage.getText());
	}

	@Then("I get a error message in the user field - too long and verify the result")
	public void i_get_a_error_message_in_the_user_field_too_long_and_verify_the_result() throws InterruptedException {
		WebElement errorTooLongUsername = driver
				.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
		assertEquals("Enter a value less than 100 characters long", errorTooLongUsername.getText());
		WebElement errorMessage = driver.findElement(By.id("av-flash-errors"));
		assertEquals("Please check your entry and try again.", errorMessage.getText());
	}

	@Then("I get a error message in the user field - already taken and verify the result")
	public void i_get_a_error_message_in_the_user_field_already_taken_and_verify_the_result()
			throws InterruptedException {
		WebElement errorMessage = driver.findElement(By.id("av-flash-errors"));
		assertEquals("Please check your entry and try again.", errorMessage.getText());
		WebElement duplicateUserError = driver
				.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
		assertEquals("Another user with this username already exists. Maybe it's your evil twin. Spooky.",
				duplicateUserError.getText());

	}

	public void click(By by) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(by));
		driver.findElement(by).click();
	}

	@After
	public void tearDown() {
		driver.quit();
	}
}
