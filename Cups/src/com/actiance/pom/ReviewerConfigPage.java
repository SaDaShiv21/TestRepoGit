package com.actiance.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewerConfigPage {
	@FindBy(xpath="//a[contains(text(),'Configuration')]")
	private static WebElement configuration;
	public WebElement configurationTab()
	{	return configuration; 	}
	
	@FindBy(xpath="//a[contains(text(),'ILP Rules')]")
	private static WebElement ilpRules;
	public WebElement ilpRules()
	{	return ilpRules; 	}
	
	@FindBy(xpath="//a[contains(text(),'ILP Policies')]")
	private static WebElement ilpPolicies;
	public WebElement ilpPolicies()
	{	return ilpPolicies; 	}

}
