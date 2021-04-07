package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.security.SecureRandom;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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
		driver.manage().window().fullscreen();
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-reject-all-handler")));
		WebElement consent = driver.findElement(By.id("onetrust-reject-all-handler"));
		consent.click();

	}

	@Given("I enter {string} in the Email field")
	public void i_enter_in_the_email_field(String emailInput) {

		switch (emailInput) {
		case "valid":
			WebElement emailField = driver.findElement(By.id("email"));
			StringBuilder sb = new StringBuilder(12);
			for (int i = 0; i < 12; i++)
				sb.append(charList.charAt(rnd.nextInt(charList.length())));
			String email = sb + "@" + sb + ".cx";
			emailField.sendKeys(email);
			assertEquals(email, emailField.getAttribute("value"));

			break;
		default:
			// enter nothing in the emailfield
		}
	}

	@Given("I enter {string} in the Username field")
	public void i_enter_in_the_username_field(String usernameInput) {

		WebElement userField = driver.findElement(By.id("new_username"));
		StringBuilder sb;
		switch (usernameInput) {
		case "valid":

			sb = new StringBuilder(16);
			for (int i = 0; i < 16; i++)
				sb.append(charList.charAt(rnd.nextInt(charList.length())));

			break;
		case "too long":
			sb = new StringBuilder(101);
			for (int i = 0; i < 101; i++)
				sb.append(charList.charAt(rnd.nextInt(charList.length())));

			break;
		case "existing":
			sb = new StringBuilder();
			sb.append("user");
			break;

		default:
			sb = null;

		}
		userField.sendKeys(sb);

	}

	@Given("I enter {string} in the Password field")
	public void i_enter_in_the_password_field(String passwordInput) {

		WebElement userPassword = driver.findElement(By.id("new_password"));
		userPassword.sendKeys("SamePasswordForEvery1!");

		List<WebElement> elements = driver.findElements(By.tagName("li"));
		for (WebElement e : elements) {
			assertEquals(false, e.isDisplayed());
		}
	}

	@When("I press the Sign-up button")
	public void i_press_the_sign_up_button() throws InterruptedException {

		WebElement signUp = driver.findElement(By.id("create-account"));

		signUp.click();

	}

	@Then("I get a {string} and verify the result")
	public void i_get_a_and_verify_the_result(String result) {
		WebElement errorMessage;
		switch (result) {
		case "confirmation":
			WebElement confirmation = driver
					.findElement(By.cssSelector("h1[class='!margin-bottom--lv3 no-transform center-on-medium']"));
			assertEquals("Check your email", confirmation.getText());
			break;
		case "error message - too long user":
			WebElement errorTooLongUsername = driver
					.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals("Enter a value less than 100 characters long", errorTooLongUsername.getText());
			errorMessage = driver.findElement(By.id("av-flash-errors"));
			assertEquals("Please check your entry and try again.", errorMessage.getText());

			break;
		case "error message - already taken user":

			WebElement duplicateUserError = driver
					.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals("Another user with this username already exists. Maybe it's your evil twin. Spooky.",
					duplicateUserError.getText());
			errorMessage = driver.findElement(By.id("av-flash-errors"));
			assertEquals("Please check your entry and try again.", errorMessage.getText());

			break;
		case "error message - none entered email":
			WebElement errorEmail = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
			assertEquals("Please enter a value", errorEmail.getText());
			errorMessage = driver.findElement(By.id("av-flash-errors"));
			assertEquals("Please check your entry and try again.", errorMessage.getText());
		}
	}

	@After
	public void tearDown() {
		driver.quit();
	}
}
