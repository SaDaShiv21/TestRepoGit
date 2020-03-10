package com.actiance.tests;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.Client.JabberSmackAPI;
import com.actiance.generics.CSVdata;

public class CupsTestCases extends CupsTestBase {
	static int lc = 1;
	static MultiUserChat[][] pchatusrcon ;
	MultiUserChat[][] nwayusrcon ;
	
	//String systemmsg ="Notice: All instant messages sent to and from this buddy name will be logged by the Smarsh server and are subject to archival, monitoring, or review and/or disclosure to someone other than the recipient.";
	
	@Test(dataProvider = "map", invocationCount = 1)
	public void executeTest(CSVdata testObject) {
		System.out.println();
		System.out.println("************************"+testObject.getTestCaseNo()+" start ************************");
		System.out.println(testObject.getTestCaseNo()+" From:"+testObject.getFromUser()+" To:"+testObject.getToUserOrRoom()+" Type:"+testObject.getImOrGc()+" JoinLeave:"+testObject.getCheckJoinLeave());
		
		String chatType = testObject.getImOrGc();
		String message = testObject.getMessageText();
		System.out.println("MessageText:"+message);
		
		System.out.println("SqlQuery:"+testObject.getSqlQuery());
		int messageCount = dbutils.getMessageCount(stmt);
		int interactionCount=dbutils.getInteractionCount(stmt);
		
		System.out.println("Before executing testcase Interaction count: "+interactionCount+" message-count:"+messageCount);
	
		if(lc==1) {//login	// join all users to chatroomz	
		try {
				userssignin();	
				pchatusrcon = joinAllusrstoAllPchatrooms();
//				nwayusrcon = joinAllusrstoAllNwaychatrooms();
			}
//			catch (XMPPException e) { e.printStackTrace(); }
			catch (Exception e) {e.printStackTrace();}
			System.out.println("All users got login and joined to chatrroms");
			lc++;
		}
		
		//check
/*		for(int r=0;r<3;r++) {
			for(int u=0;u<4;u++) {
				System.out.println("Room:"+r+" User:"+u);
				try {pchatusrcon[r][u].sendMessage("Message to Room-"+r+" from user-"+u);Thread.sleep(3000);
				}catch(XMPPException e){	e.printStackTrace();}catch(Exception e){e.printStackTrace();}
			}
		} */
		//check
		String sender = users[(getIntegerValue(testObject.getFromUser() )-1)].getBuddyname();
		int u1=(getIntegerValue(testObject.getFromUser() )-1);
		int u2rm=(getIntegerValue(testObject.getToUserOrRoom())-1);
		
		if(chatType.equalsIgnoreCase("IM")) {
			try {	//System.out.println("From:"+users[u1].getBuddyname());System.out.println("To:"+users[u2rm].getBuddyname());
				System.out.println("Type:"+testObject.getImOrGc()+" Messaging from:"+users[u1].getBuddyname()+" to "+users[u2rm].getBuddyname());
				sendmessageIM(testObject.getFromUser(),testObject.getToUserOrRoom(),message+" SuiteTime:"+suitecurrenttime);
				Thread.sleep(1800);
				}catch(Exception e) {e.printStackTrace();}
			}
		else if(chatType.equalsIgnoreCase("Pchat")) 
		{
			System.out.println("From:"+users[u1].getBuddyname()+" to pchatroom:"+pchatrooms[u2rm]);
			System.out.println("SendingText:"+message+" SuiteTime:"+suitecurrenttime);
			try {pchatusrcon[u2rm][u1].sendMessage(message+" SuiteTime:"+suitecurrenttime);
					Thread.sleep(1800);
			}catch(XMPPException e){	e.printStackTrace();}catch(Exception e){e.printStackTrace();}	
		}
		else if(chatType.equalsIgnoreCase("Nway")) {
			System.out.println("From:"+users[u1].getBuddyname()+" Nwaychatroom:"+nwayrooms[u2rm]);
			try {nwayusrcon[u2rm][u1].sendMessage(message+" SuiteTime:"+suitecurrenttime);
			Thread.sleep(1800);
			}catch(XMPPException e){	e.printStackTrace();}catch(Exception e){e.printStackTrace();}
			
		}
		//IM DLP
		else if( (chatType.equalsIgnoreCase("ImDlpBlockIn")) | (chatType.equalsIgnoreCase("ImDlpBlockOut")) | (chatType.equalsIgnoreCase("ImDlpBlockInOut")) |
				(chatType.equalsIgnoreCase("ImDlpDoNotBlockInOut")) | (chatType.equalsIgnoreCase("ImDlpChallengeIn")) | (chatType.equalsIgnoreCase("ImDlpChallengeOut")) ) 
		{ sendDLPmessage(chatType,testObject.getFromUser(),testObject.getToUserOrRoom(),testObject.getMessageText(),dlpPhrases,"SuiteTime:"+suitecurrenttime); }
		//IM DLP
		
		//pchat dlp
		else if( (chatType.equalsIgnoreCase("pchatDlpBlockIn")) | (chatType.equalsIgnoreCase("pchatDlpBlockOut")) | (chatType.equalsIgnoreCase("pchatDlpBlockInOut")) |
				(chatType.equalsIgnoreCase("pchatDlpDoNotBlockInOut")) | (chatType.equalsIgnoreCase("pchatDlpChallengeIn")) | (chatType.equalsIgnoreCase("pchatDlpChallengeOut")) ) 
		{ sendDLPmessage(chatType,testObject.getFromUser(),testObject.getToUserOrRoom(),testObject.getMessageText(),dlpPhrases,"SuiteTime:"+suitecurrenttime); 
				System.out.println(testObject.getTestCaseNo()+" : "+chatType);
		}
		//pchat dlp
		
		else { System.out.println("invalide parameter ImOrGc():"+testObject.getImOrGc());}

		//executionHelper(testObject.getImOrGc(),testObject.getFromUser(),testObject.getToUserOrRoom(),testObject.getMessageText(),testObject.getCheckJoinLeave(),pchatusrcon,nwayusrcon);
		try {Thread.sleep(1200);}catch (Exception e) {e.printStackTrace();}
		System.out.println("DB verification of "+testObject.getTestCaseNo());
		boolean result = dbutils.validateTestcase(stmt, interactionCount,messageCount, testObject.getImOrGc(), sender, testObject.getSqlQuery(), message, "SuiteTime:"+suitecurrenttime, testObject.geCheckDisclaimer(),dlpPhrases );
				
		System.out.println("Result is " + result);
		Assert.assertEquals(true, result);			
		System.out.println("************************"+testObject.getTestCaseNo()+" end ************************");

	}
	//=========================================x=========================================
	
