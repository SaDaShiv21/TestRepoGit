package com.Client;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class SadashivLogin1 implements MessageListener {

	XMPPConnection connection;

	public XMPPConnection login(String userName, String password, String pbSbIP, String domain)
			throws XMPPException {
		//"String domain" should be associated to DC in etc\host
		System.out.println(String.format("Login with >> UN:%1$s, Pwd:%2$s, PbSbIP:%3$s, domain:%4$s", userName,password,pbSbIP,domain));
		
		ConnectionConfiguration config = new ConnectionConfiguration(pbSbIP, 5222, domain);
		//ConnectionConfiguration config = new ConnectionConfiguration("192.168.125.181", 5222, "cupsqa115su3.com");
		connection = new XMPPConnection(config);

		connection.connect();
		connection.login(userName, password,"jabber_");
		connection.getChatManager();connection.getConnectionID();connection.getUser();connection.getAccountManager();
		System.out.println(String.format("GetChatManager:%1$s, GetConID:%2$s, GetUser:%3$s, GetAccMAnager:%4$s", 
				connection.getChatManager(), connection.getConnectionID(), connection.getUser(), connection.getAccountManager() ));
		return this.connection;
	}

	public void sendMessage(String message, String toUser,XMPPConnection connection1) throws XMPPException 
	{
		System.out.println("This ref to this class obj ref:"+this);
		Chat chat = connection1.getChatManager().createChat(toUser, this);
		System.out.println("Chat"+chat);
		chat.sendMessage(message);
		System.out.println("Message:"+message+" sent to:"+toUser);
	}

	public void displayBuddyList() {
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();

		System.out.println("\n\n" + entries.size() + " buddy(ies):");
		for (RosterEntry r : entries) {
			System.out.println(r.getUser());
		}
	}

	public void disconnect() {
		connection.disconnect();
	}

	public void processMessage(Chat chat, Message message) {
		if (message.getType() == Message.Type.chat)

			System.out.println(chat.getParticipant() + " says: "
					+ message.getBody());
		else
			System.out.println("test");
	}

	public static void main(String[] args) {
		
		System.out.println("Working but need lot more improvements");
		System.out.println("Start...");
		
		XMPPConnection usr1con = null;
		//XMPPConnection.DEBUG_ENABLED = true;
		SadashivLogin1 s1 =new SadashivLogin1();
		try {
			usr1con=s1.login("katte115u2", "FaceTime@123", "192.168.125.181", "cupsqa115su3.com");
		} catch (XMPPException e) {	e.printStackTrace(); }
		System.out.println("send msg");
		try {
			s1.sendMessage("TestMsg123456789", "katte115u1@cupsqa115su3.com", usr1con);
		} catch (XMPPException e) { e.printStackTrace(); }
		//usr1con.disconnect();
		System.exit(0);
	}
	/*
	 * public static void main(String args[]) throws XMPPException, IOException
	 * { // declare variables JabberSmackAPI c = new JabberSmackAPI();
	 * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 * String msg;
	 * 
	 * 
	 * // turn on the enhanced debugger XMPPConnection.DEBUG_ENABLED = false;
	 * 
	 * 
	 * // Enter your login information here c.login("cupuser1", "facetime");
	 * 
	 * c.displayBuddyList();
	 * 
	 * System.out.println("-----");
	 * 
	 * // System.out.println(
	 * "Who do you want to talk to? - Type contacts full email address:");
	 * //String talkTo = br.readLine(); String talkTo =
	 * "cupuser2@pheonix.local"; System.out.println("-----");
	 * System.out.println("All messages will be sent to " + talkTo);
	 * System.out.println("Enter your message in the console:");
	 * System.out.println("-----\n");
	 * 
	 * while( !(msg=br.readLine()).equals("bye")) { c.sendMessage(msg, talkTo);
	 * }
	 * 
	 * c.disconnect(); System.exit(0); }
	 */

}