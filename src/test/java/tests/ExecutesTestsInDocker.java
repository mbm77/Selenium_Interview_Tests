package tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.google.common.util.concurrent.Uninterruptibles;

public class ExecutesTestsInDocker {
	
	@Test
	public void localTest() throws MalformedURLException {
	//Capabilities capabilities = new ImmutableCapabilities("browserName", "chrome");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		driver.get("https://www.google.co.in/");
		System.out.println("Title is "+driver.getTitle()+" Thread = "+Thread.currentThread().getId());
		//Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
		driver.quit();;
	}
	
	@Test
	public void localTest2() throws MalformedURLException {
		//Capabilities capabilities = new ImmutableCapabilities("browserName", "chrome");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setBrowserName("firefox");
			WebDriver driver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), cap);
			driver.get("https://www.google.co.in/");
			System.out.println("Title is "+driver.getTitle()+" Thread = "+Thread.currentThread().getId());
			//Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
			driver.quit();
		}

}
