package com.Client;

public class CupsUsers extends Cupsutils {
	String buddyname;
	String userfullname;
	long nextMsgSendTime;
	long messageDelay;
	int msgCount;
	boolean lock;
	boolean connected;
	
	public CupsUsers () {	}
	
	public CupsUsers (String buddy,String fullname,long delay)
	{
		this.buddyname= buddy;
		this.userfullname=fullname;
		this.messageDelay=delay;
		this.msgCount=0;
		this.lock = false;
		this.nextMsgSendTime=0;
		
	}
	public String Getbuddyname()
	{
		return buddyname;
	}
	
	public String Getuserfullname()
	{
		return(this.userfullname);
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