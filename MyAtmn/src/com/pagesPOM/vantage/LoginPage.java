package com.pagesPOM.vantage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage 
{
//	driver.findElement(By.xpath("//input[@type='text']")).sendKeys("sysadmin");
	@FindBy(xpath="//input [ @name='username' or @type='text' ]")
	private static WebElement usernm;
	public WebElement usernmTB(String unTxt)
	{	usernm.sendKeys(unTxt); 
		return usernm; 	}
	
//    driver.findElement(By.xpath("//input[@type='password']")).sendKeys("facetime");
	@FindBy(xpath="//input [@type='password' or name='password']")
	private static WebElement pwd;
	public WebElement pwdTB(String pwTxt)
	{	pwd.sendKeys(pwTxt);	
		return pwd; 	}
	
//    driver.findElement(By.xpath("//input[@type='submit']")).click();
	@FindBy(xpath="//input [@class='button' or @type='submit' or @value='Login']")
	private static WebElement loginBTN;
	public WebElement loginBTN()
	{	return loginBTN;	}
	
	@FindBy(xpath="//a[@title='About Vantage']")
	private static WebElement aboutVantage;
	public WebElement aboutVtgBTN(String un)
	{	return aboutVantage;	}
	

}