	public static void sendDLPmessage(String imgc,String frmusr,String tousr,String message,String[] dlpPhrases,String SuitetimeStamp) {
		int u1=(getIntegerValue(frmusr)-1);
		int u2rm=(getIntegerValue(tousr)-1);
		
		System.out.println("IMDLP Msg:"+message);
		String[] msg = message.split(",");//first send 0th(get interIDs) then send 1st msg(DLP)
		String DLPtext = null;
		boolean actionflag=false;
		for(int i=0;i<dlpPhrases.length;i++) {
			String[] dlp = dlpPhrases[i].split(",");
			String actionIM = "ImDlp"+dlp[1];
			String actionPchat = "pchatDlp"+dlp[1];
			String actionNway = "nwayDlp"+dlp[1];
			if( (imgc.equalsIgnoreCase(actionIM)) | (imgc.equalsIgnoreCase(actionPchat)) | (imgc.equalsIgnoreCase(actionNway)) ) {
				DLPtext = dlp[0];//get dlp text to be sent
				actionflag=true;
				break;
			}
		}
		String msg1=msg[0]+" "+SuitetimeStamp;
		String msg2=msg[1]+" "+DLPtext+" "+SuitetimeStamp;
		System.out.println("msg1:"+msg1);
		System.out.println("msg2:"+msg2);
		if(actionflag) {
			
			if( (imgc.equalsIgnoreCase("ImDlpBlockIn")) | (imgc.equalsIgnoreCase("ImDlpBlockOut")) | 
					(imgc.equalsIgnoreCase("ImDlpBlockInOut")) | (imgc.equalsIgnoreCase("ImDlpDoNotBlockInOut")) ) {
				sendmessageIM(frmusr,tousr,msg1);//message to get Interactions
				try {Thread.sleep(1200);}catch (Exception e) {e.printStackTrace();}
				sendmessageIM(frmusr,tousr,msg2);//message with DLP
				try {Thread.sleep(1200);}catch (Exception e) {e.printStackTrace();}
				
				//System.out.println("IMDLP >> ImDlpBlockIn"+" DLPmessage:"+DLPtext);
			}
			else if( (imgc.equalsIgnoreCase("ImDlpChallengeIn")) | (imgc.equalsIgnoreCase("ImDlpChallengeOut")) ) {
				System.out.println("Send:password");
				sendmessageIM(frmusr,tousr,msg1);//message to get Interactions
				try {Thread.sleep(1200);}catch (Exception e) {e.printStackTrace();}
				sendmessageIM(frmusr,tousr,msg2);//message with DLP(Challenge)
				try {Thread.sleep(1800);}catch (Exception e) {e.printStackTrace();}
				sendmessageIM(frmusr,tousr,"password");//send password for challenge phrase
				try {Thread.sleep(1200);}catch (Exception e) {e.printStackTrace();}
				//System.out.println("IMDLP >> ImDlpChallengeIn"+" DLPmessage:"+DLPtext);
				}
			
			else if( (imgc.equalsIgnoreCase("pchatDlpBlockIn")) | (imgc.equalsIgnoreCase("pchatDlpBlockOut")) | (imgc.equalsIgnoreCase("pchatDlpBlockInOut")) |
					(imgc.equalsIgnoreCase("pchatDlpDoNotBlockInOut")) ) {
				try {pchatusrcon[u2rm][u1].sendMessage(msg1);}catch(XMPPException e){e.printStackTrace();}	
				try {Thread.sleep(1800);}catch (Exception e) {e.printStackTrace();}
				try {pchatusrcon[u2rm][u1].sendMessage(msg2);}catch(XMPPException e){e.printStackTrace();}	
				try {Thread.sleep(1800);}catch (Exception e) {e.printStackTrace();}
			}
			else if( (imgc.equalsIgnoreCase("pchatDlpChallengeIn")) | (imgc.equalsIgnoreCase("pchatDlpChallengeOut")) ) {
				System.out.println("DLP challenge feature is not supported with cups-pchat");			
			}
			
			else {System.out.println("Kindly provide valid DLP action");}
		}
		else {System.out.println("Provided DLP action is invalid:"+imgc);
		System.out.println("Valid DLP actions are:ImDlpBlockIn,ImDlpBlockOut,ImDlpBlockInOut,ImDlpDoNotBlockInOut,ImDlpChallengeIn,ImDlpChallengeOut");}
	}
	
	
	public static void executionHelper(String imgc,String frmusr,String tousr,String mssg,String joinleave,MultiUserChat[][] pchatusrcon,MultiUserChat[][] nwayusrcon) {
		System.out.println("check1hlpr");
		String fromUser =frmusr;
		String touserroom = tousr;
		String msg = mssg;
		String curtim = getCurrentTimeStamp();
		String joinleave1=joinleave;
		
		int u1=(getIntegerValue(fromUser)-1);
		int u2rm=(getIntegerValue(touserroom)-1);
		int toprintu1=getIntegerValue(fromUser);
		int toprintu2rm=getIntegerValue(touserroom);
		
		
		if(imgc.equalsIgnoreCase("IM")) {
			try {	//System.out.println("From:"+users[u1].getBuddyname());System.out.println("To:"+users[u2rm].getBuddyname());
				System.out.println("Type:"+imgc+" Messaging from:"+users[u1].getBuddyname()+" to "+users[u2rm].getBuddyname());
				sendmessageIM(fromUser,touserroom,msg);
				Thread.sleep(3000);
				}catch(Exception e) {e.printStackTrace();}	
	/*		if( !(users[u1].getBuddyname().equalsIgnoreCase("katte115u2")) ) {
				sendmessageIM(fromUser,"user2",msg);
			}
			*/
			}
		else if(imgc.equalsIgnoreCase("Pchat")) 
		{
			System.out.println("sending from user"+toprintu1+" to pchatroom"+toprintu2rm);
			System.out.println("From:"+users[u1].getBuddyname()+" pchatroom:"+pchatrooms[u2rm]);
			try {pchatusrcon[u1][u2rm].sendMessage("PchatMsg:"+msg);
					Thread.sleep(3000);
			}catch(XMPPException e){	e.printStackTrace();}catch(Exception e){e.printStackTrace();}	
		}
		else if(imgc.equalsIgnoreCase("Nway")) {
			System.out.println("sending from user"+toprintu1+" to Nwaychatroom"+toprintu2rm);
			System.out.println("From:"+users[u1].getBuddyname()+" Nwaychatroom:"+nwayrooms[u2rm]);
		/*	try {nwayusrcon[u1][u2rm].sendMessage("PchatMsg:"+msg);
			Thread.sleep(3000);
			}catch(XMPPException e){	e.printStackTrace();}catch(Exception e){e.printStackTrace();}
			*/
		}
		else if(joinleave1.equalsIgnoreCase("Join") || joinleave1.equalsIgnoreCase("Leave")) {
			if(joinleave1.equalsIgnoreCase("Join")) 
			{System.out.println("Join event should be generated ");
				System.out.println("From:"+users[u1].getBuddyname()+" pchatroom:"+pchatrooms[u2rm]);}
			else if(joinleave1.equalsIgnoreCase("Leave")) 
			{System.out.println("Leave event should be generated ");
				System.out.println("From:"+users[u1].getBuddyname()+" pchatroom:"+pchatrooms[u2rm]);}
			else {System.out.println("kidly provide proper details as join/leave, provided is:"+joinleave1);}
		}
		else if(imgc.equalsIgnoreCase("Gc")) {//System.out.println("This is GroupChat case:"+imgc);
		try {
		for(int rm=0;rm<numberofrooms;rm++) {
			 for(int u=0;u<numberofusers;u++) {
				
				nwayusrcon[rm][u].sendMessage("NwayMsg:"+msg+"   "+curtim+"    nwayroomNo:"+rm+" usrNo:"+u);;
				Thread.sleep(1000);
				pchatusrcon[rm][u].sendMessage("PchatMsg:"+msg+"   "+curtim+"    PchatroomNo:"+rm+" usrNo:"+u);
				Thread.sleep(1000);
			 } }
		}catch(Exception e) {e.printStackTrace();}
		}
		else { System.out.println("invalide parameter ImOrGc():"+imgc);}
		
		try {Thread.sleep(3000);}catch (Exception e) {e.printStackTrace();}
	}
	
