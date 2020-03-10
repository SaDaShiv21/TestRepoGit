package com.actiance.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.actiance.generics.*;
import com.actiance.pom.Login;
import com.actiance.pom.LoginPage;
import com.actiance.testconfig.DlpConfiguration;

public class CupsTestBase {
	
	public static Properties props;
	public static Properties prereqdata;
	public static DatabaseUtils dbutils;
	public static Statement stmt;
	public static Connection con;
	static ArrayList<String> testdata;
	public static String suitecurrenttime;
	public static String[] dlpPhrases = {"MarketValue,BlockIn","sharePrice,BlockOut","IOBLK,BlockInOut",
			"YamahaDontBlck,DoNotBlockInOut","ToyotaInCh,ChallengeIn","HyundaiChlngOut,ChallengeOut",};
	//====================================
	public static CupsUsers users[];
	public static int numberofusers;
	public static int messagerate;
	public static int loadrunduration;
	public static String buddynameprefix;
	public static String resourcename;
	public static int buddynamestartindex;
	public static String server;
	public static String password;
	public static String domainname;
	public static String msgtext;
	public static long msgsPerSecPerUser;
	public static long messageDelayPerUser;
	public static long duration;
	public static String usersbuddyname[];
	public static String userspwd[];
	public static String userpbsb[];
	public static String pbsbip;
	public static int numberofrooms; 
	public static String nwayrooms[];
	public static String pchatrooms[];
	//====================================

@Parameters({"dbHost","dbName","configPropertiesFile","vantageIP","browser","vantageUN","vantagePwd","cupstestdata","dbUN","dbPwd","dbType","ReviewerUN","ReviewerPwd" })
	@BeforeSuite
	public static void beforeSuite(String dbHost, String dbName, String configPropertiesFile,String vantageIP, 
			String browser, String vantageUN, String vantagePwd, String cupstestdata,String dbUN,String dbPwd,String dbType, String reviewer,String reviewerpwd )
				throws InterruptedException, IOException {
		System.out.println("Before suite");
		prereqdata = new Properties();
		prereqdata.setProperty("dbHost", dbHost);
		prereqdata.setProperty("dbName", dbName);
		prereqdata.setProperty("configPropertiesFile", configPropertiesFile);
		prereqdata.setProperty("browser", browser);
		prereqdata.setProperty("vantageIP", vantageIP);
		prereqdata.setProperty("vantageUN", vantageUN);
		prereqdata.setProperty("vantagePwd", vantagePwd);
		prereqdata.setProperty("cupstestdata", cupstestdata);
		prereqdata.setProperty("dbUN", dbUN);
		prereqdata.setProperty("dbPwd", dbPwd);
		prereqdata.setProperty("dbType", dbType);
		prereqdata.setProperty("reviewer", reviewer);
		prereqdata.setProperty("reviewerpwd", reviewerpwd);
				
		props = new Properties();
		getProperties(props,prereqdata.getProperty("configPropertiesFile"));
		createusers();
		
		// create DLP rules and policies and close browser session
		//System.out.println(prereqdata.getProperty("browser")+" "+prereqdata.getProperty("vantageIP")+" "+prereqdata.getProperty("reviewer")+" "+prereqdata.getProperty("reviewerpwd"));
		openBrowserAndCreateDLPs(prereqdata.getProperty("browser"),prereqdata.getProperty("vantageIP"),prereqdata.getProperty("reviewer"),prereqdata.getProperty("reviewerpwd"),dlpPhrases);
		
		dbutils = new DatabaseUtils();
		con = dbutils.createConnection(prereqdata.getProperty("dbType"),prereqdata.getProperty("dbHost"),prereqdata.getProperty("dbName"),prereqdata.getProperty("dbUN"),prereqdata.getProperty("dbPwd"));
		if(con != null) {try {stmt = con.createStatement();} catch (SQLException e) { e.printStackTrace(); }}
		suitecurrenttime=getCurrentTimeStamp();	
	}
	
	public static WebDriver openBrowserAndLogin(String browser,String vtgIP,String vtgLoginUN,String vtgLoginPwd) {
		WebDriver driver = null;
		Login lgn = new Login();
		driver= lgn.openBrowser(browser,vtgIP);/*driver= lgn.openBrowser("chrome", "192.168.125.234");*/
		driver.manage().timeouts().implicitlyWait(180,TimeUnit.SECONDS);
		LoginPage loginpg=PageFactory.initElements(driver,LoginPage.class);
		loginpg.usernmTB().sendKeys(vtgLoginUN);/*loginpg.usernmTB().sendKeys("reviewer");//reviewer/sysadmin*/
		loginpg.pwdTB().sendKeys(vtgLoginPwd);/*loginpg.pwdTB().sendKeys("facetime");*/
		loginpg.loginBTN().click();
		return driver;
	}
	
	public static void openBrowserAndCreateGroupsAndEmployees(String browser,String vtgIP,String vtgLoginUN,String vtgLoginPwd) {
		WebDriver driver = openBrowserAndLogin(browser,vtgIP,vtgLoginUN,vtgLoginPwd);
	}
	
