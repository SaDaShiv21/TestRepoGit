package com.actiance.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Login {
	
	public static ChromeDriver openChrome(String vantageIP) {
		String url="https:\\"+vantageIP+":8443\\ima";
		System.setProperty("webdriver.chrome.driver", "./drivers/ChromeDriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.get(url);
		driver.findElement(By.xpath("//button[contains(text(),'Advanced')]")).click();
		driver.findElement(By.partialLinkText("Proceed to")).click();
		return driver;
	}
	public static WebDriver openfirefox(String vantageIP) {
		String url="https:\\"+vantageIP+":8443\\ima";
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get(url);
		return driver;
	}
	public static WebDriver openBrowser(String browser,String vantageIP) {
		WebDriver driver = null;
		if(browser.equalsIgnoreCase("chrome")) {driver=openChrome(vantageIP);}
		else if(browser.equalsIgnoreCase("firefox")) {driver=openfirefox(vantageIP);}
		else {System.out.println("Invalide browserType provided browserType:"+browser);}
		//driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
		return driver;
	}
}