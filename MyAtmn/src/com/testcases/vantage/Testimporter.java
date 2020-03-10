package com.testcases.vantage;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.pagesPOM.vantage.ImporterSettingsPage;
import com.pagesPOM.vantage.InteractionExporterPage;
import com.pagesPOM.vantage.LoginPage;

import generics.BrowserLauncher;
import generics.Logging;
import generics.PropsReader;

public class Testimporter 
{
	private static final Logger log = Logger.getLogger(Logging.class);
	//logs every thing in logs/automation.log and details in log-props/log4j.props
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception 
	{
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
		//--------------------------
		HomePage hp=PageFactory.initElements(driver, HomePage.class);
		hp.Configuration().click();
		ConfigurationPage cp=PageFactory.initElements(driver, ConfigurationPage.class);
		cp.importSettings().click();//sllep here
		ImporterSettingsPage isp=PageFactory.initElements(driver, ImporterSettingsPage.class);
		//preREq
		ArrayList<String> locatn = new ArrayList<>();//folder location
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp1");//location1-
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp2");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp3");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp4");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp5");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp6");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp7");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp8");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp9");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp10");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp11");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp12");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp13");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp14");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp15");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp16");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp17");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp18");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp19");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp20");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp21");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp22");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp23");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp24");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp25");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp26");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp27");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp28");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp29");
	     locatn.add("C:\\NewSadaShivImporter\\YellowJacket\\imp30");//locations
		//-------------*******looping for repeation*********--------------
	    // DateTimeFormatter df1 = DateTimeFormatter.ofPattern("hh:mma");
	        String s11 = new SimpleDateFormat("hh:mma").format(Calendar.getInstance().getTime());
		   int timer=0,tmr=3;//timer is the time for initial tmr set from current time
		   System.out.println("First importer time set from current time is after "+tmr+" minutes"+"  current time is:"+s11);
		   log.debug("First importer time set from current time is after "+tmr+" minutes"+"  current time is:"+s11);
		    //System.out.println("First importer time set from current time is after "+tmr);
		   log.debug("Total no of importers:"+locatn.size());
		    //System.out.println("Total no of importers:"+locatn.size());
		    int multiServerId=0;
		   int importerNo=0;//importer no starting
		for(int i=0;i<locatn.size();i++)
		{
			log.debug("iteration : "+(i+1));
			//isp.createNewBtn().click();
			Thread.sleep(1000);
			log.debug("Selecting importer "+importerNo+" xpath: "+"//select[@name='importerNum']/option[@value="+importerNo+"]");
			driver.findElement(By.xpath("//select[@name='importerNum']/option[@value="+importerNo+"]")).click();//select importer
			isp.goBtn().click();//sleep 2
			isp.ldapSearchLocationTB().clear();
			isp.ldapSearchLocationTB().sendKeys(p.reader("searchLoc"));
			isp.empEmailDomain().clear();
			isp.empEmailDomain().sendKeys(p.reader("DomainsYJ"));//sleep 900
			isp.setup().click();
			//----x--enable--x-----
			if(!isp.enableCheckBox().isSelected())
			{
				isp.enableCheckBox().click();
			}
			//file location
			isp.fileLocation().clear();
			
			String locn=locatn.get(i);
			log.debug("Location Set:"+locn);
			//System.out.println("Location Set:"+locn);
			isp.fileLocation().sendKeys(locn);
			//select server
			//(//select[@name='serverIDs']//option)[x]
			//isp.selectServerID().click();
			int serverId = Integer.parseInt(p.reader("importerServerID"));
			log.debug("importer: "+importerNo+"  is running with serverID:"+serverId);
						
			//serverID
			if (serverId==1) 
			{       System.out.println("executing exporter with single serverId");
			        isp.serverIdSelect(driver, 1);
			}
			else
			{
				if (multiServerId==0) 
				{	isp.serverIdSelect(driver, 1);
					multiServerId=1;  }
				else 
				    {   isp.serverIdSelect(driver, 2);
					    multiServerId=0;	
				    }
			}
			/*
			 *  if ( !driver.findElement(By.xpath("//select[@name='serverIDs']//option [@value=1]")).isSelected() )
			    { //check and select Enable check box
			    	driver.findElement(By.xpath("//select[@name='serverIDs']//option [@value=1]")).click();
			    }
			    */
			
			isp.networkYellowJacket().click();
			isp.typeEmployeeView().click();
			isp.startImportingAtRadioBtn().click();
			//time
		    DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mma");
	        String s1 = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
	        LocalTime lt = LocalTime.parse(s1);
	        log.debug("current timeis =>"+s1);
	        //System.out.println("current timeis =>"+s1);
	        timer=tmr;
	        tmr=timer+3;//time gap between the importers
	        log.debug("adding  "+tmr+"  minutes to current time and setting the importer");
	        String setTime = df.format(lt.plusMinutes(tmr));// current time plus 3 min
	        log.debug(">----Importer:"+importerNo+" is set at ---->-->---"+setTime);
	        //System.out.println(">----Importer:"+i+" is set at ---->-->---"+xt);
	        log.debug("    ======x========x======    ");
		    //time
	        isp.startTime().clear();
	        isp.startTime().sendKeys(setTime);//set time after 3 min
	        isp.startTimeZoneIST().click();
	        System.out.println("click ok bttn");
	        //isp.okBtn().click();
	        importerNo++;//to choose new importer
		}
	}
}
