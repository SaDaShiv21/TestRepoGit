package com.testcases.vantage;

import generics.ExcelHandler;

public class CheckXLreader {

	public static void main(String[] args) throws Exception 
	{
		ExcelHandler xll=new ExcelHandler();
		int cell=0;
		for (int row=0;row<12;row++) {
		xll.readExcel("Sheet1", row, cell);
		System.out.println("");
		}
	}
}
