package com.actiance.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewerILPPolicies {
	
	//input[@id='Add'] | //input[@value='Add'] | //input[@name='Add']
	@FindBy(xpath="//input[@id='Add'] | //input[@value='Add'] | //input[@name='Add']")
	private static WebElement addPolicyBtn;
	public WebElement addPolicyBtn()
	{	return addPolicyBtn; 	}
	
	//===============================================
	@FindBy(xpath="//input[@id='name'] | //input[@name='name']")
	private static WebElement policyName;
	public WebElement policyName()
	{	return policyName; 	}
	
	@FindBy(xpath="//textarea[@id='policyDescription'] | //textarea[@name='policyDescription']")
	private static WebElement policyDescription;
	public WebElement policyDescription()
	{	return policyDescription; 	}
	
	@FindBy(xpath="//input[@id='enabledFlag1'] | //input[@name='enabledFlag']")
	private static WebElement enablepolicy;
	public WebElement enablepolicy()
	{	return enablepolicy; 	}
	
	//a/img[@alt='Add Rules to List']  | //select[@id='dlpRulesSelected']/../a[1]
	@FindBy(xpath="//a/img[@alt='Add Rules to List']  | //select[@id='dlpRulesSelected']/../a[1]")
	private static WebElement addrules;
	public WebElement addrules()
	{	return addrules; 	}
	
	//============selectRule==========
	//td[contains(text(),'testrule')]/..//td/input
	/* public static String selector;
	 public void setSelector(String rulename) {
		 selector = rulename;
	 }
	@FindBy(xpath=selector)
	private static WebElement selectRules;
	public WebElement selectRules()
	{	return selectRules; 	}*/
	
	//==========actions====================
	@FindBy(xpath="//input[@id='donotBlockInIMFlag'] | //input[@name='blockInIMFlag'][1]")
	private static WebElement doNotBlockIncoming;
	public WebElement doNotBlockIncoming()
	{	return doNotBlockIncoming; 	}
	
	@FindBy(xpath="//input[@id='blockInIMFlag'] | //input[@name='blockInIMFlag'][2]")
	private static WebElement blockIncoming;
	public WebElement blockIncoming()
	{	return blockIncoming; 	}
	
	@FindBy(xpath="//input[@id='challengeInIMFlag'] | //input[@name='blockInIMFlag'][3]")
	private static WebElement challengeIncoming;
	public WebElement challengeIncoming()
	{	return challengeIncoming; 	}
	
	@FindBy(xpath="//input[@id='donotBlockOutIMFlag'] | //input[@name='blockOutIMFlag'][1]")
	private static WebElement doNotBlockOutgoing;
	public WebElement doNotBlockOutgoing()
	{	return doNotBlockOutgoing; 	}
	
	@FindBy(xpath="//input[@id='blockOutIMFlag'] | //input[@name='blockOutIMFlag'][2]")
	private static WebElement blockOutgoing;
	public WebElement blockOutgoing()
	{	return blockOutgoing; 	}
	
	@FindBy(xpath="//input[@id='challengeOutIMFlag'] | //input[@name='blockOutIMFlag'][3]")
	private static WebElement challengeOutgoing;
	public WebElement challengeOutgoing()
	{	return challengeOutgoing; 	}
	
	//save
	@FindBy(xpath="//input[@value='Save'] | //td[@id='footerLeft']/input[1]")
	private static WebElement savebtn;
	public WebElement savebtn()
	{	return savebtn; }	
	

}
