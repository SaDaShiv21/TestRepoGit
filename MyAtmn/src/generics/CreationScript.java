package generics;

import org.testng.Assert;
import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class CreationScript 
{
	String dynamicEmpDN = null;
	String dynamicEmpGivenName = null;
	static String groupDN9;
	static Properties prop = new Properties();
	static Attributes userAttributes;
	static HashMap<String, String> empDNsT = new HashMap<String, String>();
	static DirContext dirCon = null;

	public static void main(String[] args) throws NamingException 
	{	
		String constValue = getUniqueConstantValue();
		
		if(dirCon != null)
		{	dirCon.close();	}
		//HardCoded
		dirCon = getDirContext(getLDAPURL("192.168.115.200", "389"), "Simple", "cn=root", "facetime");
		//dirCon = getDirContext(getLDAPURL("192.168.116.133", "389"), "Simple", "cn=root", "FaceTime@123");
		System.out.println("Connected to LDAP using jndi");
				
	//--------Creating 10KUers
		//cn=atmnrealm10kusers,cn=ibmpolicies
		final String DNTatmn="cn=atmnrealm10kusers,cn=ibmpolicies";
		//final String DNT = "cn=qa_realm,cn=ibmpolicies";
		//creation method------------------///-------------------
		HashMap<String, String> empDNT1 = createTivoliUsers(dirCon, 36000 , 3 , DNTatmn,"");
				
	//Create Group for 10K-users
		//String GroupName="AutomationGroup10KTivoliUsers";
		String GroupName="AutomationGroup1KTivoliUsers"+"IBM";
		groupDN9 = "CN="+GroupName+","+DNTatmn;
	try {	createGroupTivoli(groupDN9, dirCon, GroupName, empDNsT);
			System.out.println("Done created 10K user and GroupName :"+GroupName);
		} catch (NamingException e) 
		{ //e.printStackTrace();
		System.out.println("Please check in Tivoli Group might already present");}
			
}
	
public static HashMap<String, String> createTivoliUsers(DirContext dirCon,int baseCount , int  tCount, String DNT1 ,String grpName)
	{
		String[] dynamicUserID = new String[10002];
		int xid=0;
		String constValue = getUniqueConstantValue();
		final String fNameT = "SadaFN"+constValue;
		final String lNameT = "LastNTivoliUser";
		String DNT = DNT1;
		final String Domain = "actiance.com";
		for(int i=baseCount; i<baseCount+tCount; i++)
		 {
		   try	{	//public Attributes getUserAttributesForTivoliDynamicUser(int userNumber, String domainName)
					userAttributes = getUserAttributesForTivoliDynamicUser(i+1, Domain,fNameT,lNameT);
					//System.out.println("getUserAttributesForTivoli Executed");
					System.out.println("intUserID : "+i);
					String userID=fNameT+"Sada"+(i+1);
					System.out.println("Creating a user with uid : "+userID);
					System.out.println("DNT: "+DNT);
					if(!createUserWithoutPasswordModification("uid="+userID+","+DNT, dirCon, userAttributes))
					{	System.out.println("Tivoli Dynamic user not created");
						Assert.fail("Could not create a subcontext");	
					}
					dynamicUserID[xid]=userID;
					xid++;
					System.out.println("Created Dynamic user: "+"uid="+fNameT+lNameT+(i+1));
					empDNsT.put(userAttributes.get("uid").get().toString(), "uid="+fNameT+lNameT+(i+1)+","+DNT);
					//Add the employee to the created group
					//util.modifyAttributes(groupDN8, dirCon, "Add", "member", "CN="+employeeDN+(i+1)+","+prop.getProperty("BaseDNSub"));
					System.out.println();
				}	catch(Exception e)
				{   System.out.println("Please check in Tivoli User might already present");
					e.printStackTrace();
				}	
			}
		return empDNsT;
	}
		
	public static String getUniqueConstantValue(){
//		SimpleDateFormat format = new SimpleDateFormat("yyMMMddhhmmss");
		SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy");
		return format.format(new Date());
	}
//---------------------
	public static String getLDAPURL(String host, String port){
		return "ldap://"+host.trim()+":"+port;
	}
//-----------
	public static DirContext getDirContext(String ldapURL, String authType, String userName, String password) 
			throws NamingException{
		Hashtable<String, String> environment = new Hashtable<String, String>();
		
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, ldapURL);
		environment.put(Context.SECURITY_AUTHENTICATION, authType);
		environment.put(Context.SECURITY_PRINCIPAL, userName);
		environment.put(Context.SECURITY_CREDENTIALS, password);
		//environment.put(Context.REFERRAL,"follow");
		//environment.put(Context.SECURITY_PROTOCOL,"ssl");
		
		DirContext dirCon = new InitialDirContext(environment);
		//System.out.println("Created DirContext connection");
		return dirCon;
	}
//--------------
	public static Attributes getUserAttributesForTivoliDynamicUser(
			int userNumber, String domainName,String fname,String lname)
	{
		Attributes userAttr = new BasicAttributes(true);
		
		BasicAttribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("person");
		objectClass.add("organizationalPerson");
		objectClass.add("inetOrgPerson");
		
		BasicAttribute firstName = new BasicAttribute("cn", fname+userNumber);
		BasicAttribute lastName = new BasicAttribute("sn", lname+userNumber);
		BasicAttribute givenName = new BasicAttribute("givenName", fname+userNumber+" "+lname+userNumber);
		BasicAttribute uid = new BasicAttribute("uid", fname+lname+userNumber);
		BasicAttribute userPassword = new BasicAttribute("userpassword", "facetime");
		BasicAttribute mail = new BasicAttribute("mail", fname+lname+userNumber+"@"+domainName);
	
		userAttr.put(objectClass);
		userAttr.put(firstName);
		userAttr.put(lastName);
		userAttr.put(givenName);
		userAttr.put(uid);
		userAttr.put(userPassword);
		userAttr.put(mail);
		
		return userAttr;
	}
	//------------------
		public static boolean createUserWithoutPasswordModification(String DN, DirContext dirCon, Attributes attr){
		try{
			System.out.println("DN: "+DN);
			dirCon.createSubcontext(DN, attr);
			System.out.println("Executed createSubcontext method-created");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//-------------------------
		public static boolean createGroupTivoli(String DN, DirContext dirCon, String groupName, HashMap<String, String> memberDNs) throws NamingException{
		BasicAttribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("groupOfNames");  
		BasicAttribute cn = new BasicAttribute("cn", groupName);
		
		BasicAttribute member = new BasicAttribute("member");
		for(String memberDN : memberDNs.keySet()){
			member.add(memberDNs.get(memberDN));
		}
		Attributes attr = new BasicAttributes(true);
		attr.put(objectClass);
		attr.put(cn);
		attr.put(member);
		
		dirCon.createSubcontext(DN, attr);
		System.out.println("Created Group");
		return true;
	}
	//--------------------

}
