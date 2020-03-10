package generics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelHandler 
{
	public void readExcel(String sheetName, int rowNo , int cellNo) throws Exception 
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
		FileInputStream fis= new FileInputStream("D:\\AutomationDataCups\\OneToOneTestData.xlsx");
		Workbook wb= WorkbookFactory.create(fis);
		System.out.println("RowNo:"+rowNo+" cellNo:"+cellNo);
		String sx=wb.getSheet(sheetName).getRow(rowNo).getCell(cellNo).getStringCellValue();
		System.out.println("Full string: "+sx);
		//=======================================
		//===============Read================================
		//--Just read all row and column
/*		for(int i=1;i<8;i++) 
		{	for (int j=1;j<8;j++) 
			{  String sx1=wb.getSheet(sheetName).getRow(i).getCell(j).getStringCellValue();
				System.out.println("row:"+i+"  cell:"+j+"  Data:"+sx1);  
			}
		}*/
		//---
   }
		
		public void  writeExcel()
		{
			System.out.println("Write to excel code is note ready dont call this");
//			//===============write================ReadWriteExcel.java
//			Cell c.setCellValue(".......................");
//			//saveAs or save--depends
//			//path to save file 
//			FileOutputStream fos= new FileOutputStream("./dataA/TestOutput.xlsx");
//			wb.write(fos);
//			//================write===============
		}
}