package com.Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cupsutils {

	public static long Getcurrenttime() {
		Calendar cal = Calendar.getInstance();
		return (cal.getTimeInMillis());
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}


