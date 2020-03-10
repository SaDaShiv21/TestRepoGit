package com.actiance.generics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class DatabaseUtils {

//	 Get DBURL and DB Driver
	public static ArrayList<String> getDBUrl(String dbType, String dbHost,String dbName,String dbUsrName,String dbPwd) {
		String dbConnectionUrl = null;
		String dbDriverUrl = null;
		//String dbConnectionUrl1 = null;
		ArrayList<String> db = new ArrayList<String>();
		if (dbType.equals("MSSQL")) {
			dbConnectionUrl = "jdbc:sqlserver://" + dbHost + ";databaseName=" + dbName + ";user=" + dbUsrName + ";password=" + dbPwd;
		  //dbConnectionUrl1 = "jdbc:sqlserver://" + DBHOST1 + ";databaseName=" + DBNAME1 + ";user=" + DBUSERNAME1 + ";password="+ DBPASSWORD;
			dbDriverUrl = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			db.add(dbDriverUrl);
			db.add(dbConnectionUrl);
		} 
		else if (dbType.equals("ORACLE")) {
			dbConnectionUrl = "jdbc:oracle:thin:" + dbUsrName + "//" + dbPwd + "@" + dbHost + ":" + "1521:" + dbName;
			dbDriverUrl = "oracle.jdbc.driver.OracleDriver";
			db.add(dbDriverUrl);
			db.add(dbConnectionUrl);
		} 
		else {	System.err.println("Provided DB-Type is Invalid:"+dbType+ " Expected is like:MSSQL/ORACLE"); }
		return db;
	}
//	Get DB Statement
	public static Connection createConnection(String dbType, String dbHost,String dbName,String dbUsrName,String dbPwd) {
		String dbConnectionUrl = null;
		String dbDriverUrl = null;
		//String dbConnectionUrl1 = null;
		if (dbType.equals("MSSQL")) {
			dbConnectionUrl = "jdbc:sqlserver://" + dbHost + ";databaseName=" + dbName + ";user=" + dbUsrName + ";password=" + dbPwd;
		  //dbConnectionUrl1 = "jdbc:sqlserver://" + DBHOST1 + ";databaseName=" + DBNAME1 + ";user=" + DBUSERNAME1 + ";password="+ DBPASSWORD;
			dbDriverUrl = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		} 
		else if (dbType.equals("ORACLE")) {
			dbConnectionUrl = "jdbc:oracle:thin:" + dbUsrName + "//" + dbPwd + "@" + dbHost + ":" + "1521:" + dbName;
			dbDriverUrl = "oracle.jdbc.driver.OracleDriver";
		} 
		else {	System.err.println("Provided DB-Type is Invalid:"+dbType+ " Expected is like:MSSQL/ORACLE"); System.out.println(); }
	//=========================================================================================================================
		//Statement stmt = null;
		Connection con = null;
		if((dbConnectionUrl != null) && (dbDriverUrl != null))
		{
		  try 
		  {  Class.forName(dbDriverUrl);//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection(dbConnectionUrl);//dbUrlAndDriver.get(1)="jdbc:sqlserver://192.168.119.24:1433;databaseName=IbmTs;user=sa;password=FaceTime@123"
			 if( con != null) {System.out.println("Connected successfully");	}
			 return con;//Actual con stat
		     } catch (Exception e) {	e.printStackTrace(); System.err.println("Error while creating statement");	}
		  	 return con;
		 } else { System.out.println("connection con=null kindly re-check DB connection parameters");return con; }
	}

	public static boolean validateTestcase(Statement stmt,int interactionCount,int messagecount, String imgc,String senderbuddyname,String messageuniquepart,String ActualMessage,String suitetimeStamp,String checkDisclaimer,String[] dlpPhrases) {
		System.out.println("Validation part");
		boolean result=false;
		int timer=1;
		boolean countChangeFlag = false;
		while( timer <= 14 ) {
			if( (interactionCount < getInteractionCount(stmt)) || (messagecount < getMessageCount(stmt)) ) 
			{ 	System.out.println("New Interaction-count: "+getInteractionCount(stmt)+" new Message-count:"+getMessageCount(stmt) );
				countChangeFlag=true; break; }
			try {Thread.sleep(3000);}catch(Exception e) {e.printStackTrace();}//waits for 3sec
			timer++;
		}
		//countChangeFlag=true;//for debug
		if( (countChangeFlag) | (imgc.equalsIgnoreCase("pchatDlpChallengeIn")) | (imgc.equalsIgnoreCase("pchatDlpChallengeOut")) ) {
			//validateIM(Statement stmt1,String senderbuddyname,String messageuniquepart,String ActualMessage,String checkDisclaimer)
			if(imgc.equalsIgnoreCase("IM")) {System.out.println("IM");
			result=validateIM(stmt,senderbuddyname,messageuniquepart,ActualMessage+" "+suitetimeStamp,checkDisclaimer);}
			
			else if(imgc.equalsIgnoreCase("IMDLP")) {}
			
			//validateChatrooms(Statement stmt1,String senderbuddyname,String messageuniquepart,String ActualMessage)
			else if(imgc.equalsIgnoreCase("Pchat")) {System.out.println("Pchat"); 
			result=validateChatrooms(stmt,senderbuddyname,messageuniquepart,ActualMessage+" "+suitetimeStamp);
			}
			else if(imgc.equalsIgnoreCase("Nway")) {System.out.println("Nway"); 
			result=validateChatrooms(stmt,senderbuddyname,messageuniquepart,ActualMessage+" "+suitetimeStamp);}
			
			else if( (imgc.equalsIgnoreCase("ImDlpBlockIn")) | (imgc.equalsIgnoreCase("ImDlpBlockOut")) | (imgc.equalsIgnoreCase("ImDlpBlockInOut")) |
					(imgc.equalsIgnoreCase("ImDlpDoNotBlockInOut")) | (imgc.equalsIgnoreCase("ImDlpChallengeIn")) | (imgc.equalsIgnoreCase("ImDlpChallengeOut")) )
			{
				String DLPtext = null;
				for(int i=0;i<dlpPhrases.length;i++) {
					String[] dlp = dlpPhrases[i].split(",");
					String action = "ImDlp"+dlp[1];
					if(imgc.equalsIgnoreCase(action)) {
						DLPtext = dlp[0];//get DLP-text
						break;
					}
				}
				
				result=validateDLP(stmt,imgc,senderbuddyname,messageuniquepart,ActualMessage,suitetimeStamp,DLPtext);
			}
			//else if( pchat DLP Nway DLP ) {}
			else if( (imgc.equalsIgnoreCase("pchatDlpBlockIn")) | (imgc.equalsIgnoreCase("pchatDlpBlockOut")) | (imgc.equalsIgnoreCase("pchatDlpBlockInOut")) |
					(imgc.equalsIgnoreCase("pchatDlpDoNotBlockInOut")) | (imgc.equalsIgnoreCase("pchatDlpChallengeIn")) | (imgc.equalsIgnoreCase("pchatDlpChallengeOut")) ) {
				String DLPtext = null;
				for(int i=0;i<dlpPhrases.length;i++) {
					String[] dlp = dlpPhrases[i].split(",");
					String action = "pchatDlp"+dlp[1];
					if(imgc.equalsIgnoreCase(action)) {
						DLPtext = dlp[0];//get DLP-text
						break;
					}
				}
				
				result=validateDLP(stmt,imgc,senderbuddyname,messageuniquepart,ActualMessage,suitetimeStamp,DLPtext);
			}
			
			else {System.out.println("Provided conversation type is invalid it should be IM or Pchat or Nway, Here provided value is: "+imgc);}
		}
		return result;
	}
	
	//check count changed>> which handles latency
	public static int getMessageCount(Statement stmt1) {
		String query1 ="select count(*) from Messages";
		//System.out.println("Executing query:"+query1);
		String messages = null;	int msgcount = 0;
		try {
			 ResultSet rs = stmt1.executeQuery(query1);
			 while(rs.next()) { messages= rs.getString(1);}
			}catch (SQLException e) { e.printStackTrace(); }
		
		if(messages.length() != 0){msgcount = Integer.parseInt(messages);}
		return msgcount; 
	}
	public static int getInteractionCount(Statement stmt1) {
		String query1 ="select count(*) from Interactions";
		//System.out.println("Executing query:"+query1);
		String interactions = null;	int intr = 0;
		try {
			 ResultSet rs = stmt1.executeQuery(query1);
			 while(rs.next()) { interactions= rs.getString(1);}
			}catch (SQLException e) { e.printStackTrace(); }
		
		if(interactions.length() != 0){intr = Integer.parseInt(interactions);}
		return intr; 
	}
	
	public static ArrayList<String> fetchInteraction(Statement stmt1,String messageuniquepart) {
		String interIDCompare = null,interID=null;
		String query1 ="select * from Messages where text like '%"+messageuniquepart+"%'";
		System.out.println("Executing query:"+query1);
		ArrayList<String> arr = new ArrayList<String>();
		try {
			 ResultSet rs = stmt1.executeQuery(query1);
			 while(rs.next()) {
				 interID=rs.getString("interID");
				 if( !(interID.equalsIgnoreCase(interIDCompare)) ) {
					 arr.add(interID);
				 }
				 //arr.add(rs.getString("interID"));
				 interIDCompare=interID;
			 }
			}catch (SQLException e) { e.printStackTrace(); }
		return arr;
	}
	
	public static void validateParticipantsListAndJoinLeaveEvents() {
		//need to check possible ways
		//GroupChat >> server view >>only 1 interaction
	}
	
	public static boolean validateChatrooms(Statement stmt1,String senderbuddyname,String messageuniquepart,String ActualMessage) {
		//not checking disclaimer since chatroom might be open for several days
		boolean result=false;
		String dbtext =null;int msgcnt=0;
		 ArrayList<String> ar = fetchInteraction(stmt1,messageuniquepart);
		 Iterator itr = ar.iterator();
		 try {//only 1 interaction since it is server-view
		 while (itr.hasNext()) { 
			 String interID = (String) itr.next();System.out.println("interactionID:"+interID);
			 String userqry = "select * from Messages where interID like '%"+interID+"%' and buddyName like '%"+senderbuddyname+"%'";
	           System.out.println("\t Executing query:"+userqry);
			   ResultSet rs1 = stmt1.executeQuery(userqry);
			   while(rs1.next()){
				   if(rs1.getString("text") != null) {dbtext = rs1.getString("text"); }
				   
				   if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(ActualMessage))  && (rs1.getString("systemText") == null) ) {  
						 System.out.println("\t verified, message:'"+ActualMessage+"' >> interID:"+interID);
						 msgcnt++;
						 }
			   }
		/*	   //validate interID and chatroom  >> select * from Interactions where interID like '%3137%'>>verify chatroom name
			 >>  select * from Interactions where interID like '%interID%'   >>verify chatroom name */			   
		 }
		 }catch(Exception e) {e.printStackTrace();}
		 if(msgcnt == 0) {System.out.println("DB-message is not matching, kindly recheck message unique-content as well sender details from above queries");}
		 if(msgcnt != 0) {result = true;}
		 
		 return result;
	}
	
	public static boolean validateDLP(Statement stmt,String imgc,String senderbuddyname,String messageuniquepart,String ActualMessage,String suitetimeStamp,String dlptext) {
	
		boolean result=false;
		String systemBlockText = "From System Administrator: The previous message was not delivered due to company policy.";
		String systemBlockOutGoingTest = "From System Administrator: The previous message was not sent due to company policy.";
		String challengeSystemtext = "Did you really mean to send this message?  If so, please type the following response: password";
		String pchatDLPBlockText ="From System Administrator: This message has been expurgated due to company policy.        ";
		//Did you really mean to send this message?  If so, please type the following response: password
		//From System Administrator: The previous message was not sent due to company policy.
		//From System Administrator: The previous message was not delivered due to company policy.
		//From System Administrator: This message has been expurgated due to company policy.        
		String[] msg = ActualMessage.split(",");
		String firstMsg = msg[0]+" "+suitetimeStamp ;
		String msgWithDlp = msg[1]+" "+dlptext+" "+suitetimeStamp;
		System.out.println("firstMsg:"+firstMsg);
		System.out.println("msgWithDlp:"+msgWithDlp);
		/*String msg1=msg[0]+" "+suitetimeStamp;
		String msg2=msg[1]+" "+dlptext+" "+suitetimeStamp;*/
		ArrayList<String> arr = fetchInteraction(stmt, firstMsg );
	//IM
		if( (imgc.equalsIgnoreCase("ImDlpBlockIn")) ) {
			boolean check1=false,check2=false,check3=false;
			Iterator itr = arr.iterator();
			 while (itr.hasNext()) {
				 //From System Administrator: The previous message was not delivered due to company policy
				 String interID = (String) itr.next();
				 String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
				 System.out.println("\t DLP query:"+dlpqry);
				 ResultSet rs1;
			 try {rs1 = stmt.executeQuery(dlpqry);
				 while(rs1.next()) {
					 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
						 System.out.println("Incoming block text:"+rs1.getString("text") );
						 if( rs1.getString("msgTypeID").equalsIgnoreCase("100") ) {check1=true;}
						 if( rs1.getString("msgTypeID").equalsIgnoreCase("7") ) {check2=true;}
					 }
					 if( (rs1.getString("systemText") != null) && (rs1.getString("systemText").equalsIgnoreCase(systemBlockText)) ) {check3=true;}
				 }
			 } catch (SQLException e) {e.printStackTrace();}
			} 
			 if ( (check1 | check2) && check3) {
				 System.out.println("Successfully verified DLP incoming block event");
				 result=true;
			 }
			else if(!check3)  {System.out.println("There might be a issue with system block text:"+systemBlockText);}
			else if ( (!check1) | (!check2)) {System.out.println("Kindly re-validate this scenario");}
		}
		
		else if( (imgc.equalsIgnoreCase("ImDlpBlockOut")) ) {
				boolean check1=false,check2=false;
				Iterator itr = arr.iterator();
				 while (itr.hasNext()) {
					 String interID = (String) itr.next();
					 String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
					 System.out.println("\t DLP query:"+dlpqry);
					 ResultSet rs1;
				try {rs1 = stmt.executeQuery(dlpqry);
					 while(rs1.next()) {
						 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
							 System.out.println("Outgoing block text:"+rs1.getString("text") );
							 if( rs1.getString("msgTypeID").equalsIgnoreCase("7") ) {check1=true;}//highlighted
						 }
						 if( (rs1.getString("systemText") != null) && (rs1.getString("systemText").equalsIgnoreCase(systemBlockOutGoingTest)) ) 
						 {check2=true;}
					 }
				  } catch (SQLException e) {e.printStackTrace();}
			   }
			 if ( check1 && check2) { System.out.println("Successfully verified DLP outgoing block event"); result=true; }
			 else if(!check2)  {System.out.println("There might be a issue with system block text:"+systemBlockOutGoingTest);}
			 else if ( (!check1) | (!check2)) {System.out.println("Kindly re-validate this scenario");}
		}
		
		else if( (imgc.equalsIgnoreCase("ImDlpBlockInOut")) ) {
				boolean check1=false,check2=false;
				Iterator itr = arr.iterator();
				 while (itr.hasNext()) {
					 String interID = (String) itr.next();
					 String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
					 System.out.println("\t DLP query:"+dlpqry);
					 ResultSet rs1;
				try {rs1 = stmt.executeQuery(dlpqry);
					 while(rs1.next()) {
						 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
							 System.out.println("Incoming as well outgoing block text:"+rs1.getString("text") );
							 if( rs1.getString("msgTypeID").equalsIgnoreCase("7") ) {check1=true;}//highlighted
						 }
						 if( (rs1.getString("systemText") != null) && (rs1.getString("systemText").equalsIgnoreCase(systemBlockOutGoingTest)) ) 
						 {check2=true;}
					 }
				  } catch (SQLException e) {e.printStackTrace();}
				 }
				 if ( check1 && check2) { System.out.println("Successfully verified DLP incoming as well outgoing block event"); result=true; }
				 else if(!check2)  {System.out.println("There might be a issue with system block text:"+systemBlockOutGoingTest);}
				 else if ( (!check1) | (!check2)) {System.out.println("Kindly re-validate this scenario");}
			}
		
		else if( (imgc.equalsIgnoreCase("ImDlpDoNotBlockInOut")) ) {
				int checkcount=0;
				Iterator itr = arr.iterator();
				 while (itr.hasNext()) {
					 String interID = (String) itr.next();
					 String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
					 System.out.println("\t DLP query:"+dlpqry);
					 ResultSet rs1;
				try {rs1 = stmt.executeQuery(dlpqry);
					 while(rs1.next()) {
						 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) )
						 { checkcount++; }
					 }
				  } catch (SQLException e) {e.printStackTrace();}
				 }
				 System.out.println("checkcount:"+checkcount);
			  if ( checkcount >= 2) { System.out.println("Successfully verified DLP do not block event"); result=true; }
			  else {System.out.println("Kindly re-validate this scenario");}
		}
		
		else if( (imgc.equalsIgnoreCase("ImDlpChallengeIn")) ) {//challengeSystemtext
			int msgcount=0,pwdcount=0,systemMsgCount=0;
			Iterator itr = arr.iterator();
			while (itr.hasNext()) {
			String interID = (String) itr.next();
			String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
			System.out.println("\t DLP query:"+dlpqry);
			ResultSet rs1;
			try {rs1 = stmt.executeQuery(dlpqry);
			while(rs1.next()) {//code
				if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
					 System.out.println("Incoming as well outgoing block text:"+rs1.getString("text") );
					 if( rs1.getString("msgTypeID").equalsIgnoreCase("100") ) {msgcount++;}//highlighted
				 }
				if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase("password")) ) { if( rs1.getString("msgTypeID").equalsIgnoreCase("4") ) {pwdcount++;} }
				
				if( (rs1.getString("systemText") != null) && (rs1.getString("systemText").equalsIgnoreCase(challengeSystemtext)) ) {systemMsgCount++;}	 
								 
			}
			} catch (SQLException e) {e.printStackTrace();}
			}
			if ( (msgcount>1) && (pwdcount>1) && (systemMsgCount>1) ) {System.out.println("successfully verified DLP challange-incoming event");result=true;}
			else if( !(msgcount>1)) {System.out.println("Kindly check audited message for both sender as well receiver");}
			else if( !(systemMsgCount>1)) {System.out.println("reverify system messge for both the users >> "+challengeSystemtext);}
			else if( !(pwdcount>1)) {System.out.println("reverify is entered password got audited for both the users");}
		}
		
		else if( (imgc.equalsIgnoreCase("ImDlpChallengeOut")) ) {
			int msgcount=0,pwdcount=0,systemMsgCount=0;
			Iterator itr = arr.iterator();
			while (itr.hasNext()) {
			String interID = (String) itr.next();
			String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
			System.out.println("\t DLP query:"+dlpqry);
			ResultSet rs1;
			try {rs1 = stmt.executeQuery(dlpqry);
			while(rs1.next()) {
				if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
					 System.out.println("Incoming as well outgoing block text:"+rs1.getString("text") );
					 if( rs1.getString("msgTypeID").equalsIgnoreCase("100") ) {msgcount++;}//highlighted-sender
					 if( rs1.getString("msgTypeID").equalsIgnoreCase("0") ) {msgcount++;}//receiver side
				 }
				if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase("password")) ) { if( rs1.getString("msgTypeID").equalsIgnoreCase("4") ) {pwdcount++;} }
				if( (rs1.getString("systemText") != null) && (rs1.getString("systemText").equalsIgnoreCase(challengeSystemtext)) ) {systemMsgCount++;}					 
							//code	 
			}
			} catch (SQLException e) {e.printStackTrace();}
			}
			if ( (msgcount>1) && (pwdcount>1) && (systemMsgCount>1) ) {System.out.println("successfully verified DLP challange-incoming event");result=true;}
			else if( !(msgcount>1)) {System.out.println("Kindly check audited message for both sender as well receiver");}
			else if( !(systemMsgCount>1)) {System.out.println("reverify system messge for both the users >> "+challengeSystemtext);}
			else if( !(pwdcount>1)) {System.out.println("reverify is entered password got audited for both the users");}
		}
	//IM
		//else if() {}
	//pchat
		else if( (imgc.equalsIgnoreCase("pchatDlpBlockIn")) | (imgc.equalsIgnoreCase("pchatDlpBlockOut")) | (imgc.equalsIgnoreCase("pchatDlpBlockInOut")) ) {
			result=false;
			boolean dlpflagged=false,policymsg=false;
			Iterator itr = arr.iterator();
			while (itr.hasNext()) {
			String interID = (String) itr.next();
			String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
			System.out.println("\t DLP query:"+dlpqry);
			ResultSet rs1;
			try {rs1 = stmt.executeQuery(dlpqry);
			while(rs1.next()) {
				 /*System.out.println("Debug text:"+rs1.getString("text") );
				 System.out.println("Debug flags >>> dlpflagged:"+dlpflagged+" and policymsg:"+policymsg);*/
				 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(pchatDLPBlockText)) ) {policymsg=true;}
				 else if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
					 if( rs1.getString("msgTypeID").equalsIgnoreCase("7") ) {dlpflagged=true;}//flagged
				 }
				else {dlpflagged=false;policymsg=false;}
				
				if(dlpflagged && policymsg) 
				{ result=true; }
			}
			} catch (SQLException e) {e.printStackTrace();}
			}
		}
		else if( (imgc.equalsIgnoreCase("pchatDlpDoNotBlockInOut")) ) {
			result=false;
			Iterator itr = arr.iterator();
			while (itr.hasNext()) {
			String interID = (String) itr.next();
			String dlpqry = "select * from Messages where interID like '%"+interID+"%'";
			System.out.println("\t DLP query:"+dlpqry);
			ResultSet rs1;
			try {rs1 = stmt.executeQuery(dlpqry);
			while(rs1.next()) {
				 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(msgWithDlp)) ) {
					 if( rs1.getString("msgTypeID").equalsIgnoreCase("100") ) {result=true;}//flagged
				 }
			}
			} catch (SQLException e) {e.printStackTrace();}
			}
		}
		else if( (imgc.equalsIgnoreCase("pchatDlpChallengeIn")) | (imgc.equalsIgnoreCase("pchatDlpChallengeOut")) )
		{	System.out.println("For P-chatrooms DLP-Challenge feature is not supported");	result=true;}
	//pchat
		return result;
	}

	public static boolean validateIM(Statement stmt1,String senderbuddyname,String messageuniquepart,String ActualMessage,String checkDisclaimer) {
		String systemmsg ="Notice: All instant messages sent to and from this buddy name will be logged by the Smarsh server and are subject to archival, monitoring, or review and/or disclosure to someone other than the recipient.";
		int msgcnt=0,disccnt=0;	boolean result=false;String dbtext,dbsystemtext;
	 try {	 
		 ArrayList<String> arr = fetchInteraction(stmt1,messageuniquepart);
		 Iterator itr = arr.iterator();
		 while (itr.hasNext()) 
	        {
			 String interID = (String) itr.next(); 
	           System.out.println("interID:"+interID);
	           String userqry = "select * from Messages where interID like '%"+interID+"%' and buddyName like '%"+senderbuddyname+"%'";
	           System.out.println("\t Executing query:"+userqry);
			   ResultSet rs1 = stmt1.executeQuery(userqry);
			   while(rs1.next())
				 {
				   if(rs1.getString("systemText") == null) {
				   System.out.println("ActualMessage:"+ActualMessage);
				   System.out.println("From-Database:"+rs1.getString("text"));
				   }
				 
					 dbsystemtext=null;dbtext=null;
					 if(rs1.getString("text") != null) {dbtext = rs1.getString("text"); }
					 if(rs1.getString("systemText") != null) {dbsystemtext = rs1.getString("systemText").trim(); }
					 //System.out.println("Text:"+dbtext); System.out.println("SystemText:"+dbsystemtext);
					 if( (rs1.getString("text") != null) && (rs1.getString("text").equalsIgnoreCase(ActualMessage))  && (rs1.getString("systemText") == null) ) {  
						 System.out.println("\t verified, message:'"+ActualMessage+"' >> interID:"+interID);
						 msgcnt++;}//message
					 if ( (checkDisclaimer.equalsIgnoreCase("Yes")) && (rs1.getString("systemText")!=null) && dbsystemtext.equalsIgnoreCase(systemmsg) &&  (rs1.getString("text") == null) ) {
						 System.out.println("\t Disclaimer validation successful \t >> interID:"+interID);
						 disccnt++;}//disclaimer
				 }
	        }
		 	if( !(checkDisclaimer.equalsIgnoreCase("Yes")) ){System.out.println("\t Skipped disclaimer-validation part since this might not be the first message from this sender:"+senderbuddyname);}
		 }catch (SQLException e) { e.printStackTrace(); }
		 
		 if( (msgcnt >= 2) && (disccnt >= 1) ) {result = true;}
		 else if( (msgcnt >= 2) && ( !checkDisclaimer.equalsIgnoreCase("Yes") ) ) {result = true;}
		 //else if(msgcnt >= 2 && (checkDisc == false) ) {result = true;}
		 else if(msgcnt==2 && disccnt!=1) {System.out.println("Disclaimer is not matching kindly re-check");}
		 else if( (msgcnt < 2) && disccnt==1) {System.out.println("Actual sent-message and message in DB are not matching to each other");}
		 else {System.out.println("");}
		 //System.out.println("Result:"+result);
		 return result;
	}

	public static void main(String[] args) {
				
		System.out.println("Start...");
		String senderbuddyname="katte115u1";
		String msg = "Normal text msg firstMsg TimeStamp:2020-02-07 17:31:58";
		String checkdisclaimer="yes";//"Yes";
		String systemmsg ="Notice: All instant messages sent to and from this buddy name will be logged by the Smarsh server and are subject to archival, monitoring, or review and/or disclosure to someone other than the recipient.";
		 Connection con1 = createConnection("MSSQL","192.168.117.26","CiscoImPAtmn","sa","Cert@1234");
		 Statement stmt1 = null;
		 try {stmt1 = con1.createStatement();} catch (SQLException e1) {e1.printStackTrace();}
		 
		 //timer
		 	int interactionCount = getInteractionCount(stmt1);
		 	int newinteractionCount=interactionCount;
			int timer=1;
			System.out.println("Interaction count:"+interactionCount);
			System.out.println("start at:"+getCurrentTimeStamp());
			while((timer <= 60) ) {
				if(interactionCount < getInteractionCount(stmt1)) {break;/*Flag to set once interaction count changes else dont execute validation */}
				try {Thread.sleep(3000);}catch(Exception e) {e.printStackTrace();}
				System.out.println("Time:"+timer+" >> "+getCurrentTimeStamp());
				timer++;
			}
			System.out.println("Ended at:"+getCurrentTimeStamp());
			System.out.println("Interaction count:"+getInteractionCount(stmt1));
			
		 //timer
		 
		 int x=getInteractionCount(stmt1);
		 System.out.println("Interaction count"+x);
		
		 boolean check=validateIM(stmt1,senderbuddyname,msg,msg,checkdisclaimer);
		 System.out.println(check);
		 validateChatrooms(stmt1,"sumanth115u2","MessagePchat","TestUniqueMessagePchat");
		 
	}
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}