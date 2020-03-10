package com.Cups.Groupchat;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.Client.Cupsutils;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class GroupChatUsers extends Cupsutils{
	
	int msgCount;
	String buddyname;
	String userfullname;
	int smallroom_count;
	int largeroom_count;
	int mediumroom_count;
	long sm_chatroom_msgdelay;
	long med_chatroom_msgdelay;
	long Lar_chatroom_msgdelay;
	long snextmsgsendtime;
	long mnextmsgsendtime;
	long lnextmsgsendtime;
	Boolean connected;
	int smsgcount;
	int mmsgcount;
	int lmsgcount;
	Boolean lock;
	String list_of_srooms_joined[];
	String list_of_mrooms_joined[];
	String list_of_lrooms_joined[];
	MultiUserChat roomconn[];
	
	public GroupChatUsers (String buddyname, String fullname, int sroom_count, int mroomcount,int lroomcount, 
			long sroom_delay, long mroom_delay, long lroom_delay)
		{
			this.buddyname = buddyname;
			this.userfullname = fullname;
			this.smallroom_count = sroom_count;
			this.mediumroom_count = mroomcount;
			this.largeroom_count = lroomcount;
			this.sm_chatroom_msgdelay = sroom_delay;
			this.med_chatroom_msgdelay = mroom_delay;
			this.Lar_chatroom_msgdelay = sroom_delay;
			this.snextmsgsendtime = 0;
			this.mnextmsgsendtime= 0;
			this.lnextmsgsendtime = 0;
		}
	public String getbuddyname()
	{
		return this.buddyname;
	}
	
	public String getfullname()
	{
		return this.userfullname;
	}
	
	public boolean sroomcansend()
	{
		long currtime = Getcurrenttime();
		if ((this.snextmsgsendtime >= currtime) && (this.lock == true))
		{
			return (true);
		}
		else
		{
		return (false);
		}
	}
	
	public boolean mroomcansend()
	{
		long currtime = Getcurrenttime();
		if ((this.mnextmsgsendtime >= currtime) && (this.lock == true))
		{
			return (true);
		}
		else
		{
		return (false);
		}
	}
	
	public boolean lroomcansend()
	{
		long currtime = Getcurrenttime();
		if ((this.lnextmsgsendtime >= currtime) && (this.lock == true))
		{
			return (true);
		}
		else
		{
		return (false);
		}
	}
	
	public void smsgsent(long msgdelay)
	{
		long currtime = Getcurrenttime();
		this.snextmsgsendtime = currtime + this.sm_chatroom_msgdelay- msgdelay;
		this.lock = false;
		this.smsgcount++;
	}
	
	public int getsmsgcount()
	{
		return (this.smsgcount);
	}
	
	public void mmsgsent()
	{
		long currtime = Getcurrenttime();
		this.mnextmsgsendtime = currtime + this.med_chatroom_msgdelay;
		this.lock = false;
		this.mmsgcount++;
	}
	
	public int getmmsgcount()
	{
		return (this.mmsgcount);
	}
	
	public void setroomconn(int i,MultiUserChat conn)
	{
		this.roomconn[i] = conn;
	}
}

