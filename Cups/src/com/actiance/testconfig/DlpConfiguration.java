package com.actiance.testconfig;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.actiance.pom.HomePageSysadmin;
import com.actiance.pom.Login;
import com.actiance.pom.LoginPage;
import com.actiance.pom.ReviewerConfigPage;
import com.actiance.pom.ReviewerILPPolicies;
import com.actiance.pom.ReviewerILPRules;

public class DlpConfiguration {
/*	public static void main(String[] args) {
		WebDriver driver = null;
		Login lgn = new Login();
		driver= lgn.openBrowser("chrome", "192.168.125.234");
		driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
		
		LoginPage loginpg=PageFactory.initElements(driver, LoginPage.class);
		loginpg.usernmTB().sendKeys("reviewer");
		loginpg.pwdTB().sendKeys("facetime");
		loginpg.loginBTN().click();
		//HomePage homepage=PageFactory.initElements(driver, HomePage.class);
		String[] dlpPhrases = {"inComingBlock,BlockIN","OutGoingBlock,BlockOut","ChallengeInComing,ChallengeIn",
		"DoNotBlkIncoming,DoNotBlockIn","DoNotBlkOutGoing,DoNotBlockOut","OutGoingChlng,ChallengeOut","IOBLK,BlockInOut"};
		createAllRulesAndPolicies(driver,dlpPhrases);
	}   */
	
	
	public static void createAllRulesAndPolicies(WebDriver driver, String[] dlpPhrases) {
		System.out.println("Create rules and policies");
		ReviewerConfigPage rcp = PageFactory.initElements(driver,ReviewerConfigPage.class);
		//rule creation
		ReviewerILPRules rulespage = PageFactory.initElements(driver,ReviewerILPRules.class);
		createRules(driver,rcp,rulespage,dlpPhrases);	
		//policies---actions
		ReviewerILPPolicies policiespage = PageFactory.initElements(driver,ReviewerILPPolicies.class);
		createPolicy(driver,rcp,policiespage,dlpPhrases);
	}
	
	public static void selectRule(WebDriver driver,ReviewerILPPolicies rp,String ruleName) {
		String parent = driver.getWindowHandle();//System.out.println("Parent:"+parent);
		rp.addrules().click();
		Set<String> winds=driver.getWindowHandles();
		Iterator it = winds.iterator();
		while(it.hasNext()) {
			String win =(String) it.next();
			if ( !parent.equalsIgnoreCase(win))
			{/*System.out.println("Child:"+win);*/
			driver.switchTo().window(win);}
		}
		String rulexpath = "//td[contains(text(),'"+ruleName+"')]/..//input";
		driver.findElement(By.xpath(rulexpath)).click();
		driver.findElement(By.xpath("//input[@id='submitBtn'] | //input[@value=' Add Now '] |//input[@onclick='assaignValues()']")).click();
		driver.switchTo().window(parent);		
	}
	