	public static void openBrowserAndCreateDLPs(String browser,String vtgIP,String vtgLoginUN,String vtgLoginPwd,String[] dlpPhrases) {
		System.out.println(dlpPhrases);
		WebDriver driver = openBrowserAndLogin(browser,vtgIP,vtgLoginUN,vtgLoginPwd);
		DlpConfiguration dlpconfig = new DlpConfiguration();
		dlpconfig.createAllRulesAndPolicies(driver,dlpPhrases);
		System.out.println("creation of Dlp rules and policies are successful, Hence closing "+browser+" browser");
		driver.close();
	}
	
	@DataProvider(name = "map")
	public Object[][] testData() {
		System.out.println("DataProvider:");
		ArrayList<Object> objAdd = new ArrayList<Object>();
		CsvUtilsLib getValues = new CsvUtilsLib();
		CSVdata obj = null;

		Object[][] object = null;
		// HashMap<String, CSVObjectListsRead> objects = new HashMap();
		List<CSVdata> objects = new ArrayList<CSVdata>();
		try { 
			objects = getValues.getCsvObjects("./configFiles/CupsTestData.xlsx", 9);
		} catch (Exception er) {  er.printStackTrace();	}

		for (CSVdata csvObjectListsRead : objects)
		{	objAdd.add(csvObjectListsRead); 	}
		System.out.println("TestCasesToExecute:"+objAdd);
		object = new Object[objAdd.size()][1];
		
		for (int i = 0; i < objects.size(); i++)
		{	//System.out.println(objAdd.get(i));
			object[i][0] = objAdd.get(i);
		}
		return object;
	}
	
	public static void getProperties(Properties prop,String filepath)
	 {	//System.out.println("PropsFile");
		//Properties prop = new Properties();
   	try {//load a properties file
   		//prop.load(new FileInputStream("./configFiles/config.properties"));
   		prop.load(new FileInputStream(filepath));
   		
   		numberofusers = new Integer(prop.getProperty("Number_of_users"));
   		messagerate = new Integer(prop.getProperty("messagerate"));
   		loadrunduration =new Integer(prop.getProperty("loadrunduration"));
   		buddynameprefix = prop.getProperty("buddynameprefix");
   		resourcename = prop.getProperty("resourcename");
   		buddynamestartindex = new Integer (prop.getProperty("buddyNameIndex"));
   		server = prop.getProperty("server");
   		password = prop.getProperty("password");
   		domainname = prop.getProperty("domainname");
   		msgtext = prop.getProperty("msgtext");
   		pbsbip = prop.getProperty("pbSbIP");
   		
   		msgsPerSecPerUser=(messagerate/numberofusers);
   		messageDelayPerUser=1000/msgsPerSecPerUser;
   		duration=loadrunduration*60*1000;
   		//user details
   		usersbuddyname = new String[numberofusers];
   		userspwd = new String[numberofusers];
   		userpbsb = new String[numberofusers];
   		System.out.println("DomainName:"+domainname+"    Publisher/Subscriber:"+pbsbip);
   		for (int i=0;i<numberofusers;i++) {
   		String userprefix = "user"+(i+1);
   		String usrpwd = "passwordofuser"+(i+1);
   		String usrpbsb = "pbsbofuser"+(i+1);
   		usersbuddyname[i]=prop.getProperty(userprefix);
			userspwd[i]=prop.getProperty(usrpwd);
			userpbsb[i]=prop.getProperty(usrpbsb);
			System.out.println("usersBuddyname["+i+"]="+usersbuddyname[i]+" <<= Props-"+userprefix+":"+prop.getProperty(userprefix) +" "+usrpwd+":"+userspwd[i]+" "+prop.getProperty(usrpbsb));
			}
   		//room details
   		numberofrooms= new Integer(prop.getProperty("numberOfnwayPchatrooms"));
   		nwayrooms =new String[numberofrooms];
   		pchatrooms = new String[numberofrooms];
   		for(int j=0;j<numberofrooms;j++) {
   			String nwayz = "nwayroomID"+(j+1);
       		String pchatz = "pchatroomID"+(j+1);
       		nwayrooms[j]= prop.getProperty(nwayz);
       		pchatrooms[j]= prop.getProperty(pchatz);
       		System.out.println("Nway["+j+"] :"+nwayrooms[j]);
       		System.out.println("Pchat["+j+"] :"+pchatrooms[j]);
   		}
   		System.out.println("==================== x ====================");

   	} catch (IOException ex) {	ex.printStackTrace();  }
  }
	
	public static void createusers()
    {	
		System.out.println("UsrObjCreation");
		users = new CupsUsers[numberofusers];
    	for(int i=0;i<numberofusers;i++)
    	{ String buddyname = usersbuddyname[i];
    		String userPwd = userspwd[i];
    		String usrpbsb = userpbsb[i];
    		String fullname = buddyname.concat("@").concat(domainname);
    	    users [i]= new CupsUsers(buddyname,fullname, userPwd,usrpbsb,messageDelayPerUser);
    	  //users[i]=new CupsUsers(buddyname, fullname, messageDelayPerUser); //need modifications
    	  System.out.println("user:"+i+" BuddyName:"+users[i].getBuddyname()+" Fullname:"+users[i].getUserfullname()+" UserPwd:"+users[i].getUserpwd()+" UserPbSb:"+users[i].getUserpbsb());
    	}
    }
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	@AfterSuite
	public static void afterSuite() {
		if(con != null) {	try {con.close();} catch (SQLException e) {e.printStackTrace();}}
		//System.exit(0);		8
	}
}