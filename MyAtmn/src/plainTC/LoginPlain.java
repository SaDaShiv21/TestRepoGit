package plainTC;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import generics.WebElementHighlighter;

public class LoginPlain 
{

	public static void main(String[] args) throws InterruptedException 
	{
		System.out.println("Enter BrowserLauncher>>chrome");
		System.setProperty("webdriver.chrome.driver", "./driver/ChromeDriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
		
		driver.get("https://tenant1:8443");
		
		WebElementHighlighter wh = new WebElementHighlighter();
		
		WebElement un = driver.findElement(By.xpath("//input [@type='text' or @name='username']"));
		//WebElementHighlighter.helper(driver, un);
		un.sendKeys("sysadmin");
		// pwd-xpath="//input[@type='password']"
		WebElement pw = driver.findElement(By.xpath("//input [@type='password' or name='password']"));
		pw.sendKeys("FaceTime@123");
		//xpath=//input[@type='submit']
		WebElement lbtn =driver.findElement(By.xpath("//input [@class='button' or @type='submit' or @value='Login']"));
		lbtn.click();
		
	}

}
