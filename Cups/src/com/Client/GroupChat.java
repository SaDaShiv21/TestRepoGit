package com.Client;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.*;

/**
 * A GroupChat is a conversation that takes place among many users in a virtual
 * room. When joining a group chat, you specify a nickname, which is the identity
 * that other chat room users see.
 *
 * @see XMPPConnection#createGroupChat(String)
 * @author Matt Tucker
 */
public class GroupChat extends Cupsutils {

    private XMPPConnection connection;
    private String room;
    private String nickname = null;
    private boolean joined = false;
    private List<String> participants = new ArrayList<String>();
    

    private PacketFilter presenceFilter;
    private PacketFilter messageFilter;
    private PacketCollector messageCollector;

    /**
     * Creates a new group chat with the specified connection and room name. Note: no
     * information is sent to or received from the server until you attempt to
     * {@link #join(String) join} the chat room. On some server implementations,
     * the room will not be created until the first person joins it.<p>
     *
     *  Most XMPP servers use a sub-domain for the chat service (eg chat.example.com
     * for the XMPP server example.com). You must ensure that the room address you're
     * trying to connect to includes the proper chat sub-domain.
     *
     * @param connection the XMPP connection.
     * @param room the name of the room in the form "roomName@service", where
     *      "service" is the hostname at which the multi-user chat
     *      service is running.
     */
    public GroupChat(XMPPConnection connection, String room) {
        this.connection = connection;
        this.room = room;
        // Create a collector for all incoming messages.
        messageFilter = new AndFilter(new FromContainsFilter(room),
                new PacketTypeFilter(Message.class));
        messageFilter = new AndFilter(messageFilter, new PacketFilter() {
            public boolean accept(Packet packet) {
                Message msg = (Message)packet;
                return msg.getType() == Message.Type.groupchat;
            }
        });
        messageCollector = connection.createPacketCollector(messageFilter);
        // Create a listener for all presence updates.
        presenceFilter = new AndFilter(new FromContainsFilter(room),
                new PacketTypeFilter(Presence.class));
        connection.addPacketListener(new PacketListener() {
            public void processPacket(Packet packet) {
                Presence presence = (Presence)packet;
                String from = presence.getFrom();
                if (presence.getType() == Presence.Type.available) {
                    synchronized (participants) {
                        if (!participants.contains(from)) {
                            participants.add(from);
                        }
                    }
                }
                else if (presence.getType() == Presence.Type.unavailable) {
                    synchronized (participants) {
                        participants.remove(from);
                    }
                }
            }
        }, presenceFilter);
    }

    public GroupChat() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Returns the name of the room this GroupChat object represents.
     *
     * @return the groupchat room name.
     */
    public String getRoom() {
        return room;
    }

    /**
     * Joins the chat room using the specified nickname. If already joined
     * using another nickname, this method will first leave the room and then
     * re-join using the new nickname. The default timeout of 5 seconds for a reply
     * from the group chat server that the join succeeded will be used.
     *
     * @param nickname the nickname to use.
     * @throws XMPPException if an error occurs joining the room. In particular, a
     *      409 error can occur if someone is already in the group chat with the same
     *      nickname.
     */
    public synchronized void join(String nickname) throws XMPPException {
        join(nickname, SmackConfiguration.getPacketReplyTimeout());
    }

    /**
     * Joins the chat room using the specified nickname. If already joined as
     * another nickname, will leave as that name first before joining under the new
     * name.
     *
     * @param nickname the nickname to use.
     * @param timeout the number of milleseconds to wait for a reply from the
     *      group chat that joining the room succeeded.
     * @throws XMPPException if an error occurs joining the room. In particular, a
     *      409 error can occur if someone is already in the group chat with the same
     *      nickname.
     */
    public synchronized void join(String nickname, long timeout) throws XMPPException {
        if (nickname == null || nickname.equals("")) {
            throw new IllegalArgumentException("Nickname must not be null or blank.");
        }
        // If we've already joined the room, leave it before joining under a new
        // nickname.
        if (joined) {
            leave();
        }
        // We join a room by sending a presence packet where the "to"
        // field is in the form "roomName@service/nickname"
        Presence joinPresence = new Presence(Presence.Type.available);
        joinPresence.setTo(room + "/" + nickname);
        // Wait for a presence packet back from the server.
        PacketFilter responseFilter = new AndFilter(
                new FromContainsFilter(room + "/" + nickname),
                new PacketTypeFilter(Presence.class));
        PacketCollector response = connection.createPacketCollector(responseFilter);
        // Send join packet.
        connection.sendPacket(joinPresence);
        // Wait up to a certain number of seconds for a reply.
        Presence presence = (Presence)response.nextResult(timeout);
        response.cancel();
        if (presence == null) {
            throw new XMPPException("No response from server.");
        }
        else if (presence.getError() != null) {
            throw new XMPPException(presence.getError());
        }
        this.nickname = nickname;
        joined = true;
    }

