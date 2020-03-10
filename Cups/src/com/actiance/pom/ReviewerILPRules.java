package com.actiance.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewerILPRules {
	
	@FindBy(xpath="//input[@value = 'Add']")
	private static WebElement addbtn;
	public WebElement addbtn()
	{	return addbtn; 	}
	
	@FindBy(xpath="//input[@id='name'] | //input[@name='name']")
	private static WebElement rulename;
	public WebElement rulename()
	{	return rulename; 	}
	
	@FindBy(xpath="//input[@id='ruleValueId']  | //input[@name='ruleValue']")
	private static WebElement dlpphrase;
	public WebElement dlpphrase()
	{	return dlpphrase; 	}
	
	@FindBy(xpath="//input[@value='Save'] | //td[@id='footerLeft']/input[1]")
	private static WebElement saveBtn;
	public WebElement saveBtn()
	{	return saveBtn; 	}

}
