package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriveCreator {

	public WebDriver createBrowser (String browser) {
		WebDriver driver;
		
		if(browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jesper\\eclipse\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if(browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jesper\\eclipse\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\Jesper\\eclipse\\Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();	
		}

		return driver;
		
	}
}
