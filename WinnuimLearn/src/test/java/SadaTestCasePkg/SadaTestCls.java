package SadaTestCasePkg;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import java.util.*;

import GenericUtils.DatabaseUtils;
import GenericUtils.TestBaseCups;

public class SadaTestCls extends TestBaseCups {

	public static void main(String[] args) throws MalformedURLException 
	{	
		System.out.println("Reading test data from Excel file ");
		String sheetName="sheet1";
		ArrayList<String> xlData1=excelData(sheetName,12,0);
		Iterator<String> iterator1 = xlData1.iterator();
		while (iterator1.hasNext()){ System.out.println(iterator1.next() );}
		System.out.println("1===========x===================x====================");
			
		Process process = null;
		stopwinium(process);
		String winiumPth = "D:\\\\AtmnWinium\\\\SadaWinnium\\\\Driver\\\\drvr\\\\new1.6\\\\Winium.Desktop.Driver.exe";
		process=startWinium(process, winiumPth);
				 
		DesktopOptions option=new DesktopOptions();
		option.setApplicationPath("C:\\Program Files (x86)\\Cisco Systems\\Cisco Jabber\\CiscoJabber.exe");
		System.out.println("opening cisco client");
		WiniumDriver driver=new WiniumDriver(new URL("http://localhost:9999"), option);
		System.out.println("Opened Jabber Client");
		//need to handle multiple ways of login
	/*	try {driver.findElement(By.name("Menu")).click();
		    }catch(Exception e) {System.out.println("Not able to click on setup ");}
	*/	//click file >> reset jabber//
		
		//To doubleClk -need actions class
		Actions act=new Actions(driver);//act.doubleClick().build(_WEB_ELEMENT_).perform();
		Robot rbot = null;
		try {
			 rbot = new Robot();
		    } catch (AWTException e1) {	e1.printStackTrace();   }
		
	/*	driver.findElement(By.name("Advanced settings")).click();
		System.out.println("clicked on adv settings");
		driver.findElement(By.name("Cisco IM  Presence")).click();
		driver.findElement(By.name("Use the following server")).click();
		//System.out.println("enter cisco server IP to login");
		driver.findElement(By.id("20568")).sendKeys(propertiesFileReader("cupsLoginServer"));
		driver.findElement(By.name("Save")).click();
		System.out.println("Entering  UID");
		driver.findElement(By.id("20544")).click();
		driver.findElement(By.id("20544")).sendKeys(propertiesFileReader("cupsUser"));
		driver.findElement(By.name("Continue")).click();
		driver.findElement(By.id("20094")).sendKeys(propertiesFileReader("cupsPwd"));;
		//driver.findElement(By.name("Password")).sendKeys("FaceTime@123");//facetime
		System.out.println("Click on sign-in");
		driver.findElement(By.name("Sign In")).click();//introduce some delay 10 sec
		try {Thread.sleep(9000);	} catch (InterruptedException e1) {	e1.printStackTrace();}
		System.out.println("Signed-in to jabber client");
	*/
		
/*	//-----------one-to-one conversation
		String userToChat=propertiesFileReader("userToMsg");
		System.out.println("opening 1to1 chat window with user: "+userToChat);
		driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='"+userToChat+"']")).click();
		act.doubleClick(driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='"+userToChat+"']"))).build().perform();
		
		//---------Sending 1to1 msg to user: from Excel file
			String data1 = null; int cellNo=0;
			String msgBoxXpath = "//*[@Name='RichEdit Control' or @ClassName='ATL:RICHEDIT50W']";
			for (int rowNo=0;rowNo<12;rowNo++) {
			try {
				data1 = readExcel("sheet1", rowNo, cellNo);//reading data 1 by 1 from excel
				System.out.println("sending msg"+ (rowNo+1) +":"+data1);
				driver.findElement(By.xpath(msgBoxXpath)).sendKeys(data1);
				System.out.println("Entered message: "+data1);
				rbot.keyPress(KeyEvent.VK_ENTER);
				} catch (Exception e) { System.out.println("Catch block of sending message ");	e.printStackTrace(); }
			}

		//-------------------verification
				System.out.println("Test verification");
				DatabaseUtils dbutils = new DatabaseUtils();
				//dbutils.getMessageAuditedCups("First test message");
				String xlActualData = null, xlQryData=null; int cellActualData=0,cellQueryData=1;
				for (int rowNo=0;rowNo<12;rowNo++)
				{ try {
						xlActualData = readExcel("sheet1", rowNo, cellActualData);//reading data 1 by 1 from excel
						xlQryData = readExcel("sheet1", rowNo, cellQueryData);
						String dbData = dbutils.getMessageAuditedCups(xlQryData);
						//debug---------------
						System.out.println("Excel data:"+xlActualData);
						System.out.println("DB data:"+dbData);
						//debug---------------
						if (dbData.equalsIgnoreCase(xlActualData)) 
						{
						System.out.println("1to1 data is audited properly with data: "+dbData);
						}
						else { System.out.println("1to1 Data is not audited properly for: "+xlActualData);
						}
					  } catch (Exception e) {	e.printStackTrace(); }		}
		//-------------------verification
	*/
		// ------------ ADhoc-Conv ----------------		
		String adUsers1=propertiesFileReader("adUser1");
		String adUsers2=propertiesFileReader("adUser2");
		String adUsers3=propertiesFileReader("adUser3");
		String adUsers4=propertiesFileReader("adUser4");
		
		System.out.println(String.format("Users: " + "%1$s, %2$s, %2$s and %3$s", adUsers1, adUsers2, adUsers3, adUsers4));
		System.out.println("Check");
		driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='"+adUsers1+"']")).click();
		rbot.keyPress(KeyEvent.VK_CONTROL);
		rbot.delay(30000);
		driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='"+adUsers2+"']")).click();
		//rbot.keyPress(KeyEvent.VK_CONTROL);
		driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='"+adUsers3+"']")).click();
		//Double click on last use-- or right-click
		//rbot.keyPress(KeyEvent.VK_CONTROL);
		//act.doubleClick(driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='"+adUsers4+"']"))).build().perform();
		
		//right-click--contextClick-- on last user
		//System.out.println("check rightclick function");
		act.contextClick(driver.findElement(By.xpath("//*[@ControlType='ControlType.TreeItem' and @Name='sumanth115u3@cupsqa115su3.com']"))).perform();
		rbot.keyRelease(KeyEvent.VK_CONTROL);
		
		try{driver.findElement(By.name("Start a group chat")).click();} catch(Exception e){driver.findElement(By.id("Item 2")).click();}
		//AdHoc chat-room name
		try {Thread.sleep(1000);} catch (InterruptedException e) {	e.printStackTrace();}
		//*[Name='30616']/*[@id='30759' or @classname='Edit']
		try {driver.findElement(By.id("30759")).click();//value=//*[@id='30759']}
		driver.findElement(By.id("30759")).sendKeys("AutomationAdocChatRoom");
			//driver.findElement(By.name("Cancel")).click();// works
			//driver.findElement(By.name("Start")).click();//works
		}
		catch(Exception e){e.printStackTrace();}
		
		System.out.println("Stopping Winium.Desktop.Driver.exe ");
		//process.destroy();
		stopwinium(process);
	}
	//======x=============x=======Supporting-methods==============x==============
	public static void stopwinium(Process process) {
		String dosCommand = "taskkill /IM \"Winium.Desktop.Driver.exe\" /F";
		 try {
			   process = Runtime.getRuntime().exec(dosCommand );
	           final InputStream in = process.getInputStream();
	           int ch;
	           while((ch = in.read()) != -1) {System.out.print((char)ch);}
	           System.out.println("Stopped winiumDriver.exe");
	      } catch (IOException e) { e.printStackTrace(); }
	}
	public static Process startWinium(Process process, String winiumPth) {
		try {
			Runtime runtime=Runtime.getRuntime();
			System.out.println("Trying to start winium driver");
			process=runtime.exec(winiumPth);
			System.out.println("Started WiniumDriver.exe");
		    } catch (IOException e) { e.printStackTrace();}
		return process;
	}
	public static ArrayList<String> excelData(String sheetName,int rows, int cellno) {
		//int rows->no of rows of data which is present in excel,  and cellno
		//int cellno=0;
		ArrayList<String> xlData = new ArrayList<String>();
		String data1 = null;
			for (int rowNo=0;rowNo<rows;rowNo++) //for (int rowNo=0;rowNo<12;rowNo++)
			{  try {
					data1 = readExcel(sheetName, rowNo, cellno);//reading data One-by-One from excel
				    } 
				catch (Exception e) { System.out.println("Catch block of sending message ");e.printStackTrace(); }
			
			xlData.add(data1);
			}
		return xlData;
	}
}