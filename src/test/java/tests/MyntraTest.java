package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.util.concurrent.Uninterruptibles;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyntraTest {
	@Parameters("Browser")
	@Test
	public void myntraTest(String browserName) {
		System.out.println("browser name is " + browserName);
		WebDriver driver = null;

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--disable-notifications");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(options);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			FirefoxProfile ffprofile = new FirefoxProfile();
			ffprofile.setPreference("dom.webnotifications.enabled", false);
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(ffprofile);
			driver = new FirefoxDriver(options);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.myntra.com/");
		WebElement men = driver.findElement(By.xpath("//a[@class='desktop-main' and text()='Men']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(men).build().perform();
		WebElement tshirts = driver
				.findElement(By.xpath("//a[text()='Topwear']/parent::li/following-sibling::li/a[text()='T-Shirts']"));
		actions.moveToElement(tshirts).click().build().perform();
		WebElement brandMore = driver
				.findElement(By.xpath("//div[contains(@class,' brand-container')]/div[@class='brand-more']"));
		actions.moveToElement(brandMore).click().build().perform();
		WebElement brandH = driver
				.findElement(By.xpath("//li[@data-item='h' and not(contains(@class,'FilterDirectory-listTitle'))]"));
		actions.moveToElement(brandH).click().build().perform();
		List<WebElement> brandsH = driver.findElements(By.xpath(
				"//li[@data-item='h' and contains(@class,'FilterDirectory-listTitle')]/following-sibling::li/label/input[starts-with(@value,'H') or starts-with(@value,'h')]"));
		Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
		int items = 0;
		for (int i = 0; i < brandsH.size(); i++) {
			String brandName = brandsH.get(i).getAttribute("value").trim();
			if (brandName.equalsIgnoreCase("HRX by Hrithik Roshan") || brandName.equalsIgnoreCase("HERE&NOW")) {
				String itemsStr = brandsH.get(i).findElement(By.xpath(".//following-sibling::span")).getText().trim()
						.replaceAll("[^0-9]", "");
				items = items + Integer.parseInt(itemsStr);
				System.out.println("Total Items Under: " + brandName + " is " + Integer.parseInt(itemsStr));
				actions.moveToElement(brandsH.get(i)).click().build().perform();
				Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
			}
		}

		// Assert.assertEquals(items, 3652, "No of Items mismatched");
		String currentUrl = driver.getCurrentUrl();
		// Assert.assertTrue(currentUrl.contains("men-tshirts?f=Brand%3AHERE%26NOW%2CHRX%20by%20Hrithik%20Roshan"),
		// "URL not contains given string");
	}

}
