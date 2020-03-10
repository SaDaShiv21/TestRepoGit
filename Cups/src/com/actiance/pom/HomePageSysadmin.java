package com.actiance.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageSysadmin 
{
	@FindBy(xpath="//a[text() = 'Dashboard']")
	private static WebElement Dashboard;
	public WebElement Dashboard()
	{	
		return Dashboard;	
	}
	
	@FindBy(xpath="//a[text() = 'Policies']")
	private static WebElement Policies;
	public WebElement Policies()
	{	
		return Policies;	
	}
	
	@FindBy(xpath="//a[text() = 'Groups']")
	private static WebElement Groups;
	public WebElement Groups()
	{	return Groups;		}
	
	@FindBy(xpath="//a[text() = 'Employees']")
	private static WebElement Employees;
	public WebElement Employees()
	{		return Employees;		}
	
	@FindBy(xpath="//a[text() = 'Reports']")
	private static WebElement Reports;
	public WebElement Reports()
	{	
		return Reports;	
	}
	
	@FindBy(xpath="//a[text() = 'Configuration']")
	//OldVtg--////table[@class='mainMenu']//td//a[text() = 'Configuration']
	private static WebElement Configuration;
	public WebElement Configuration()
	{	
		return Configuration;	
	}
	
	@FindBy(xpath="//a[text() = 'Preferences']")
	private static WebElement Preferences;
	public WebElement Preferences()
	{	
		return Preferences;	
	}
}
