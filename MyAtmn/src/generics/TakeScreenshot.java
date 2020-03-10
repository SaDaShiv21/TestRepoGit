package generics;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.apache.commons.io.FileUtils;

public class TakeScreenshot 
{ 
  public static void browserScreenshot(WebDriver driver) throws IOException
	{
	SimpleDateFormat s= new SimpleDateFormat ("dd-MM-yy_hh-mm-ss");
	String TimeNow = s.format (new Date());
	String location="D:\\AutomationScreenshots\\Taken-";
	String FileName=location+TimeNow+".png";
	System.out.println(FileName);
			
	TakesScreenshot t=(TakesScreenshot)driver;
	File scrFile = t.getScreenshotAs(OutputType.FILE);
	//or--File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE)
	FileUtils.copyFile(scrFile, new File(FileName));
	//Storing file in required Location
	} 
//	public void desktopScreenshot() 
//	{}
}
