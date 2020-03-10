package com.actiance.generics;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
 
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
 
 
public class ExcelDataRdr {
    public static void main(String[] args) throws IOException {
    	
    	String filepath = "./configFiles/OneToOneTestData.xlsx";
    	readExcelRow(0,filepath);
 
    }
    
    public static ArrayList<String> readExcelRow(int celno, String filepath) {
    	System.out.println();
    	ArrayList<String> xlData = new ArrayList<String>();
    	String data1 = null;
    	
    	try {FileInputStream fin = new FileInputStream(new File(filepath));// get file
    	//try {FileInputStream fin = new FileInputStream(new File("./configFiles/OneToOneTestData.xlsx"));// get file
        XSSFWorkbook wb = new XSSFWorkbook(fin); // create book holding object
        XSSFSheet sheet = wb.getSheetAt(0);// get sheet
        
        //To iterate through rows       
        Iterator<Row> rowIt = sheet.rowIterator();
        while(rowIt.hasNext() ){
        	System.out.println(  rowIt.hasNext() );
        	XSSFRow row = (XSSFRow) rowIt.next();
        	//To iterate through Columns
            Iterator<Cell> colIt = row.cellIterator();
            while (colIt.hasNext()) {
            	Cell cell = colIt.next();
                System.out.println(cell.toString());
            }
        	data1 = row.getCell(celno).toString();
           //System.out.println( row.getCell(celno).toString() );
        	xlData.add(data1);
        }
    	} catch (FileNotFoundException e) { e.printStackTrace();} catch (IOException e) { e.printStackTrace(); }
    	return xlData;
    }
    

    public static void readAllrowsAndAllCells() {
        try {
        	 
            FileInputStream excelFile = new FileInputStream(new File("./configFiles/OneToOneTestData.xlsx"));// ./configFiles/OneToOneTestData.xlsx
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
 
            while (iterator.hasNext()) {
 
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
 
                while (cellIterator.hasNext()) {
 
                    Cell currentCell = cellIterator.next();
 
                    if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                        System.out.print(currentCell.getStringCellValue() + "***");
                    } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "***");
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}