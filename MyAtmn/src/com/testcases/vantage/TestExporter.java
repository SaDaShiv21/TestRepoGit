package com.testcases.vantage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import com.pagesPOM.vantage.ConfigurationPage;
import com.pagesPOM.vantage.HomePage;
import com.pagesPOM.vantage.InteractionExporterPage;
import com.pagesPOM.vantage.LoginPage;
import generics.BrowserLauncher;
import generics.Logging;
import generics.PropsReader;
import generics.TakeScreenshot;
import net.bytebuddy.description.modifier.SynchronizationState;

public class TestExporter 
{
	
	public static void main(String[] args) throws Exception 
	{
//		BrowserLauncher br =new BrowserLauncher();
//		WebDriver driver = br.chrome();
		
		final Logger log = Logger.getLogger(Logging.class);
		WebDriver driver;
		//BrowserLauncher br =new BrowserLauncher();
		//WebDriver driver = br.chrome();//launches chrome browser and login to vantage with help of property file
		//-------------------------
		System.setProperty("webdriver.chrome.driver", "./driver/ChromeDriver.exe");
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
		//driver = new ChromeDriver(capabilities);
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capability);
		
		driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);// before finding all elements if its not
		
		//WebDriver driver = new ChromeDriver();
		PropsReader p= new PropsReader();//For getting values from properties file
		
		log.debug("Login details:"+" "+p.reader("link")+" User:"+p.reader("user")+" Password:"+p.reader("password"));
		driver.get("https://"+p.reader("link")+":8443/ima");
		LoginPage lp=PageFactory.initElements(driver, LoginPage.class);
		//System.out.println("values:"+p.reader("user")+" "+p.reader("password"));
		lp.usernmTB(p.reader("user"));
		lp.pwdTB(p.reader("password"));
		lp.loginBTN().click();
		
		HomePage hp=PageFactory.initElements(driver, HomePage.class);
		hp.Configuration().click();
		ConfigurationPage cp=PageFactory.initElements(driver, ConfigurationPage.class);
		cp.InteractionExporter().click();
		
		int multiServerId=0;
		
		int timer=0,tmr=3;//timer is the time for initial tmr set from current time
		InteractionExporterPage iep=PageFactory.initElements(driver, InteractionExporterPage.class);
		//for loop
//Repetn		//int i=0;//--looping
	for (int i=0; i<30;i++)
	{
		//iep.createNewBTN().click(); //create new exporter every time
		//Exporter Number
		int ExporterNo=i;
		
		String xprtrNo="//select[@id='exporterNum' or name='exporterNum']/option[@value="+ExporterNo+"]";
		driver.findElement(By.xpath(xprtrNo)).click();
		//Exporter Number
		iep.goBTN().click();
		iep.setup().click();
		if( !iep.exporterEnableCheckBox().isSelected()) 
		//if(! iep.exporterEnableCheckBox().isSelected())
		{//check and select Enable check box
			iep.exporterEnableCheckBox().click();
		}
		if(! iep.logAllActivity().isSelected())
		{
			iep.logAllActivity().click();
		}
		//serverID
		int serverId = Integer.parseInt(p.reader("exporterServerID"));
		log.debug("importer: "+ExporterNo+"  is running with serverID:"+serverId);
		if (serverId==1) 
		{       System.out.println("executing exporter with single serverId");
		        iep.serverIdSelect(driver, 1);
		}
		else
		{
			if (multiServerId==0) 
			{
				iep.serverIdSelect(driver, 1);
				multiServerId=1;
			}
			else 
			    {   iep.serverIdSelect(driver, 2);
				    multiServerId=0;	
			    }
		}
				
		// only if u have to set filter  
		/*iep.exporterFilters().click();
		if(!iep.processInteractionFromCheckB().isSelected())
		{   iep.processInteractionFromCheckB().click();	}
		
		iep.processFromDate().clear();
		iep.processFromDate().sendKeys(p.smtpProps("fromDate"));//m-d-y
		iep.processToDate().clear();
		iep.processToDate().sendKeys(p.smtpProps("toDate"));*/
		
	//set time for each exporter
		iep.frequency().click();
		iep.StartProcessingAtRBtn().click();	
	//time
	    DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mma");
        String s1 = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        LocalTime lt = LocalTime.parse(s1);
        log.debug("current timeis =>"+s1);
     //System.out.println("current timeis =>"+s1);
        timer=tmr;
        tmr=timer+3;//time gap between the importers
        log.debug("adding  "+tmr+"  minutes to current time and setting the Exporter");
        String setTime = df.format(lt.plusMinutes(tmr));// current time plus 3 min
        log.debug(">----Importer:"+ExporterNo+" is set at ---->-->---"+setTime);
     //System.out.println(">----Importer:"+i+" is set at ---->-->---"+xt);
        log.debug("            ======x========x======    ");
	  //time
   
		iep.exporterStartTime().clear();
		iep.exporterStartTime().sendKeys(setTime);//check- exporter is set after current time + 3 min
		iep.exporterStartTimezoneIST().click();
		iep.ExportType().click();
		//--props reader data
		//PropsReader ps= new PropsReader();
		//lp.usernmTB(p.reader("user"));
		System.out.println("smtp sender "+p.smtpProps("sender"));
		iep.smtpSender().clear();
		iep.smtpSender().sendKeys(p.smtpProps("sender"));//smtp sender autodata>>smtpData
		System.out.println("smtp reciever "+PropsReader.smtpProps("recipient"));
		iep.smtpRecipients().clear();
		iep.smtpRecipients().sendKeys(p.smtpProps("recipient"));
		iep.smtpServer().clear();
		iep.smtpServer().sendKeys(p.smtpProps("server"));
		//---------
		
		iep.formatSettings().click();
		iep.formatSettingsTimeZoneIST().click();
		iep.outPutFormat().click();
		iep.outPutFormatInteractionAttchmnt().click();
		
		//click ok 
  //iep.okBtn().click();
		//------ for screen-shots
		/*driver.findElement(By.xpath(xprtrNo)).click();
		iep.goBTN().click();
		iep.setup().click();
		iep.frequency().click();
		TakeScreenshot.browserScreenshot(driver);*/
		//driver.close();
		System.out.println("End TestExporter.java");
		//--for loop end
		}
	}
}