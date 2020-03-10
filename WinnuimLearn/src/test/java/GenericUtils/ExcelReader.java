package GenericUtils;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	
	//public String readExcel(String exclFilePath, String sheetName, int rowNo , int cellNo) throws Exception 
	public String readExcel( String sheetName, int rowNo , int cellNo) throws Exception 
	{
		//================READ======================
		//file location
		/*System.out.println("Plz do check excel file path");
		FileInputStream fis= new FileInputStream("D:\\AutomationDataAndScreenshots\\TestData.xlsx");
		Workbook wb= WorkbookFactory.create(fis);//open excel sheet
		Sheet s=wb.getSheet("sheet1");//go to sheet 1
		Row r= s.getRow(0);//Go to 1 st row--for i=0
		Cell c=r.getCell(0);//go to cell1
		String x=c.getStringCellValue();//get value
		System.out.println(x);*/
		//=====================optimised===========================================
		System.out.println("Plz do check excel file path");
		//--------------------Excepect here sheetNAme RowNo CellNo if ok FilePath --------------
		FileInputStream fis= new FileInputStream("D:/AutomationDataCups/OneToOneTestData.xlsx");
		//FileInputStream fis= new FileInputStream(exclFilePath);
		Workbook wb= WorkbookFactory.create(fis);
		System.out.println("RowNo:"+rowNo+" cellNo:"+cellNo);
		String sx=wb.getSheet(sheetName).getRow(rowNo).getCell(cellNo).getStringCellValue();
		System.out.println("Full string: "+sx);
		return sx;
	}

}
