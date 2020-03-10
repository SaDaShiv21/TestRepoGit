package com.actiance.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewerHomePage {
	
	@FindBy(xpath="//a[contains(text(),'Dashboard')]")
	private static WebElement dashboard;
	public WebElement dashboardTab()
	{	return dashboard; 	}
	
	@FindBy(xpath="//a[contains(text(),'Interactions')]")
	private static WebElement interactions;
	public WebElement interactionsTab()
	{	return interactions; 	}
	
	@FindBy(xpath="//a[contains(text(),'ILP Incidents ')]")
	private static WebElement ilpIncidents;
	public WebElement ilpIncidentsTab()
	{	return ilpIncidents; 	}
	
	@FindBy(xpath="//a[contains(text(),'Configuration')]")
	private static WebElement configuration;
	public WebElement configurationTab()
	{	return configuration; 	}

}
