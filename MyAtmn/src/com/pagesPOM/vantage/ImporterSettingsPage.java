package com.pagesPOM.vantage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ImporterSettingsPage 
{
	@FindBy(xpath="//input[@name='employeeSearchLocation']") private static WebElement LdapSearchLocation;
	public WebElement ldapSearchLocationTB()
	{	return LdapSearchLocation;	}
	

	@FindBy(xpath="//input[@name='employeeEmailDomains']") private static WebElement eDomains;
	public WebElement empEmailDomain()
	{	return eDomains;	}
	
	@FindBy(xpath="//input [@id='go']") private static WebElement go;
	public WebElement goBtn()
	{	return go;	}
	
	@FindBy(xpath="//input[@value='Create New']") private static WebElement createNew;
	public WebElement createNewBtn()
	{	return createNew;	}
	
	@FindBy(xpath="//a[contains(text(),'Setup')]") private static WebElement setp;
	public WebElement setup()
	{	return setp;	}
	
	@FindBy(xpath="//input[@name='importerEnabled']") private static WebElement enableCB;
	public WebElement enableCheckBox()
	{	return enableCB;	}
	
	@FindBy(xpath="//input[@name='fileLocation']") private static WebElement fileLoc;
	public WebElement fileLocation()
	{	return fileLoc;	}
	
	//serverIDs position 1 to 2
	//server ID logic
    public static void serverIdSelect(WebDriver driver, int optionValue) 
	 {
	  if( !driver.findElement(By.xpath("//select [@id='serverIDs']//option["+optionValue+"] | (//select[@name='serverIDs']//option)["+optionValue+"]")).isSelected())
	    {  driver.findElement(By.xpath("//select [@id='serverIDs']//option["+optionValue+"] | (//select[@name='serverIDs']//option)["+optionValue+"]")).click();  }
	 } 
	
	//=================serverIDs
	
	@FindBy(xpath="//select[@id='networkList']/option[@value=22]") private static WebElement yellowJacket;
	public WebElement networkYellowJacket()
	{return yellowJacket;}
	
	@FindBy(xpath="//select[@id='networkList']/option[@value=22]") private static WebElement empView;
	public WebElement typeEmployeeView()
	{return empView;}
	
	@FindBy(xpath="//input[@id='startProcessing']  |  (//input[@name='continuousProcessingFlag'])[1]") 
	private static WebElement startImportingAt;
		public WebElement startImportingAtRadioBtn()
		{return startImportingAt;}
		
	@FindBy(xpath="//input[@id='timeToProcessInters' or @name='timeToProcessInters']") private static WebElement startTm;
	public WebElement startTime()	{return startTm;}
	
	@FindBy(xpath="//option[@value='IST'] | //input[@id='timeToProcessInters']/..//option[@value='IST']") private static WebElement startTmZn;
	public WebElement startTimeZoneIST()	{return startTmZn;}
	
	@FindBy(xpath="//input[@id='btnApply'] | //input[@value='OK']")private static WebElement okk;
	public WebElement okBtn(){return okk;}
	
}
