package com.testcases.vantage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.pagesPOM.vantage.ConfigurationPage;
import com.pagesPOM.vantage.HomePage;
import com.pagesPOM.vantage.InteractionExporterPage;

import generics.BrowserLauncher;

public class DelExporters
{
	public static void main(String[] args) 
	{
		BrowserLauncher br =new BrowserLauncher();
		WebDriver driver = br.chrome();
		HomePage hp=PageFactory.initElements(driver, HomePage.class);
		hp.Configuration().click();
		ConfigurationPage cp=PageFactory.initElements(driver, ConfigurationPage.class);
		cp.InteractionExporter().click();
		InteractionExporterPage iep=PageFactory.initElements(driver, InteractionExporterPage.class);
		
		for (int x=4;x<5;x++)
		{
		int ExporterNo=x;
		String xprtrNo="//select[@id='exporterNum' or name='exporterNum']/option[@value="+ExporterNo+"]";
		driver.findElement(By.xpath(xprtrNo)).click();
		iep.delBTN().click();
		
		for (int i=0;i<3; i++) 
		{
			try 
			{
				Robot r=new Robot();
				r.keyPress(KeyEvent.VK_TAB);
				r.keyRelease(KeyEvent.VK_TAB);
			}catch(AWTException e){e.printStackTrace();}
			
		}
		
		}//for loop
		
	}

}
