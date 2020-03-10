package com.pagesPOM.vantage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InteractionExporterPage 
{
	public static void check() 
	{
		System.out.println("TestMethod inside POM class");
	}
	////select[@id='exporterNum']/option[@value=0]-selectExporter-by value
	public static int ExporterNo; //Logic
	public void exporterno(int i) 
	{		ExporterNo = i; System.out.println("Exporter selected is: "+ExporterNo);	}
	public static String xprtrNo="//select[@id='exporterNum' or name='exporterNum']/option[@value="+ExporterNo+"]";
	@FindBy(xpath="//select[@id='exporterNum' or name='exporterNum']/option[@value=ExporterNo]")
	private static WebElement selectExporter;
	public WebElement selectExporter()
	{	System.out.println("Please pass exporter number using exporterno()");		
		return selectExporter;  }
	
	@FindBy(xpath="//input [@id='go' or @onclick='changeExporter();' or @value='Go']")
	private static WebElement goBTN;
	public WebElement goBTN()
	{	
		return goBTN;	
	}
	
	@FindBy(xpath=" //input[@onclick='createNew();' or @value='Create New']")
	private static WebElement createNewBTN;
	public WebElement createNewBTN()
	{	return createNewBTN;	}
	
	@FindBy(xpath="//h2[2]/a  |  //a[text()='Setup']")
	private static WebElement setupLink;
	public WebElement setup()
	{	return setupLink;	}
	   
	  @FindBy(xpath="//input[@id='enabledFlag' or @name='enabledFlag']")
	  private static WebElement exporterEnable;
	  public WebElement exporterEnableCheckBox()
	  {	return exporterEnable;	}
	  
	  @FindBy(xpath="//input[@id='logStatusFlag' or @name='logStatusFlag']")
	  private static WebElement LogAllActivity;
	  public WebElement logAllActivity()
	  {	return LogAllActivity;	}
	
	//server ID logic
    public static void serverIdSelect(WebDriver driver, int optionValue) 
	 {
	  if( !driver.findElement(By.xpath("//select [@id='serverIDs']//option["+optionValue+"] | (//select[@name='serverIDs']//option)["+optionValue+"]")).isSelected())
	    {  driver.findElement(By.xpath("//select [@id='serverIDs']//option["+optionValue+"] | (//select[@name='serverIDs']//option)["+optionValue+"]")).click();  }
	 }  
	
	@FindBy(xpath="//h2[3]/a  |  //a[contains(text(), 'Filters')]")
	private static WebElement exporterFilters;
	public WebElement exporterFilters()
	{	return exporterFilters;	}
	
	   @FindBy(xpath="//input[@id='filter_InterProcessDateEnabled'] |  //input[@onclick='onClickInterProcessDates()'] | //input[@name='filter_InterProcessDateEnabled']")
		private static WebElement pcb;
		public WebElement processInteractionFromCheckB()
		{	return pcb;	}
		
		@FindBy(xpath="//input[@id='filter_processFromDate' or @name='filter_processFromDate']")
		private static WebElement pfd;
		public WebElement processFromDate()
		{	return pfd;	}
		
		@FindBy(xpath="//input[@id='filter_processToDate' or @name='filter_processToDate']")
		private static WebElement ptd;
		public WebElement processToDate()
		{	return ptd;	}

	@FindBy(xpath="//h2[5]/a  |  //a[contains(text(), 'Frequency')]")
	private static WebElement freq;
	public WebElement frequency()
	{	return freq;	}
	   @FindBy(xpath="(//input[@id='continuousProcessingIntersFlag'])[2] | //input[@onclick='return onclickTime()'] | (//input[@name='continuousProcessingIntersFlag'])[2]")
		private static WebElement spRB;
		public WebElement StartProcessingAtRBtn()
		{	return spRB;	}
		//input[@type='text' and @name='fixedTime_timeToProcessInters']
		@FindBy(xpath="//input[@type='text' and @name='fixedTime_timeToProcessInters']")
		private static WebElement startTime;
		public WebElement exporterStartTime()
		{	return startTime;	}
		@FindBy(xpath="//select[@name='fixedTime_timezoneToProcessInters']/option[@value='IST']")
		private static WebElement timeZone;
		public WebElement exporterStartTimezoneIST()
		{	System.out.println("Time zone hard coded to IST");
			return timeZone;	}
	
	@FindBy(xpath="//h2[6]/a  |  //a[contains(text(), 'Export type')]")
	private static WebElement exprtp;
	public WebElement ExportType()
	{	return exprtp;	}
	//------------------
		@FindBy(xpath="//input[@id='smtpSender'] | //input[@name='smtpSender']")
		private static WebElement sender;
		public WebElement smtpSender()
		{	return sender;	}
		@FindBy(xpath="//input[@id='smtpRecipients'] | //input[@name='smtpRecipients']")
		private static WebElement recipients;
		public WebElement smtpRecipients()
		{	return recipients;	}
		@FindBy(xpath="//input[@id='smtpServer'] | //input[@name='smtpServer']")
		private static WebElement server;
		public WebElement smtpServer()
		{	return server;	}
	//------------------------------
	
	@FindBy(xpath="//h2[7]/a  |  //a[contains(text(), 'Format Settings')]")
	private static WebElement fst;
	public WebElement formatSettings()
	{	return fst;	}	
	    @FindBy(xpath="//select[@id='timezoneToFormatInters']/option[@value='IST'] | //select[@name='timezoneToFormatInters']/option[@value='IST']")
		private static WebElement fstTimeZone;
		public WebElement formatSettingsTimeZoneIST()
		{	System.out.println("Format setting tmie zone hard coded to IST");
			return fstTimeZone;	}
		@FindBy(xpath="//select[@id='converterClass']/option[2] | //select[@name='converterClass']/option[2]")
		private static WebElement outputFmt;
		public WebElement outPutFormat()
		{	System.out.println("Format setting tmie zone hard html1");
			return outputFmt;	}
		@FindBy(xpath="//select[@id='attachmentConverterClass']/option[2] | //select[@name='attachmentConverterClass']/option[2]")
		private static WebElement outputFmtIntractn;
		public WebElement outPutFormatInteractionAttchmnt()
		{	System.out.println("Format setting tmie zone hard coded html1");
			return outputFmtIntractn;	}
	
	@FindBy(xpath="//input[@id='submit3' and @type='button' or @onclick='apply();' or @value='OK']")
	private static WebElement ok;
	public WebElement okBtn()
	{	return ok;	}
	
	@FindBy(xpath="//input [@id='deactivate2' or onclick='delConfirmationDialog();' or value=' Delete']")
	private static WebElement delete;
	public WebElement delBTN()
	{	return delete;	}
	
}

