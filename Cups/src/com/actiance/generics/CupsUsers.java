package com.actiance.generics;

import com.Client.Cupsutils;

public class CupsUsers extends Cupsutils {
	public String buddyname;
	public String userfullname;
	public long nextMsgSendTime;
	public long messageDelay;
	public int msgCount;
	public boolean lock;
	public boolean connected;
	
	public CupsUsers () {	}
	//Newly created
	String userPwd;
	String userpbsb;
	public CupsUsers (String buddy,String fullname,String userPwd,String userpbsb,long delay)
	{
		this.buddyname= buddy;
		//setBuddyname(buddy);
		this.userfullname=fullname;
		//setUserfullname(fullname);
		this.userPwd = userPwd;
		//setUserpwd(userPwd);
		this.userpbsb = userpbsb;
		//setUserpbsb(userpbsb);
		
		this.messageDelay=delay;
		this.msgCount=0;
		this.lock = false;
		this.nextMsgSendTime=0;
	}
	public CupsUsers (String buddy,String fullname,long delay)
	{
		this.buddyname= buddy;
		this.userfullname=fullname;
		this.messageDelay=delay;
		this.msgCount=0;
		this.lock = false;
		this.nextMsgSendTime=0;
	}
	
	public String getBuddyname()
	{
		return buddyname;
	}
	public void setBuddyname(String buddy) {
		this.buddyname= buddy;
	}
	
	public String getUserfullname()
	{
		return(this.userfullname);
	}
	public void setUserfullname(String fullname)
	{
		this.userfullname=fullname;
	}
	
	public String getUserpwd()
	{
		return(this.userPwd);
	}
	public void setUserpwd(String userPwd)
	{
		this.userPwd = userPwd;
	}
	
	public String getUserpbsb()
	{
		return(this.userpbsb);
	}
	public void setUserpbsb(String userpbsb)
	{
		this.userpbsb=userpbsb;
	}
	
	
	public boolean cansend()
	{
		long currtime = Getcurrenttime();
		if((currtime >= this.nextMsgSendTime) && (this.lock==false))
		{
			this.lock = true;
			return(true);
		}
		else 
		{
			return(false);
		}
	}
	
	public void msgsent(long msgdelay)
	{
		long currtime= Getcurrenttime();
		this.nextMsgSendTime = currtime + this.messageDelay - msgdelay;
		this.msgCount++;
		this.lock=false;
	}
	
	public int getMsgSent()
	{
		return(this.msgCount);
	}
}