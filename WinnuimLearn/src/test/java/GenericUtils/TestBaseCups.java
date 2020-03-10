package GenericUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public class TestBaseCups {
	
	private static Properties prop;
	protected static WebDriver driver;
	protected static DatabaseUtils dbutils;
	//protected static ExcelReader exclRdr;
	protected static Logger logger;

	@Parameters({ "configPropertiesFile",  "vantageIP" })
	@BeforeSuite
	public static void beforeSuite(String configPropertiesFile,
			String vantageIP )
			throws InterruptedException, IOException {
		System.out.println("Inside Test BASE");
		/*System.setProperty("dbHost", dbHost);
		System.setProperty("dbName", dbName);*/
		System.setProperty("ConfigPropertiesFile", configPropertiesFile.trim());
				
		prop = new Properties();
		File file = new File(System.getProperty("ConfigPropertiesFile"));
		FileInputStream f = new FileInputStream(file);
		prop.load(f);
		
		
		System.setProperty("dbHost", getObject("dbHostIP"));
		System.setProperty("dbName", getObject("dbName"));
		System.setProperty("dbType", getObject("dbType"));
		System.setProperty("dbUsername", getObject("dbUsername"));
		System.setProperty("dbPassword", getObject("dbPassword"));
		//====================
		logger = Logger.getLogger(TestBaseCups.class);
		logger.info("URL : " + System.getProperty("URL"));
		dbutils = new DatabaseUtils();
		//exclRdr = new ExcelReader();
		
		//exclRdr.readExcel("", 1, 1);
		
		//init(System.getProperty("URL"));
	}

	public static String getURLFromVantageIP(String vantage) throws InterruptedException {
		return "https://" + vantage + ":8443/ima/";
	}

	public static String getObject(String Data) {
		String data = prop.getProperty(Data);
		return data;
	}

	public static void init(String url) throws IOException {
		System.out.println("init method");
		System.out.println(url);
		//open browser if needed
		// open jabber client===
	}
	
	public static String propertiesFileReader(String x) 
	{
		System.out.println("Config.props file reader recieved parameter:"+x);
		String r1 = null;
		try 
		{
			//make sure to check file path if there is an error
			File file = new File("./UtilityFiles/ConfigData.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			String reader = properties.getProperty("Creater");
			r1 = properties.getProperty(x);
			//System.out.println("LoginProperty file reader file reader passing key: "+ x+"-value: "+r1);
			
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) {	e.printStackTrace(); }
		return r1;
	}
	
	//public String readExcel(String exclFilePath, String sheetName, int rowNo , int cellNo) throws Exception 
	public static String readExcel( String sheetName, int rowNo , int cellNo) throws Exception 
	{		
		//System.out.println("Plz do check excel file path");
		//--------------------Excepect here sheetName RowNo CellNo if ok FilePath --------------
		//  ./UtilityFiles/OneToOneTestData.xlsx //D:/AutomationDataCups/OneToOneTestData.xlsx
		//FileInputStream fis= new FileInputStream(propertiesFileReader("excelFilePath"));
		FileInputStream fis= new FileInputStream("./UtilityFiles/OneToOneTestData.xlsx");
		//FileInputStream fis= new FileInputStream(exclFilePath);
		Workbook wb= WorkbookFactory.create(fis);
		System.out.println("Reading: "+"RowNo:"+rowNo+" cellNo:"+cellNo);
		String sx=wb.getSheet(sheetName).getRow(rowNo).getCell(cellNo).getStringCellValue();
		System.out.println("For RowNo"+rowNo+" CellNo"+cellNo+" Data is:"+sx);
		return sx;
	}

	@AfterClass
	public void afterClass() {
		System.out.println("After Class");
		//dashboard.clickConfiguration();
		//close DB connection
		//close chat window if needed
	}
}