    static XMPPConnection[ ] userconnection;
	static JabberSmackAPI[ ] obj;
	
	public static void userssignin() throws XMPPException
	   {
		XMPPConnection.DEBUG_ENABLED = true;
		   userconnection = new XMPPConnection[numberofusers];
		   obj = new JabberSmackAPI[numberofusers];
		 
		  for(int i=0;i<numberofusers;i++)
	   		{
			  obj[i] = new JabberSmackAPI(); 
			  String userName = users[i].getBuddyname();
			  //String password = userspwd[i];
			  String password = users[i].getUserpwd();
			  String pbsbip = users[i].getUserpbsb();
			  //String pbSbIP = pbsbip;
			  System.out.println("\nLogin with>> un:"+userName+" pwd:"+password+" pbsb:"+pbsbip+" domain:"+domainname);
			  //userconnection[i]= obj[i].login("katte115u1","FaceTime@123", "192.168.125.181", "cupsqa115su3.com");
			  userconnection[i]= obj[i].login(userName,password, pbsbip, domainname);
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
	
	public static void sendmessageIM(String sendr, String receivr, String msg) {
			  int fromusrInt = (getIntegerValue(sendr)-1);
			  int tousrInt = (getIntegerValue(receivr)-1);

			  if(users[fromusrInt].connected) {
				  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				  Date now = new Date();
				   String currtime = dateformat.format(now);
				   String msgtext =msg;
				  //String msgtext =msg+"from "+users[fromusrInt].getBuddyname()+"--To--"+users[tousrInt].getBuddyname()+" "+currtime;
				  System.out.println("Sending msg>> From:"+users[fromusrInt].getBuddyname()+"  To:"+users[tousrInt].getBuddyname());
				  
				  try {		//System.out.println("Sending message");
					   obj[1].sendMessage(msgtext, users[tousrInt].userfullname, userconnection[fromusrInt]);
				  	  } catch (XMPPException e) { System.out.println("Failed to send message");e.printStackTrace(); }
			  }	  else {System.out.println("Kindly check for Login-connectivity of:"+users[fromusrInt].getBuddyname());}
		  }
	//public static int getUserIntegerValue(String str)
	
	public static MultiUserChat[][] joinAllusrstoAllNwaychatrooms() {
		System.out.println("Join nway");
		 //Nway
		   MultiUserChat nwayusrcon [][] =new MultiUserChat[numberofrooms][numberofusers];
		   for(int r=0;r<numberofrooms;r++) {
			   for(int u=0;u<numberofusers;u++) {
				   String roomID = nwayrooms[r];
				   String usr = "user"+(u+1);
				   try {
					nwayusrcon [r][u] = joinusrstochatrooms(usr, roomID); } catch (XMPPException e) {e.printStackTrace();	}
				   System.out.println("User["+u+"]:"+users[u].getBuddyname()+"connected to room:"+roomID);
				   
			   }
		   }
		   return nwayusrcon;
	}
	
	public static MultiUserChat[][] joinAllusrstoAllPchatrooms() {
		System.out.println("join pchat");
		//pchat-objs
		   MultiUserChat pchatusrcon [][] =new MultiUserChat[numberofrooms][numberofusers];
		   for(int rm=0;rm<numberofrooms;rm++) {
			   for(int u=0;u<numberofusers;u++) {
				   String usr = "user"+(u+1);
				   String roomID = pchatrooms[rm];
				   try {pchatusrcon [rm][u] = joinusrstochatrooms(usr, roomID); } catch (XMPPException e) {e.printStackTrace();}
				   System.out.println("User["+u+"]:"+users[u].getBuddyname()+"connected to room:"+roomID);
			   }
		   }
		   return pchatusrcon;
	}
	
	public static MultiUserChat joinusrstochatrooms(String usr, String roomID) throws XMPPException {
		  	
			  int usrint=(getIntegerValue(usr)-1);
			  XMPPConnection usrcon = userconnection[usrint];
			  String userrbuddyname =  users[usrint].getBuddyname();
			  String userpwd = userspwd[usrint];
			  MultiUserChat roomconn = null;
			  //try {		
		  		 roomconn = new MultiUserChat(usrcon, roomID);
		  		roomconn.getRoom();
		  		if( !(roomconn.isJoined()) ) 
		  		{System.out.println("BuddyName:"+userrbuddyname+" with pwd:"+userpwd+" Joining:"+roomID);
		  			//roomconn.join("katte115u2", "FaceTime@123");
		  			roomconn.join(userrbuddyname,userpwd);
		  		}
		  		roomconn.createMessage(); 
		  	//} catch (XMPPException e) {	e.printStackTrace();	}
			  return roomconn;
		  }
	public static int getIntegerValue(String str) 
	  {		int x=0;
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
	
}