package com.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import java.util.*;
import java.io.*;
 
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.sun.org.apache.bcel.internal.generic.PUSH;



public class CupsLoadnew extends CupsUsers{
	
	public CupsLoadnew(String buddy, String fullname, long delay) {
		super(buddy, fullname, delay);
		System.out.println(String.format(" Constructor >>Buddy:%1$s, Fullname:%2$s, delay:%3$s",buddy,fullname,delay));
		
	}

	public CupsLoadnew() {
		System.out.println("Constructor >>");
	}

	public static int numberofusers;
	public static int messagerate;
	public static int loadrunduration;
	public static String buddynameprefix;
	public static String resourcename;
	public static int buddynamestartindex;
	public static String server;
	public static String password;
	public static String domainname;
	public static String msgtext;
	public static long msgsPerSecPerUser;
	public static long messageDelayPerUser;
	public static long duration;
	
	public static CupsUsers cupsusrs[];
	public static String users[];			
	public static void getProperties()
	{
		Properties prop = new Properties();
		 
    	try {
            //load a properties file
    		prop.load(new FileInputStream("./configFiles/config.properties"));
    		
            //get the property value and print it out
    		numberofusers = new Integer(prop.getProperty("Number_of_users"));
    		messagerate = new Integer(prop.getProperty("messagerate"));
    		loadrunduration =new Integer(prop.getProperty("loadrunduration"));
    		buddynameprefix = prop.getProperty("buddynameprefix");
    		resourcename = prop.getProperty("resourcename");
    		buddynamestartindex = new Integer (prop.getProperty("buddyNameIndex"));
    		server = prop.getProperty("server");
    		password = prop.getProperty("password");
    		domainname = prop.getProperty("domainname");
    		msgtext = prop.getProperty("msgtext");
    		
    		msgsPerSecPerUser=(messagerate/numberofusers);
    		messageDelayPerUser=1000/msgsPerSecPerUser;
    		duration=loadrunduration*60*1000;
    		
    		users = new String[numberofusers];
    		for (int i=0;i<numberofusers;i++) {
    			String userprefix = "user"+(i+1);
    			users[i]=prop.getProperty(userprefix);
    			//System.out.println("props-"+userprefix+":"+prop.getProperty(userprefix)+"  =>> users["+i+"]<<"+users[i]);
    			System.out.println("users["+i+"]="+users[i]+" <<= Props-"+userprefix+":"+prop.getProperty(userprefix));
    		}
    		    		
    		System.out.println("numberofusers:"+numberofusers);
    		System.out.println("messagerate:"+messagerate );
    		System.out.println("loadrunduration:"+loadrunduration);
    		System.out.println("buddynameprefix:"+buddynameprefix );
    		System.out.println("resourcename:"+resourcename  );
    		System.out.println("buddynamestartindex:"+buddynamestartindex);
    		System.out.println("server:"+server);
    		System.out.println("password:"+password);
    		System.out.println("domainname:"+domainname);
    		System.out.println("msgtext:"+msgtext);
    		System.out.println("msgsPerSecPerUser:"+msgsPerSecPerUser);
    		System.out.println("messageDelayPerUser:"+messageDelayPerUser);
    		System.out.println("duration:"+duration);
    		    		
    	} catch (IOException ex) 
    	{
    		ex.printStackTrace();
         }
   }
    	//create users
    public static void createusers()
    {
    	cupsusrs = new CupsUsers[numberofusers];
    	for(int i=0;i<numberofusers;i++)
    	{
    	  String bname = new Integer(buddynamestartindex++).toString(); 
    	  String buddyname= buddynameprefix.concat(bname);
    	  String fullname = buddyname.concat("@").concat(domainname);
    	//  CupsUsers user = new CupsUsers(buddyname, fullname, messageDelayPerUser); 
    	  
    	  cupsusrs[i]=new CupsUsers(buddyname, fullname, messageDelayPerUser); 
    	  System.out.println(cupsusrs[i].Getbuddyname());
    	}
    }
    public static void userData() {
    	cupsusrs =new CupsUsers[numberofusers];//need to change int value
    	for(int i=0 ; i<numberofusers;i++) {
    	String buddyname = users[i];
    	String fullname = buddyname.concat("@").concat(domainname);
    	cupsusrs[i] = new CupsUsers(buddyname,fullname,messageDelayPerUser);
    	}
    }
   
    //static XMPPConnection[ ] userconnection;
    static XMPPConnection[ ] userxmppconnections;
	static JabberSmackAPI[ ] jbrSmackApiObjs;
	//static JabberSmackAPI[ ] userconnection1;
	
