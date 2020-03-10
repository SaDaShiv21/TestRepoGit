package generics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.pagesPOM.vantage.LoginPage;
import generics.PropsReader;

public class BrowserLauncher 
{
	
	public static WebDriver chrome() 
	{		
		System.out.println("Enter BrowserLauncher>>chrome");
		System.setProperty("webdriver.chrome.driver", "./driver/ChromeDriver.exe");
		ChromeDriver driver = new ChromeDriver();
		//For getting values from properties file
		PropsReader p= new PropsReader();
		driver.get("https://"+p.reader("link")+":8443/ima");
		//driver.get("https://"+p.reader("link")+":8443/ima");
		
		LoginPage lp=PageFactory.initElements(driver, LoginPage.class);
		System.out.println("values:"+p.reader("user")+" "+p.reader("password"));
		
		lp.usernmTB(p.reader("user"));
		lp.pwdTB(p.reader("password"));
		lp.loginBTN().click();
		System.out.println("Finish BrowserLauncher>>chrome");
		return driver;
	}
}
