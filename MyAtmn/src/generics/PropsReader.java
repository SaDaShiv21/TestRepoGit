package generics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropsReader 
{
	public static String reader(String x) 
	{
		System.out.println("Config.props file reader recieved parameter:"+x);
		String r1 = null;
		try 
		{
			File file = new File("./AutoData/config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			String reader = properties.getProperty("Creater");
			r1 = properties.getProperty(x);
			System.out.println("LoginProperty file reader file reader passing key: "+ x+"-value: "+r1);
			
//			Enumeration enuKeys = properties.keys();
//			while (enuKeys.hasMoreElements())
//			{
//				String key = (String) enuKeys.nextElement();
//				String value = properties.getProperty(key);
//				System.out.println("For key: "+key + " - "+" value is: " + value);
//			}
		} 
		
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) {	e.printStackTrace(); }
		return r1;
	}
	public static String smtpProps(String y) 
	{
		System.out.println("smtpData.props file reader recieved parameter:"+y);
		String r1 = null;
		try 
		{
			File file = new File("./AutoData/smtpData.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			String reader = properties.getProperty("Creater");
			r1 = properties.getProperty(y);
			System.out.println("SmtpProperty file reader file reader passing key: "+ y+"-value: "+r1);
		}
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) {	e.printStackTrace(); }
		return r1;
	}
}