   public static void userssignin() throws XMPPException
   {
	   userxmppconnections = new XMPPConnection[numberofusers];
	   jbrSmackApiObjs = new JabberSmackAPI[numberofusers];
	 
	  for(int i=0;i<numberofusers;i++)
   		{
		  jbrSmackApiObjs[i] = new JabberSmackAPI(); 
		  String userName = cupsusrs[i].Getbuddyname();
		  System.out.println( cupsusrs[i].buddyname + "User signing in");
  		  // userconnection[i]= obj1.login(userName,"facetime");
		  userxmppconnections[i]= jbrSmackApiObjs[i].login(userName,"FaceTime@123", "192.168.125.181", "cupsqa115su3.com");//returnz Userconn
		   if(userxmppconnections[i] != null)
  			{
			   cupsusrs[i].connected=true;
  			   try { Thread.sleep(4000);	} catch (InterruptedException e) {	e.printStackTrace();  }
  			}
   		}
   }
   
   	
	public static XMPPConnection singleUserSignIn(String userName, String password, String pbSbIP, String domain)
			throws XMPPException {
		//"String domain" should be associated to DC in etc\host
		System.out.println(String.format("Login with >> UN:%1$s, Pwd:%2$s, PbSbIP:%3$s, domain:%4$s", userName,password,pbSbIP,domain));
		XMPPConnection usrconnection;
		JabberSmackAPI jsa = new JabberSmackAPI();
		usrconnection = jsa.login(userName, password, pbSbIP, domain);
		if (usrconnection != null) { System.out.println("User login successful :"+usrconnection.getUser()); }
		return usrconnection;
		//return this.usrconnection;
	}
   
   public static void sendmessage(int User_Startindex, int no_of_users) throws XMPPException
   {
	   numberofusers = User_Startindex + no_of_users-1;
	   long starttime = Getcurrenttime();
	   long elapsedtime = 0;
	   String currtime;
	  while (elapsedtime < duration)
	  {
	   for(int i=User_Startindex-1;i<numberofusers;i++)
  		{
		   if(cupsusrs[i].connected)
		   {
			   boolean cansend = cupsusrs[i].cansend();
			if(cansend==true)
			{
			  Random randomGenerator = new Random();
			   int number = randomGenerator.nextInt(numberofusers);//simply a random no generated 
			   if(number == i)
			   {
				   number=randomGenerator.nextInt(numberofusers);
			   }
			   System.out.println("number:"+number+" i:"+i);
			   SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   Date now = new Date();
			   currtime = dateformat.format(now);
			  
			   msgtext = "SadaShiv" + "-" + currtime;//msg text to be sent
			   
			   long t1 = Getcurrenttime();
			   //declared above as >>static JabberSmackAPI[ ] obj;>>obj[i] = new JabberSmackAPI(); 
			   System.out.println(String.format("SendMsg>> MsgTxt:%1$s, user:%2$s, UsrCon:%3$s", msgtext, cupsusrs[number].userfullname, userxmppconnections[i]));
			   jbrSmackApiObjs[i].sendMessage(msgtext, cupsusrs[number].userfullname, userxmppconnections[i]);
			   long t2 = Getcurrenttime();
			   long msgsendingdelay = t2 - t1;
			   cupsusrs[i].msgsent(msgsendingdelay);
			   
			   //System.out.println(users[i].cansend());
			   System.out.println("Sending Message for user" + cupsusrs[i].buddyname + " message count is" + cupsusrs[i].msgCount);
		   }
			else{
			//System.out.println("not sending message");
			}
  		}
	   elapsedtime = Getcurrenttime() - starttime;
	   }
   	}
	  String endtime = getCurrentTimeStamp();
	  System.out.println("Test end time is " + endtime);
  }
   
   
   public void disconnect()
   {
	   for(int i=0;i<numberofusers;i++)
 		{
		   if(cupsusrs[i].connected)
		   {
			   jbrSmackApiObjs[i].disconnect();
		   }
 		}
   }
   
   public static void main(String[] args) throws XMPPException 
   {
	
	  System.out.println("Start....");
	   int userstartindex,userperthread,numberofthreads;
	   numberofthreads=2;
	   
	   userperthread = 4/numberofthreads;
	   userstartindex=1;
	  // XMPPConnection.DEBUG_ENABLED = true;
	   getProperties();
	   userData();
	   /*createusers();
	   userssignin();
	   sendmessage(userstartindex,userperthread);*/
	  
	  try {
	   //XMPPConnection.DEBUG_ENABLED = true;
	   XMPPConnection usrcon = singleUserSignIn("katte115u2", "FaceTime@123", "192.168.125.181", "cupsqa115su3.com");
	   GroupChat grpcht = new GroupChat();
	   MultiUserChat roomconn = new MultiUserChat(usrcon, "katte115u2@cupsqa115su3.com");
	   roomconn.create("newtestpchatrooom27991968919689@conference-2-standaloneclusterb51da.cupsqa115su3.com");
	  }catch(Exception e) {e.printStackTrace(); System.exit(0);}
	   System.exit(0);
	   }
	   
	   /*A.disconnect();*/
	
   }