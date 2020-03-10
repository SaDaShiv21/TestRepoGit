package com.testcases.vantage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.pagesPOM.vantage.HomePage;

import generics.BrowserLauncher;

public class TestEcl 
{
	public static void main(String[] args) throws Exception 
	{
		BrowserLauncher br =new BrowserLauncher();
		WebDriver driver = br.chrome();
		HomePage hp=PageFactory.initElements(driver, HomePage.class);
		hp.Groups().click();
		driver.findElement(By.xpath("(//table[@id='employees']//input[@type='checkbox'])[6]")).click();
		
		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.id("deactivate2")).click();
		
		for (String winHandle : driver.getWindowHandles())
		{	
			driver.switchTo().window(winHandle);			
		}
		driver.findElement(By.xpath("//input[@name='Submit']")).click();
//		driver.close();
		driver.switchTo().window(parentHandle);
		hp.Dashboard().click();
		hp.Groups().click();
	}
}
