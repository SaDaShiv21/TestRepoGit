package com.actiance.generics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CsvUtilsLib {
	
	public List<CSVdata> getCsvObjects(String filepath) throws IOException {
		//System.out.println("CsvUtilsLib-GetCsvObjs>>>");
		BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"utf-8"));
		String currentLine = null;
		boolean firstRow = true;
		String[] currentLineData;
		CSVdata obj = null;
		List<CSVdata> objectsMap = new ArrayList<CSVdata>();
		//int i=1;
		while((currentLine = file.readLine())!=null){
			if(firstRow){  //skip 1st line
				//System.out.println("currentLine1:"+currentLine);
				firstRow = false;
				continue;
			} //System.out.println("Row: "+ (i++) );
			obj = new CSVdata();
			currentLineData = currentLine.split(",");	
			obj.setTestCaseNo(currentLineData[0]);
			obj.setFromUser(currentLineData[1]);
			obj.setToUserOrRoom(currentLineData[2]);
			obj.setImOrGc(currentLineData[3]);
			obj.setMsgTxt(currentLineData[4]);
			obj.setExecute(currentLineData[5]);
			
			if(obj.getExecuteYesOrNO().trim().equalsIgnoreCase("Yes"))
			{		
				objectsMap.add(obj);
			}
		}
		file.close();
		return objectsMap;
	}
	// with excelFile-
	public static List<CSVdata> getCsvObjects(String filepath,int tcelno) {
		CSVdata obj = null;
		List<CSVdata> objectsMap = new ArrayList<CSVdata>();
		List<List<String>> alldata1 = readExcelRow(filepath,tcelno);
		System.out.println("size:"+alldata1.size());
    	for (int i=0; i<alldata1.size();i++) 
    	{	List<String> test = alldata1.get(i);
    		//System.out.println(test.size());
    		if(test.size() != 0)
    		{//TestCaseNo >> FromUser >> ToUserGC >> TypeImOrGC >> Disc >> CheckJoinLeave >> MsgText >> Execute
    		 //System.out.println(test.get(0)+" "+test.get(1)+" "+test.get(2)+" "+test.get(3)+" "+test.get(4)+" "+test.get(5)+" "+test.get(6)+" "+test.get(7));
    			obj = new CSVdata();	
    			obj.setTestCaseNo(test.get(0) );
    			obj.setFromUser(test.get(1) );
    			obj.setToUserOrRoom(test.get(2) );
    			obj.setImOrGc(test.get(3) );
    			obj.setCheckDisclaimer(test.get(4) );
    			obj.setCheckJoinLeave(test.get(5) );
    			obj.setMsgTxt(test.get(6) );
    			obj.setSqlQuery(test.get(7));
    			obj.setExecute(test.get(8) );
    			
    			if(obj.getExecuteYesOrNO().trim().equalsIgnoreCase("Yes")) 
    			{objectsMap.add(obj);}
    	   }
    	}
		//=============================
		return objectsMap;
	}
		
    public static List<List<String>> readExcelRow(String filepath,int tcelno) {
    	//System.out.println();
    	List<List<String>> alldata = new ArrayList<List<String>>();
    	List<String> rowdata;
    	boolean frflag=true;
    	int numberofcells=0;
    	try {FileInputStream fin = new FileInputStream(new File(filepath));// get file
        XSSFWorkbook wb = new XSSFWorkbook(fin); // create book holding object
        XSSFSheet sheet = wb.getSheetAt(0);// get sheet
        //To iterate through rows       
        Iterator<Row> rowIt = sheet.rowIterator();
        while(rowIt.hasNext() )
        {	rowdata = new ArrayList<String>();
        	XSSFRow row = (XSSFRow) rowIt.next();
        	if(frflag) {//skip first-row and get clm count//----------------------
        		System.out.print("TestDataFormat:");
        		Iterator<Cell> colIt = row.cellIterator();
                while (colIt.hasNext() && (numberofcells != tcelno) ) 
                	{	Cell cell = colIt.next(); 
                		System.out.print("\t"+cell.toString()); 
                		numberofcells++;
                	}
            	frflag = false;
            }
            else {//--------------------------------------------------------------
            	numberofcells=0;
            	Iterator<Cell> colIt = row.cellIterator();
                while (colIt.hasNext() && (numberofcells != tcelno)) 
                	{ Cell cell = colIt.next(); //System.out.println(colIt.next().toString());
                	  rowdata.add(cell.toString()); //System.out.print("\t"+cell.toString()); 
                	  numberofcells++;
                	}
            }
        	alldata.add(rowdata);
        }
    	} catch (FileNotFoundException e) { e.printStackTrace();} catch (IOException e) { e.printStackTrace(); }
    	System.out.println();
    	return alldata;
    }
	//===========================================================
 /*   public static void main(String[] args) {
    	//List<List<String>> alldata1 = readExcelRow(8,"./configFiles/CupsTestData.xlsx");
    	
    	System.out.println("size:"+alldata1.size());
    	for (int i=0; i<alldata1.size();i++) {
    		List<String> test = alldata1.get(i);
    		System.out.println(test.size());
    		if(test.size() != 0) {
    		for(int j=0;j<test.size();j++) {
    			System.out.print("\t"+test.get(j));
    		}
    		System.out.println("\n"+test.get(0)+test.get(1)+test.get(2)+test.get(3)+test.get(4)+test.get(5)+test.get(6)+test.get(7));
    		//test.get(0);test.get(1);test.get(2);test.get(3);test.get(4);test.get(5);test.get(6);test.get(7);
    	}System.out.println();
    	}    	
    	System.out.println();
    	List<CSVdata> checkObj = getCsvObjects("./configFiles/CupsTestData.xlsx",8);
    	System.out.println(checkObj);
	}
*/
}