    /**
     * Returns true if currently in the group chat (after calling the {@link
     * #join(String)} method.
     *
     * @return true if currently in the group chat room.
     */
    public boolean isJoined() {
        return joined;
    }

    /**
     * Leave the chat room.
     */
    public synchronized void leave() {
        // If not joined already, do nothing.
        if (!joined) {
            return;
        }
        // We leave a room by sending a presence packet where the "to"
        // field is in the form "roomName@service/nickname"
        Presence leavePresence = new Presence(Presence.Type.unavailable);
        leavePresence.setTo(room + "/" + nickname);
        connection.sendPacket(leavePresence);
        // Reset participant information.
        participants = new ArrayList<String>();
        nickname = null;
        joined = false;
    }

    /**
     * Returns the nickname that was used to join the room, or <tt>null if not
     * currently joined.
     *
     * @return the nickname currently being used.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the number of participants in the group chat.<p>
     *
     * Note: this value will only be accurate after joining the group chat, and
     * may fluctuate over time. If you query this value directly after joining the
     * group chat it may not be accurate, as it takes a certain amount of time for
     * the server to send all presence packets to this client.
     *
     * @return the number of participants in the group chat.
     */
    public int getParticipantCount() {
        synchronized (participants) {
            return participants.size();
        }
    }

    /**
     * Returns an Iterator (of Strings) for the list of fully qualified participants
     * in the group chat. For example, "conference@chat.jivesoftware.com/SomeUser".
     * Typically, a client would only display the nickname of the participant. To
     * get the nickname from the fully qualified name, use the
     * {@link org.jivesoftware.smack.util.StringUtils#parseResource(String)} method.
     * Note: this value will only be accurate after joining the group chat, and may
     * fluctuate over time.
     *
     * @return an Iterator for the participants in the group chat.
     */
    public Iterator<String> getParticipants() {
        synchronized (participants) {
            return Collections.unmodifiableList(new ArrayList<String>(participants)).iterator();
        }
    }

    /**
     * Adds a packet listener that will be notified of any new Presence packets
     * sent to the group chat. Using a listener is a suitable way to know when the list
     * of participants should be re-loaded due to any changes.
     *
     * @param listener a packet listener that will be notified of any presence packets
     *      sent to the group chat.
     */
    public void addParticipantListener(PacketListener listener) {
        connection.addPacketListener(listener, presenceFilter);
    }

    /**
     * Sends a message to the chat room.
     *
     * @param text the text of the message to send.
     * @throws XMPPException if sending the message fails.
     */
    public void sendMessage(String text) throws XMPPException {
        Message message = new Message(room, Message.Type.groupchat);
        message.setBody(text);
        connection.sendPacket(message);
    }

    /**
     * Creates a new Message to send to the chat room.
     *
     * @return a new Message addressed to the chat room.
     */
    public Message createMessage() {
        return new Message(room, Message.Type.groupchat);
    }

    /**
     * Sends a Message to the chat room.
     *
     * @param message the message.
     * @throws XMPPException if sending the message fails.
     */
    public void sendMessage(Message message) throws XMPPException {
        connection.sendPacket(message);
    }

     /**
     * Polls for and returns the next message, or <tt>null if there isn't
     * a message immediately available. This method provides significantly different
     * functionalty than the {@link #nextMessage()} method since it's non-blocking.
     * In other words, the method call will always return immediately, whereas the
     * nextMessage method will return only when a message is available (or after
     * a specific timeout).
     *
     * @return the next message if one is immediately available and
     *      <tt>null otherwise.
     */
    public Message pollMessage() {
        return (Message)messageCollector.pollResult();
    }

    /**
     * Returns the next available message in the chat. The method call will block
     * (not return) until a message is available.
     *
     * @return the next message.
     */
    public Message nextMessage() {
        return (Message)messageCollector.nextResult();
    }

