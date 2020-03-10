package com.Client;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Cupslogin {

	private XMPPConnection login(String userName, String password, String server, String port, String service) throws XMPPException
    {
	/*	System.setProperty("http.proxyHost", "127.0.0.1");
	    System.setProperty("https.proxyHost", "127.0.0.1");
	    System.setProperty("http.proxyPort", "8888");
	    System.setProperty("https.proxyPort", "8888");
	*/	
	System.out.println("start 1 2 3");
	ConnectionConfiguration config = new ConnectionConfiguration(server,5222);
    //ConnectionConfiguration config = new ConnectionConfiguration(server,5222,service);
    //config.setSecurityMode(SecurityMode.enabled);
    config.setSecurityMode(SecurityMode.disabled);
    //config.setSASLAuthenticationEnabled(true);
    config.setSASLAuthenticationEnabled(false);

    System.out.println("KeystorePath:"+config.getKeystorePath()+" keyStoreType:"+config.getKeystoreType()+" SASLauthEnabled:"+config.isSASLAuthenticationEnabled());
    System.out.println(" TrustStorePwd:"+config.getTruststorePassword()+",   TrustStorePath:"+config.getTruststorePath());
    
    XMPPConnection connection = new XMPPConnection(config);
   connection.connect();
   System.out.println(connection.getConnectionID()+" "+ connection.getServiceName()+" "+connection.getUser()+" "+connection.getHost());
   System.out.println(connection.getHost()+" "+connection.getSASLAuthentication());
   System.out.println("Connected to server:"+server+">> On Port:5222"+" >> Service:"+service);
   System.out.println("Now trying to login as:"+userName+", Pwd:"+password);
  // connection.loginAnonymously();
   connection.login(userName, password);
   
   System.out.println("Sada OK stage1");
   return connection;
   }


public static void main(String[] args) throws XMPPException {
	Cupslogin A= new  Cupslogin();
	//XMPPConnection conn1=A.login("cupsuser1@pheonix.Local", "facetime", "CUPSQA105PB", "5222", "qacupten.com");
	//XMPPConnection conn1=A.login("Administrator", "FaceTime@123", "192.168.124.120", "5222", "cupce105115.com");//sada115su6u1@cupqa115su6.com
  //--XMPPConnection conn1=A.login("katte105u1@cupce105115.com", "FaceTime@123", "CUPSCE105PB.cupce105115.com", "5222", "cupce105115.com");
    
	XMPPConnection conn1=A.login("katte115u4@cupsqa115su3.com", "FaceTime@123", "192.168.125.181", "5222", "CUPSQA115SU3SB.cupsqa115su3.co");
	//XMPPConnection conn1=A.login("sada115su6u1@cupqa115su6.com", "facetime", "192.168.123.41", "5222", "cupqa115SU6.com");
	
	
	//XMPPConnection conn1=A.login("katte105u1@cupce105115.com", "FaceTime@123", "CUPSCE105PB.cupce105115.com", "50781", "cupce105115.com");
	conn1.connect();
	
}
}