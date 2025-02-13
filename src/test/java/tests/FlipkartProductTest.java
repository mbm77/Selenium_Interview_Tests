package tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.util.concurrent.Uninterruptibles;

public class FlipkartProductTest {
	

	
	@Test
	public void flipkartProductTest() throws InterruptedException, MalformedURLException {
		//WebDriverManager.chromedriver().setup();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("chrome");
		
		WebDriver driver = new RemoteWebDriver(new URL("http://chrome:4444"),capabilities);
		driver.manage().window().maximize();
		
		driver.get("https://www.flipkart.com/");
		WebElement search = driver.findElement(By.name("q"));
		String product = "fridge";
		for (int i = 0; i < product.length(); i++) {
			StringBuilder sb = new StringBuilder();
			String strChar = sb.append(product.charAt(i)).toString();
			search.sendKeys(strChar);
		}
		 Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		List<WebElement> proElements = driver.findElements(By.xpath("//li//a/child::div[2]"));
		Actions actions = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (WebElement pro : proElements) {
			String productName = pro.getAttribute("innerHTML").replaceAll("<[^>]*>", " ").replace("  ", " ").trim();
			
			System.out.println(productName);
			if (productName.contains("fridge in Refrigerators") || productName.contains("fridge 5 stars in Refrigerators")) {
				js.executeScript("arguments[0].click()", pro);
				break;
			} 
		}
		 Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));

		 
		WebElement firstOption = driver.findElement(By.xpath("(//div[@style='height: 200px; width: 200px;']/img)[1]"));
		actions.moveToElement(firstOption).click().perform();
		Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
		String parentWin = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();

		for (String win : windowHandles) {
			if (!win.equals(parentWin)) {
				driver.switchTo().window(win);
				WebElement pincode = driver.findElement(By.id("pincodeInputId"));
				pincode.sendKeys("110092");
				driver.findElement(By.xpath("//span[text()='Check']")).click();
				Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
				driver.close();
			}
		}

		driver.switchTo().window(parentWin);

		Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
		WebElement brand = driver.findElement(By.xpath("//div[@title='SAMSUNG']//input[@type='checkbox']"));
		actions.moveToElement(brand).click().build().perform();
		Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
		driver.findElement(By.xpath("//div[text()='Capacity']")).click();
		WebElement capacity = driver.findElement(By.xpath("//div[text()='301 - 400 L']/preceding-sibling::input"));
		actions.moveToElement(capacity).click().build().perform();
		Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
		String resultStr = driver.findElement(By.xpath("//span[contains(text(),'Showing')]")).getText();
		String totalProductsStr = resultStr.substring(resultStr.lastIndexOf("of") + 3,
				resultStr.lastIndexOf("results") - 1);
		int totalProducts = Integer.parseInt(totalProductsStr);
		System.out.println("Total Products : " + totalProducts);

		WebElement notDeliverProduct = driver.findElement(By.xpath(
				"(//img/parent::div[@style='height: 200px; width: 200px;']/parent::div/following-sibling::div/span)[1]"));
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].scrollIntoView(true)", notDeliverProduct);
		actions.moveToElement(notDeliverProduct).build().perform();

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		String encodedString = takesScreenshot.getScreenshotAs(OutputType.BASE64);
		byte[] byteArr = Base64.getDecoder().decode(encodedString);
		try {
			FileOutputStream fos = new FileOutputStream(
					new File(System.getProperty("user.dir") + "/screenshots/product.png"));
			fos.write(byteArr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement lastProduct = driver.findElement(By.xpath(
				"((//img/parent::div[@style='height: 200px; width: 200px;']/parent::div/following-sibling::div/span)[1]/parent::div/preceding-sibling::div/child::div/preceding::div[@style='height: 200px; width: 200px;'])[last()]"));
		File source = lastProduct.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(System.getProperty("user.dir") + "/screenshots/last_product.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(System.getProperty("user.dir") + "/screenshots/last_product.png");

		Assert.assertTrue(file.exists());

		WebElement productName = driver.findElement(By
				.xpath("(//div[@style='width: 100%;']//div[contains(@class,'row')]/child::div[1]/child::div[1])[23]"));
		String product_name = productName.getText();

		System.out.println("Product Name : " + product_name);
		String brandName = product_name.split(" ")[0];
		String capacityValue = product_name.split(" ")[1];
		Assert.assertTrue(product_name.contains(brandName));
		Assert.assertTrue(product_name.contains(capacityValue));

		WebElement price = driver.findElement(By.xpath(
				"(//div[@style='width: 100%;']//div[contains(@class,'row')]/child::div[2]/child::div[1]/child::div/child::div[1])[23]"));
		String productPrice = price.getText();
		System.out.println("Product Price : " + productPrice);
		
	}


}
