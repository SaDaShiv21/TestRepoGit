package com.actiance.generics;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Client.CupsUsers;
import com.actiance.generics.*;
//import com.smarsh.slack.utils.CSVObjectListsRead;


public class CupsTestBase {
/*	static Properties	props;
	static Properties prereqdata;
	//====================================
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
		
	public static CupsUsers users[];
	public static String usersbuddyname[];
	public static String userspwd[];
	public static String pbsbip;
	public static int numberofrooms; 
	public static String nwayrooms[];
	public static String pchatrooms[];
	//====================================

	@Parameters({ "configPropertiesFile","browser","vantageIP","vantageUN","vantagePwd","cupstestdata","dbHost"
					,"dbName","dbUN","dbPwd" })
	@BeforeSuite
	public static void beforeSuite(String dbHost, String dbName, String configPropertiesFile,String vantageIP, 
			String browser, String vantageUN, String vantagePwd, String cupstestdata,String dbUN,String dbPwd )
				throws InterruptedException, IOException {
			
				//System.setProperty("key", "value");
				System.setProperty("dbHost", dbHost);
				//System.getProperty("Key");
				System.getProperty("dbHost");
					
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
					
					props = new Properties();
					getProperties(props,prereqdata.getProperty("configPropertiesFile"));
				
				
				DatabaseUtils dbu = new DatabaseUtils();
				Statement stmt = dbu.createStatement("MSSQL","192.168.125.249","MSA_2017R2_Q3","sa","FaceTime@123");
	}
	@Test(dataProvider = "map", invocationCount = 1)
	//public void executeTest(CSVObjectListsRead testObject) {
	public void executeTest(CSVdata testObject) {
		
		System.out.println("Test:"+testObject.getTestCaseNo()+" "+testObject.getFromUser()+" "+testObject.getImOrGc()+" "+testObject.getToUserOrRoom()+" "+
				testObject.getMessageText()+" "+testObject.getExecuteYesOrNO());
		//System.out.println(testObject.getToken());
		
	}
	
	@DataProvider(name = "map")
	public Object[][] excelDetails() {
		System.out.println("DataPRovider>>exceldetailsmethod:");
		ArrayList<Object> objAdd = new ArrayList<Object>();
		CsvUtilsLib getValues = new CsvUtilsLib();//reads from csv - creates csvdata objs
		CSVdata obj = null;

		Object[][] object = null;
		// HashMap<String, CSVObjectListsRead> objects = new HashMap();
		List<CSVdata> objects = new ArrayList<CSVdata>();
		try { 
			objects = getValues.getCsvObjects(".\\configFiles\\cups.csv");
		} catch (IOException er) {  er.printStackTrace();	}

		for (CSVdata csvObjectListsRead : objects)
		{	objAdd.add(csvObjectListsRead); 	}
		System.out.println(objAdd);
		object = new Object[objAdd.size()][1];

		for (int i = 0; i < objects.size(); i++)
		{	//System.out.println(objAdd.get(i));
			object[i][0] = objAdd.get(i);
		}
		
		return object;
	}
	
	public static void getProperties(Properties prop,String filepath)
	 {
		//Properties prop = new Properties();
   	try {
         //load a properties file
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
   		System.out.println("DomainName:"+domainname+"    Publisher/Subscriber:"+pbsbip);
   		for (int i=0;i<numberofusers;i++) {
   		String userprefix = "user"+(i+1);
   		String usrpwd = "passwordofuser"+(i+1);
   		usersbuddyname[i]=prop.getProperty(userprefix);
			userspwd[i]=prop.getProperty(usrpwd);
			System.out.println("usersBuddyname["+i+"]="+usersbuddyname[i]+" <<= Props-"+userprefix+":"+prop.getProperty(userprefix) +" "+usrpwd+":"+userspwd[i]);
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

   	} catch (IOException ex) {	ex.printStackTrace();  }
  }
	
*/	
}