    /**
     * Returns the next available message in the chat. The method call will block
     * (not return) until a packet is available or the <tt>timeout has elapased.
     * If the timeout elapses without a result, <tt>null will be returned.
     *
     * @param timeout the maximum amount of time to wait for the next message.
     * @return the next message, or <tt>null if the timeout elapses without a
     *      message becoming available.
     */
    public Message nextMessage(long timeout) {
        return (Message)messageCollector.nextResult(timeout);
    }

    /**
     * Adds a packet listener that will be notified of any new messages in the
     * group chat. Only "group chat" messages addressed to this group chat will
     * be delivered to the listener. If you wish to listen for other packets
     * that may be associated with this group chat, you should register a
     * PacketListener directly with the XMPPConnection with the appropriate
     * PacketListener.
     *
     * @param listener a packet listener.
     */
    public void addMessageListener(PacketListener listener) {
        connection.addPacketListener(listener, messageFilter);
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
     * @author Sadashiv Pujar
     */
    public static void sendMsgToExistingGroupChat(XMPPConnection usrcon, String usrBuddyName,String buddyUsrPwd, String roomID, String msg) {
    	try {		
    		MultiUserChat roomconn = new MultiUserChat(usrcon, roomID);
    		roomconn.getRoom();
    		if( !(roomconn.isJoined()) ) 
    		{System.out.println("Joining room as BuddyName:"+usrBuddyName+" with pwd:"+buddyUsrPwd);
    			//roomconn.join("katte115u2", "FaceTime@123");
    			roomconn.join(usrBuddyName,buddyUsrPwd); 
    		}
    		roomconn.createMessage(); 
    		roomconn.sendMessage(msg);
    		System.out.println("GroupChat message sent from user:"+usrBuddyName);
    	} catch (XMPPException e) {	e.printStackTrace();	}
        
    }
    

    public void finalize() throws Throwable {
        super.finalize();
        try {
            if (messageCollector != null) {
                messageCollector.cancel();
            }
        }
        catch (Exception e) {}
    }
    public static void main(String[] args) {
    	System.out.println("Start... GroupChat");
    	XMPPConnection connection;
    	XMPPConnection usr1con = null;
    	XMPPConnection.DEBUG_ENABLED = true;
    	
    	String room_ID= "autopchat3147262025220252@conference-2-standaloneclusterb51da.cupsqa115su3.com";
		String currtime = getCurrentTimeStamp();
		String msg ="%E3%81%93%E3%82%93%E3%81%AB%E3%81%A1%E3%81%AF%D9%85%D8%B1%D8%AD%D8%A8%D8%A7%E4%BD%A0%E5%A5%BD%E3%81%8A%E6%97%A9%E3%81%86%E3%81%94%E3%81%96%E3%81%84%E3%81%BE%E3%81%99\r\n" + 
				"" + " >>Time "+currtime;
	  //Login
		SadashivLogin1 s1 =new SadashivLogin1();
		try 
		{	
			usr1con=s1.login("katte115u2", "FaceTime@123", "192.168.125.181", "cupsqa115su3.com");
		} catch (XMPPException e) {	e.printStackTrace(); }
		
		try {
			/*ConnectionConfiguration config = new ConnectionConfiguration("192.168.125.181", 5222, "cupsqa115su3.com");
			connection = new XMPPConnection(config);
			connection.connect();
			connection.login("katte115u3", "FaceTime@123");
			String room_name = "room@conference-2-standalonecluster94c8e.qacupten.com";
			MultiUserChat roomconn = new MultiUserChat(connection, room_name);*/
			//GroupChat roomconn = new GroupChat(connection,room_name);
			
			//String room_name = "newtestpchatrooom27991968919689@conference-2-standaloneclusterb51da.cupsqa115su3.com";
			
			//working>>>>
		/*	MultiUserChat roomconn = new MultiUserChat(usr1con, room_ID);			
			roomconn.getRoom();
			roomconn.join("katte115u2","FaceTime@123");
			roomconn.createMessage(); 
			roomconn.sendMessage(msg);
			System.out.println("message sent");
		*/  //working>>>>
			} catch (Exception e) {		e.printStackTrace();	}
		
		try {//working>>
			for(int i=1;i<2;i++) {
			sendMsgToExistingGroupChat(usr1con, "katte115u2", "FaceTime@123", room_ID, msg+" :) i:"+i);
			}
		}catch (Exception e) { e.printStackTrace();}
				
		System.exit(0);
	}
}