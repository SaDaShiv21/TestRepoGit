package com.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import java.util.*;
import java.io.*;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.sun.org.apache.bcel.internal.generic.PUSH;



public class CupsLoadModifications extends CupsUsers{
	
	public CupsLoadModifications(String buddy, String fullname, long delay) {
		super(buddy, fullname, delay);
		System.out.println(String.format(" Constructor >>Buddy:%1$s, Fullname:%2$s, delay:%3$s",buddy,fullname,delay));
		
	}

	public CupsLoadModifications() {
		
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
		
	public static CupsUsers users[];
	public static String usersbuddyname[];
	public static String userspwd[];
	public static String userspbsb[];
	public static String pbsbip;
	public static int numberofrooms; 
	public static String nwayrooms[];
	public static String pchatrooms[];
	
	public static void getProperties()
	 {
		Properties prop = new Properties();
    	try {
            //load a properties file
    		prop.load(new FileInputStream("./configFiles/config.properties"));
    		
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
    		pbsbip = prop.getProperty("pbSbIP");
    		
    		msgsPerSecPerUser=(messagerate/numberofusers);
    		messageDelayPerUser=1000/msgsPerSecPerUser;
    		duration=loadrunduration*60*1000;
    		//============================user details
    		usersbuddyname = new String[numberofusers];
    		userspwd = new String[numberofusers];
    		System.out.println("DomainName:"+domainname+"    Publisher/Subscriber:"+pbsbip);
    		for (int i=0;i<numberofusers;i++) {
    		String userprefix = "user"+(i+1);
    		String usrpwd = "passwordofuser"+(i+1);
    		usersbuddyname[i]=prop.getProperty(userprefix);
			userspwd[i]=prop.getProperty(usrpwd);
			System.out.println("usersBuddyname["+i+"]="+usersbuddyname[i]+" <<= Props-"+userprefix+":"+prop.getProperty(userprefix) +" "+usrpwd+":"+userspwd[i]);
			}
    		//========================room details
    		numberofrooms= new Integer(prop.getProperty("numberOfnwayPchatrooms"));
    		nwayrooms =new String[numberofrooms];
    		pchatrooms = new String[numberofrooms];
    		for(int j=0;j<numberofrooms;j++) {
    			String nwayz = "nwayroomID"+(j+1);
        		String pchatz = "pchatroomID"+(j+1);
        		nwayrooms[j]= prop.getProperty(nwayz);
        		pchatrooms[j]= prop.getProperty(pchatz);
        		System.out.println("Nway["+j+"] :"+nwayrooms[j]);
        		System.out.println("Pchat["+j+"] :"+pchatrooms[j]);
    		}

    	} catch (IOException ex) {	ex.printStackTrace();  }
   }
    	//create users
/*    public static void createusersOriginal()//this is kept for ref 
    {	users = new CupsUsers[numberofusers];
    	for(int i=0;i<numberofusers;i++)
    	{ String bname = new Integer(buddynamestartindex++).toString(); 
    	  String buddyname= buddynameprefix.concat(bname);
    	  String fullname = buddyname.concat("@").concat(domainname);
    	  users[i]=new CupsUsers(buddyname, fullname, messageDelayPerUser); 
    	  System.out.println(users[i].Getbuddyname());
    	}
    }*/
    
	public static void createusers()
    {	users = new CupsUsers[numberofusers];
    	for(int i=0;i<numberofusers;i++)
    	{ String buddyname = usersbuddyname[i];
    	//String bname = new Integer(buddynamestartindex++).toString(); 
    	//String buddyname= buddynameprefix.concat(bname);
    	  String fullname = buddyname.concat("@").concat(domainname);
    	  users[i]=new CupsUsers(buddyname, fullname, messageDelayPerUser); 
    	 // System.out.println("BuddyName:"+users[i].Getbuddyname()+" Fullname:"+users[i].Getuserfullname());
    	}
    }
	
    static XMPPConnection[ ] userconnection;
	static JabberSmackAPI[ ] obj;
   public static void userssignin() throws XMPPException
   {
	   userconnection = new XMPPConnection[numberofusers];
	   obj = new JabberSmackAPI[numberofusers];
	 
	  for(int i=0;i<numberofusers;i++)
   		{
		  obj[i] = new JabberSmackAPI(); 
		  String userName = users[i].Getbuddyname();
		  String password = userspwd[i];
		  String pbSbIP = pbsbip;System.out.println("\nLogin with>> un:"+userName+" pwd:"+password+" pbsb:"+pbSbIP+" domain:"+domainname);
		  //userconnection[i]= obj[i].login("katte115u1","FaceTime@123", "192.168.125.181", "cupsqa115su3.com");
		 userconnection[i]= obj[i].login(userName,password, pbSbIP, domainname);
		   if(userconnection[i] != null)
  			{
			   System.out.println( users[i].buddyname + " User signed in UserConn>OBJ:"+userconnection[i]);
  			   users[i].connected=true;
  			   try { Thread.sleep(4000);	} catch (InterruptedException e) {	e.printStackTrace();  }
  			}
   		}
	  System.out.println();
	  //System.out.println("UserConObjs: "+userconnection);
   }
   
   /*public static void sendmessage(int User_Startindex, int no_of_users) throws XMPPException
   {
	   numberofusers = User_Startindex + no_of_users-1;
	   long starttime = Getcurrenttime();
	   long elapsedtime = 0;
	   String currtime;
	  while (elapsedtime < duration)
	  {
	   for(int i=User_Startindex-1;i<numberofusers;i++)
  		{
		   if(users[i].connected)
		   {
			   boolean cansend = users[i].cansend();
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
			  
			   msgtext = "ImMessage" + "-" + currtime +" to>> "+users[number].userfullname;//msg text to be sent
			   
			   long t1 = Getcurrenttime();
			   //declared above as >>static JabberSmackAPI[ ] obj;>>obj[i] = new JabberSmackAPI(); 
			   System.out.println(String.format("SendMsg>> MsgTxt:%1$s, user:%2$s, UsrCon:%3$s", msgtext, users[number].userfullname, userconnection[i]));
			   obj[i].sendMessage(msgtext, users[number].userfullname, userconnection[i]);
			   long t2 = Getcurrenttime();
			   long msgsendingdelay = t2 - t1;
			   users[i].msgsent(msgsendingdelay);
			   
			   //System.out.println(users[i].cansend());
			   System.out.println("Sending Message for user" + users[i].buddyname + " message count is" + users[i].msgCount);
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
  }*/
	
   public static int getUserIntegerValue(String str) {
		int x=0;
		for(int i=str.length()-1;i>=0;i--)
		{	char c = str.charAt(i);
		  if(Character.isDigit(c)) 
			 { 	x = Character.getNumericValue(c);	
				if(x==0) { System.out.println("Provided UserNameIs:"+str+" User int value should be greater then '0', provided intValueIs:"+x  ); }
			 }
		  else {System.out.println("Kindly provide proper user details like ex:user1 intValue is missing here:>> "+str);}
		  break;
		}
		return x;
	}
	
  public static void sendmessageIM(String sendr, String receivr, String msg) {
	  //=====================================================================================
	  int fromusrInt = (getUserIntegerValue(sendr)-1);
	  int tousrInt = (getUserIntegerValue(receivr)-1);
	  
	  if(users[fromusrInt].connected) {
		  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date now = new Date();
		   String currtime = dateformat.format(now);
		  String msgtext =msg+"from "+users[fromusrInt].Getbuddyname()+"--To--"+users[tousrInt].Getbuddyname()+" "+currtime;
		  System.out.println("Sending msg>> From:"+users[fromusrInt].Getbuddyname()+"  To:"+users[tousrInt].Getbuddyname());
		  
		  try {		//System.out.println("Sending message");
			obj[1].sendMessage(msgtext, users[tousrInt].userfullname, userconnection[fromusrInt]);
		} catch (XMPPException e) { System.out.println("Failed to send message");e.printStackTrace(); }
		  
	  }	  else {System.out.println("Kindly check for Login-connectivity of:"+users[fromusrInt].Getbuddyname());}
	  //===============================================================================	  
  }
  
  /**
   * Connects to a nway\pchat room which is already present using unique roomID and user details
   *after joining to a groupchat this will send message into that group as a user that are provided.
   *
   * @param usrcon a user login connection.
   * @param usrBuddyName this is userName with respect to usrcon.  
   * @param buddyUsrPwd this is password of user with respect to usrcon.
   * @param roomID a unique roomID of Nway or pchat.
   * @param msg this is message that needs be sent in groupchat. 
   * @author SPujar
   */
  public static void sendMsgToExistingGroupChat(String sendr, String roomID, String msg) {
  	
	  int sendrint=(getUserIntegerValue(sendr)-1);
	  XMPPConnection usrcon = userconnection[sendrint];
	  String senderbuddyname =  users[sendrint].Getbuddyname();
	  String senderpwd = userspwd[sendrint];
	  
	  try {		
  		MultiUserChat roomconn = new MultiUserChat(usrcon, roomID);
  		roomconn.getRoom();
  		if( !(roomconn.isJoined()) ) 
  		{System.out.println("Joining room as BuddyName:"+senderbuddyname+" with pwd:"+senderpwd);
  			//roomconn.join("katte115u2", "FaceTime@123");
  			roomconn.join(senderbuddyname,senderpwd); 
  		}
  		roomconn.createMessage(); 
  		roomconn.sendMessage(msg);
  		System.out.println("GroupChat message sent from user:"+senderbuddyname);
  	} catch (XMPPException e) {	e.printStackTrace();	}
      
  }
  public static MultiUserChat joinusrstochatrooms(String sendr, String roomID) throws XMPPException {
	  	
	  int sendrint=(getUserIntegerValue(sendr)-1);
	  XMPPConnection usrcon = userconnection[sendrint];
	  String senderbuddyname =  users[sendrint].Getbuddyname();
	  String senderpwd = userspwd[sendrint];
	  MultiUserChat roomconn = null;
	  //try {		
  		 roomconn = new MultiUserChat(usrcon, roomID);
  		roomconn.getRoom();
  		if( !(roomconn.isJoined()) ) 
  		{System.out.println("BuddyName:"+senderbuddyname+" with pwd:"+senderpwd+" Joining:"+roomID);
  			//roomconn.join("katte115u2", "FaceTime@123");
  			roomconn.join(senderbuddyname,senderpwd); 
  		}
  		roomconn.createMessage(); 
  	//} catch (XMPPException e) {	e.printStackTrace();	}
	  return roomconn;
  }
   
   public void disconnect()
   {
	   for(int i=0;i<numberofusers;i++)
 		{
		   if(users[i].connected)
		   {
			   obj[i].disconnect();
		   }
 		}
   }
   //==============================testData=======================================
 //public String readExcel(String exclFilePath, String sheetName, int rowNo , int cellNo) throws Exception 
 	public static String readExcel( String sheetName, int rowNo , int cellNo) throws Exception 
 	{		
 		//System.out.println("Plz do check excel file path");
 		//--------------------Excepect here sheetName RowNo CellNo if ok FilePath --------------
 		//  ./UtilityFiles/OneToOneTestData.xlsx //D:/AutomationDataCups/OneToOneTestData.xlsx
 		//FileInputStream fis= new FileInputStream(propertiesFileReader("excelFilePath"));
 		FileInputStream fis= new FileInputStream("./configFiles/OneToOneTestData.xlsx");
 		//FileInputStream fis= new FileInputStream(exclFilePath);
 		Workbook wb= WorkbookFactory.create(fis);
 		System.out.println("Reading: "+"RowNo:"+rowNo+" cellNo:"+cellNo);
 		String sx=wb.getSheet(sheetName).getRow(rowNo).getCell(cellNo).getStringCellValue();
 		System.out.println("For RowNo"+rowNo+" CellNo"+cellNo+" Data is:"+sx);
 		return sx;
 	}
 	public static ArrayList<String> excelData(String sheetName,int rows, int cellno) {
		//int rows->no of rows of data which is present in excel,  and cellno
		//int cellno=0;
		ArrayList<String> xlData = new ArrayList<String>();
		String data1 = null;
			for (int rowNo=0;rowNo<rows;rowNo++) //for (int rowNo=0;rowNo<12;rowNo++)
			{  try {
					data1 = readExcel(sheetName, rowNo, cellno);//reading data One-by-One from excel
				    } 
				catch (Exception e) { System.out.println("Catch block of sending message ");e.printStackTrace(); }
			
			xlData.add(data1);
			}
		return xlData;
 	}
 	
 	
  //==============================testData=======================================
   
   public static void main(String[] args) throws XMPPException 
   {
	   
	   //==================================================
	   System.out.println("Reading test data from Excel file ");
	   String sheetName="sheet1";
	   ArrayList<String> xlData1=excelData(sheetName,12,0);
	   Iterator<String> iterator1 = xlData1.iterator();
	   //===================================================
	
	  System.out.println("Start....");
	   int userstartindex,userperthread,numberofthreads;
	   numberofthreads=2;
	   
	   userperthread = 4/numberofthreads;
	   userstartindex=1;
	   XMPPConnection.DEBUG_ENABLED = true;
	   getProperties();
	   createusers();
	   userssignin();
	   
	   String[] dlpPhrases = {"inComingBlock0","OutGoingBlock","ChallengeInComing","DoNotBlkIncoming","DoNotBlkOutGoing","OutGoingChlng","IOBLK"};
	   for(int i=0;i<dlpPhrases.length;i++) {
	   //check unicode
	   String msg1= "DlpTest Message "+dlpPhrases[i];
	   sendmessageIM("user2","user1", msg1+"   TimeT:");
	   }
	   
	   System.out.println("check over");
	   System.exit(0);
	   //check unicode
	   
	   
	   //String roomID="chat426381749717497@conference-2-standaloneclusterb51da.cupsqa115su3.com";
/*	   for(int i=1;i<=3;i++) {
		   for(int j=1;j<=4;j++) {
			   String userName="user"+j;
			   String msg = "NwayGroupChatMsg"+i+" ";
			   String roomID = nwayrooms[(i-1)];
			   //sendMsgToExistingGroupChat(userName, roomID, msg+" :) i:"+i);
			   MultiUserChat roomjn = joinusrstochatrooms(userName, roomID);
			   roomjn.sendMessage("msg:"+msg);
			   roomjn.sendMessage("text:"+msg);
		   }
	   }
*/
	   
	   //Nway
	   /*MultiUserChat nwayusrcon [][] =new MultiUserChat[numberofrooms][numberofusers];
	   for(int nr=0;nr<numberofrooms;nr++) {
		   for(int u=0;u<numberofusers;u++) {
			   String usr = "user"+(u+1);
			   String roomID = nwayrooms[nr];
			   nwayusrcon [nr][u] = joinusrstochatrooms(usr, roomID);
			   System.out.println("User["+u+"]:"+users[u].Getbuddyname()+"connected to room:"+roomID);
			   
		   }
	   }*/
	   //pchat-objs
/*	   MultiUserChat pchatusrcon [][] =new MultiUserChat[numberofrooms][numberofusers];
	   for(int pr=0;pr<numberofrooms;pr++) {
		   for(int u=0;u<numberofusers;u++) {
			   String usr = "user"+(u+1);
			   String roomID = pchatrooms[pr];
			   pchatusrcon [pr][u] = joinusrstochatrooms(usr, roomID);
			   System.out.println("User["+u+"]:"+users[u].Getbuddyname()+"connected to room:"+roomID);
			   
		   }
	   }
	 */  
	   while (iterator1.hasNext()){ 
	   String msg =iterator1.next();
	   System.out.println(msg);
	   try {
	 /*  for(int rm=0;rm<numberofrooms;rm++) {
		   for(int u=0;u<numberofusers;u++) {
			   String curtim = getCurrentTimeStamp();
		   //nwayusrcon[rm][u].sendMessage("NwayMsg:"+msg+"   "+curtim+"    nwayroomNo:"+rm+" usrNo:"+u);
		   //nwayusrcon[rm][u].sendMessage("TxtRoomID:"+nwayrooms[rm]);
			   Thread.sleep(1000);
		   pchatusrcon[rm][u].sendMessage("PchatMsg:"+msg+"   "+curtim+"    PchatroomNo:"+rm+" usrNo:"+u);
		   //pchatusrcon[rm][u].sendMessage("TxtRoomID:"+pchatrooms[rm]);
		   }
		   }
	  */ 
	   String curtim = getCurrentTimeStamp();
	   Thread.sleep(1000);
	   sendmessageIM("user1","user2", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
/*	   sendmessageIM("user1","user3", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user1","user4", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user2","user1", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user2","user3", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user2","user4", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user3","user1", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user3","user2", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user3","user4", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user4","user1", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user4","user2", msg+"   TimeT:"+curtim);
	   Thread.sleep(1000);
	   sendmessageIM("user4","user3", msg+"   TimeT:"+curtim);*/
	   
	   Thread.sleep(6000);
	   }catch(Exception e) {e.printStackTrace();}
   }
	  //sendmessage(userstartindex,userperthread);
	   System.exit(0);
	   }
	   /*A.disconnect();*/
   }