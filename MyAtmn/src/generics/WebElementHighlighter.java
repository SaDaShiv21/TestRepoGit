package generics;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebElementHighlighter 
{

	public static void helper(WebDriver driver,WebElement element) throws InterruptedException
	{
		// Create the  JavascriptExecutor object
		JavascriptExecutor js=(JavascriptExecutor)driver; 
		// call the executeScript method
		js.executeScript("arguments[0].setAttribute('style,' border: solid 2px red;');", element);
			Thread.sleep(800);
		js.executeScript("arguments[0].setAttribute('style,'border: solid 2px white');", element);		
	}
}