	public static void createPolicy(WebDriver driver,ReviewerConfigPage rcp,ReviewerILPPolicies policiespage,String[] dlpPhrases){
		boolean found=false;
		rcp.configurationTab().click();
		rcp.ilpPolicies().click();
		List<WebElement> policynames = driver.findElements(By.xpath("//div[@class='contentSectionNoPad']//a"));
		String[] policiescreated = new String[ policynames.size() ];
		System.out.print("policies created:");
		for (int i = 0; i < policynames.size(); i++) {
		String policynm = policynames.get(i).getText();
		System.out.print(policynm+"\t");
		policiescreated[i] = policynm;
		}
		
		for(int i=0;i<dlpPhrases.length;i++) {
			found = false;
			String[] dlp = dlpPhrases[i].split(",");
			
			for(int j=0;j< policiescreated.length;j++) {
				String plnm = dlp[0]+"Policy";
				String rl = dlp[0];
				//System.out.println("DLP rule to be created:"+rlnm+" >> created rule:"+rulescreated[j]);
				if( plnm.equalsIgnoreCase(policiescreated[j])  ){
					System.out.println("DLP policy is already created:"+policiescreated[j]);
					found=true;
					break;
				}
			}
		if(found == false) {
			rcp.configurationTab().click();
			rcp.ilpPolicies().click();
			policiespage.addPolicyBtn().click();
			policiespage.policyName().sendKeys(dlp[0]+"Policy");//policy name
			policiespage.policyDescription().sendKeys(dlp[0]+"Policy PolicyDescription");//policy discription
		//enabling it
			if( !(policiespage.enablepolicy().isSelected()) ){policiespage.enablepolicy().click();}
			selectRule(driver,policiespage,dlp[0]+"RuleName");// select  rule(phrase) for this policy
		//selecting Actions
			if(dlp[1].equalsIgnoreCase("BlockIN")) {policiespage.blockIncoming().click();}
			else if(dlp[1].equalsIgnoreCase("BlockOut")) {policiespage.blockOutgoing().click();}
			else if(dlp[1].equalsIgnoreCase("BlockInOut")) {policiespage.blockIncoming().click();policiespage.blockOutgoing().click();}
			//else if(dlp[1].equalsIgnoreCase("DoNotBlockIn")) {policiespage.doNotBlockIncoming().click();}
			//else if(dlp[1].equalsIgnoreCase("DoNotBlockOut")) {policiespage.doNotBlockOutgoing().click();}
			else if(dlp[1].equalsIgnoreCase("DoNotBlockInOut")) 
			{policiespage.doNotBlockIncoming().click();policiespage.doNotBlockOutgoing().click();}
			else if(dlp[1].equalsIgnoreCase("ChallengeIn")) {policiespage.challengeIncoming().click();}
			else if(dlp[1].equalsIgnoreCase("ChallengeOut")) {policiespage.challengeOutgoing().click();}
			else {System.out.println("Invalid DLP action provided Action:"+dlp[1]);
			//System.out.println("Valid Ations are:BlockIN,BlockOut,BlockInOut,DoNotBlockInOut,DoNotBlockIn,DoNotBlockOut,ChallengeIn,ChallengeOut");}
			  System.out.println("Valid Ations are:BlockIN,BlockOut,BlockInOut,DoNotBlockInOut,ChallengeIn,ChallengeOut");}
		//click save btn
			policiespage.savebtn().click();
		}
		}		
	}
	
	public static void createRules(WebDriver driver,ReviewerConfigPage rcp,ReviewerILPRules rulespage,String[] dlpPhrases) {
		System.out.println("Create rules");
		String[] dlpRulesCreated = new String[ dlpPhrases.length ]; 
		
		rcp.configurationTab().click();
		rcp.ilpRules().click();
		//rules  //div[@class='contentSectionNoPad']//td[3]  >> rulePhrases
		List<WebElement> rules = driver.findElements(By.xpath("//div[@class='contentSectionNoPad']//td[3]"));
		String[] rulescrtd = new String[ rules.size() ];
		System.out.print("Rules:");
		for(int r=0;r<rules.size();r++) {
			String rule = rules.get(r).getText();
			System.out.print(rule+"\t");
			rulescrtd[r]=rule;
		}
		//rulenames  //a[contains(text(),'inComingBlockRuleName')]  >>ruleNames
		List<WebElement> rulenames = driver.findElements(By.xpath("//div[@class='contentSectionNoPad']//a"));
		String[] rulescreated = new String[ rulenames.size() ];
		System.out.print("Rules created:");
	    for (int i = 0; i < rulenames.size(); i++) {
		String rulenm = rulenames.get(i).getText();
		System.out.print(rulenm+"\t");
		rulescreated[i] = rulenm;
	    }
	    System.out.println();
	    boolean found=false;
	    
		for(int i=0;i<dlpPhrases.length;i++) {
			found=false;
			String[] dlp = dlpPhrases[i].split(",");
			for(int j=0;j< rulescreated.length;j++) {
				String rlnm = dlp[0]+"RuleName";
				String rl = dlp[0];
				//System.out.println("DLP rule to be created:"+rlnm+" >> created rule:"+rulescreated[j]);
				if( (rlnm.equalsIgnoreCase(rulescreated[j])) | (rl.equalsIgnoreCase(rulescrtd[j])) ){
					System.out.println("DLP rule is already created:"+rulescreated[j]+" Phrase:"+rulescrtd[j]);
					found=true;
					break;
				}
			}
			
			if(found == false){
			rcp.configurationTab().click();
			rcp.ilpRules().click();
			rulespage.addbtn().click();
			rulespage.rulename().sendKeys(dlp[0]+"RuleName");//rulename
			dlpRulesCreated[i]=dlp[0]+"RuleName";
			rulespage.dlpphrase().sendKeys(dlp[0]);//DLP phrase
			rulespage.saveBtn().click();
			}
		}
	}
	
}