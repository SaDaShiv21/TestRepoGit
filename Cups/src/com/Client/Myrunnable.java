package com.Client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;

public class Myrunnable extends CupsLoad implements Runnable 
{
	   int userStartIndex;
	   int userperthread;
	   public Myrunnable(int usIndex,int upthread) {
	        this.userStartIndex = usIndex;
	        this.userperthread = upthread;
	    }
	   
	   //int numberofthreads = 4;
	   public int getuserstartindex()
	   {
		   return(this.userStartIndex);
	   }
	  
	   
	public void run() {
	try {
			sendmessage(this.userStartIndex,this.userperthread);
		} catch (XMPPException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	 public static void main(String[] args) 
	   {
		 ServiceDiscoveryManager.setIdentityName(resourcename);
		 int userstartindex,userperthread,numberofthreads;
		 
		 getProperties();
		 userstartindex=buddynamestartindex;
		 numberofthreads=2;
		   userperthread = numberofusers/numberofthreads;
		   
		 Myrunnable myRunnable =new Myrunnable(userstartindex,userperthread);
		   //CupsLoad A= new CupsLoad();
		   	     
		   //XMPPConnection.DEBUG_ENABLED = true;
		   createusers();
		   try {
			userssignin();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   for(int i=0;i<numberofthreads;i++)
		   {
			    myRunnable =new Myrunnable(userstartindex,userperthread);
		        Thread t = new Thread(myRunnable);
		        String Tstartdate = getCurrentTimeStamp();
		        System.out.println("Test Start time is   " + Tstartdate);
		        t.start();
			   userstartindex+=userperthread;
			   System.out.println("Thread " + i);
			   //System.exit(0);
		   }
		   /*A.disconnect();*/
	   }
	   
}