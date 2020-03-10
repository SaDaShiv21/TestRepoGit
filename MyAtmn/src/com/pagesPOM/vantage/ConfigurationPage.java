package com.pagesPOM.vantage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfigurationPage 
{
	@FindBy(xpath="//td[3]/div[1]//li[3]/a")
	private static WebElement InteractionExporter;
	public WebElement InteractionExporter()
	{	
		return InteractionExporter;	
	}
	
	@FindBy(xpath="//td[1]/div[1]//li[10]/a | //a[contains(text(),'Import Settings')]")
	private static WebElement importSetting;
	public WebElement importSettings()
	{	return importSetting;	}

}
