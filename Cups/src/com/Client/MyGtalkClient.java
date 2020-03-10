package com.Client;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class MyGtalkClient implements MessageListener {
	
	public static void main(String[] args) throws XMPPException {
		// TODO Auto-generated method stub
			ConnectionConfiguration config = new ConnectionConfiguration("CUPSQA105PB",5222,"qacupten.com");
			XMPPConnection connection = new XMPPConnection(config);
			
			connection.connect();
			connection.login("cupuser1", "facetime");
	
			Chat chat = connection.getChatManager().createChat("cupsuser3@pheonix.Local",new MyGtalkClient());
			chat.sendMessage("Hi master"); /* Send the message  */
			connection.disconnect() ; //Disconnect
	}
public void processMessage (Chat chat,Message message) /*Callback method from MessageListener interface . It is called when a message is received */
{
	System.out.println("Received message: " + message.getBody());
}


}