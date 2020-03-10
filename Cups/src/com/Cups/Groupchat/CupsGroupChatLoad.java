package com.Cups.Groupchat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.Client.CupsUsers;
import com.Client.GroupChat;
import com.Client.JabberSmackAPI;



public class CupsGroupChatLoad extends GroupChatUsers {

	public CupsGroupChatLoad(String buddyname, String fullname,
			int sroom_count, int mroolcount, int lroomcount, long sroom_delay,
			long mroom_delay, long lroom_delay) {
		super(buddyname, fullname, sroom_count, mroolcount, lroomcount, sroom_delay,
				mroom_delay, lroom_delay);
		// TODO Auto-generated constructor stub
	}
	
	public static int numberofusers;
	public static int messagerate;
	public static String buddynameprefix;
	public static int userstartindex;
	public static String server;
	public static String domain;
	public static String roomname_prefix;
	public static int sroom_count;
	public static int mroom_count;
	public static int lroom_count;
	public static int sroom_delay;
	public static int mroom_delay;
	public static int lroom_delay;
	public static int sroom_msgperusrpersec;
	public static int mroom_msgperusrpersec;
	public static int lroom_msgperusrpersec;
	public static int sroom_msgdelayperuser;
	public static int mroom_msgdelayperuser;
	public static int lroom_msgdelayperuser;
	public static int sroom_numberofusers;
	public static int sroomsize;
	public static int mroom_numberofusers;
	public static int lroom_numberofusers;
	public static int mroomsize;
	public static int lroomsize;
	public static int sroom_msgrate;
	public static int mroom_msgrate;
	public static int lroom_msgrate;
	public static int duration;
	public static String msgtext;
	
	
	public static GroupChatUsers users[];
	public static void getGroupchatProperties()
	
	{
		Properties prop = new Properties();
		
		try
		{
			
		prop.load(new FileInputStream("D:\\Data\\Eclips-Projects\\Cups\\src\\com\\Client\\chatconfig.properties"));	
			
		numberofusers = new Integer(prop.getProperty("no_of_users"));
		buddynameprefix = new String(prop.getProperty("buddynameprefix"));
		userstartindex = new Integer(prop.getProperty("userstartindex"));
		sroom_msgrate = new Integer(prop.getProperty("sroom_msgrate"));
		sroomsize = new Integer(prop.getProperty("sroomsize"));
		
		server = new String(prop.getProperty("server"));
		domain = new String(prop.getProperty("domain"));
		roomname_prefix = new String(prop.getProperty("roomname_prefix"));
		sroom_count = new Integer(prop.getProperty("sroom_count"));
		mroom_count = new Integer(prop.getProperty("mroom_count"));
		lroom_count = new Integer(prop.getProperty("lroom_count"));
		duration = new Integer(prop.getProperty("duration"));
		msgtext = new String(prop.getProperty("msgtext"));
		sroom_numberofusers = (sroomsize * sroom_count);
		mroom_numberofusers = (mroomsize * mroom_count);
		lroom_numberofusers = (lroomsize * lroom_count);
		
		sroom_msgperusrpersec = (sroom_msgrate/sroom_numberofusers);
		/*mroom_msgperusrpersec = (mroom_msgrate/mroom_numberofusers);
		lroom_msgperusrpersec = (lroom_msgrate/lroom_numberofusers); */
		
		sroom_msgdelayperuser = (1000/sroom_msgperusrpersec);
		/*mroom_msgdelayperuser = 1000/mroom_msgperusrpersec;
		lroom_msgdelayperuser = 1000/lroom_msgperusrpersec;*/
		
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//create users
    public static void createusers()
    {
    	users = new GroupChatUsers[numberofusers];
    	for(int i=0;i<numberofusers;i++)
    	{
    	  String bname = new Integer(userstartindex++).toString(); 
    	  String buddyname= buddynameprefix.concat(bname);
    	  String fullname = buddyname.concat("@").concat(domain);
    	//  CupsUsers user = new CupsUsers(buddyname, fullname, messageDelayPerUser); 
    	  
    	  users[i]=new GroupChatUsers(buddyname, fullname,sroom_count, mroom_count,lroom_count, sroom_msgdelayperuser,mroom_msgdelayperuser,lroom_msgdelayperuser); 
    	  System.out.println(users[i].getbuddyname());
    	}
    }
   
    static XMPPConnection[ ] userconnection;
	static JabberSmackAPI[ ] obj;
	static JabberSmackAPI[ ] userconnection1;
	
	
	
   public static void userssignin() throws XMPPException
   {
	   userconnection = new XMPPConnection[numberofusers];
	 //  userconnection1 = new XMPPConnection[numberofusers];
	   obj = new JabberSmackAPI[numberofusers];
	 
	  for(int i=0;i<numberofusers;i++)
   		{
		  obj[i] = new JabberSmackAPI(); 
		  String userName = users[i].getbuddyname();
		  System.out.println( users[i].buddyname + "User Signed on Successfull");
  		  // userconnection[i]= obj1.login(userName,"facetime");
		 userconnection[i]= obj[i].login(userName,"FaceTime@123","192.168.125.181","cupsqa115su3.com");
		   if(userconnection[i] != null)
  			{
  			   users[i].connected=true;
  			   try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  			}
		   
   		}
   }
   
public static void usersjoinroom()
{
	
	int users_per_room = sroom_count/numberofusers;
	int i=userstartindex;
	
	int user_room_count=0;
	for (int room=0;room<=sroom_count; room++)
	{	
		String room_name = "roomname_prefix" + room + "@"+ "conference-2-standalonecluster94c8e.qacupten.com";
		int countusers=0;	
	while (countusers<users_per_room)
	{
		if (i==numberofusers)
		{
			i=userstartindex;
			user_room_count++;
		}
		MultiUserChat roomconn = new MultiUserChat(userconnection[i], room_name);
		users[i].setroomconn(user_room_count, roomconn);
		
		try {
			roomconn.getRoom();
			roomconn.join(users[i].userfullname,"facetime");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		countusers++;
	}
	}
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
		   if(users[i].connected)
		   {
			   boolean cansend = users[i].sroomcansend();
			if(cansend==true)
			{
				int no_of_rooms= users[i].roomconn.length;
			  Random randomGenerator = new Random();
			  int range = no_of_rooms - 0 + 1;
			  int randomNum =  randomGenerator.nextInt(range) + 0;
			   SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   Date now = new Date();
			   currtime = dateformat.format(now);
			   msgtext = msgtext + "-" + currtime;
			   users[i].roomconn[randomNum].createMessage();
			   long t1 = Getcurrenttime();
			   users[i].roomconn[randomNum].sendMessage("message text" + currtime);
			   long t2 = Getcurrenttime();
			   long msgsendingdelay = t2 - t1;
			   users[i].smsgsent(msgsendingdelay);
			   
			   //System.out.println(users[i].cansend());
			   System.out.println("Sending Message for user" + users[i].buddyname + "message count is" + users[i].smsgcount);
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


	public static void main(String[] args) throws XMPPException 
	   {
		
		  
		   int userstartindex,userperthread,numberofthreads;
		   numberofthreads=2;
		   
		   userperthread = 4/numberofthreads;
		   userstartindex=1;
		   XMPPConnection.DEBUG_ENABLED = true;
		   getGroupchatProperties();
		   createusers();
		   userssignin();
		   usersjoinroom();
		   sendmessage(userstartindex, numberofusers);
		   //sendmessage(userstartindex,userperthread);
		   System.exit(0);
		   }
	
}
		

