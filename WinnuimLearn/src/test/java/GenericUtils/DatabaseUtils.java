package GenericUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
	String dbType;
    String dbHost;
    String dbName;
    String dbUsername;
    String dbPassword;
    String dbPort;
    String dbUrl;
    String dbDriverClass;
    Connection con;
    ResultSet rSetPart;
    static Statement stmt;

    ResultSet rSet = null;

    public DatabaseUtils() 
    {
       getStatement();
    }

    public Statement getStatement() 
	{
       /*dbType = System.getProperty("dbType");
       dbHost = System.getProperty("dbHost");
       dbName = System.getProperty("dbName");
       dbUsername = System.getProperty("dbUsername");
       dbPassword = System.getProperty("dbPassword");
       dbPort = System.getProperty("dbPort");*/
    	
    	dbType = "MSSQL";
        dbHost = "192.168.117.26";
        dbName = "71867_Jira";
        dbUsername = "sa";
        dbPassword = "Cert@1234";
        dbPort = "1433";
              
     System.out.println("dbtype is : " + dbType);
                try {
                            if (dbType.equalsIgnoreCase("Oracle")) 
							{  dbUrl = "jdbc:oracle:thin:@" + dbHost + ":"+ dbPort + ":" + dbName + ";user =" + dbUsername + ";password ="+ dbPassword;
                               System.out.println("Oracle DB url: "+dbUrl);
								dbDriverClass = "oracle.jdbc.driver.OracleDriver";
                            } 
							else if (dbType.equalsIgnoreCase("MSSQL")) 
							{   dbUrl = "jdbc:sqlserver://" + dbHost + ":" + dbPort + ";databaseName =" + dbName + ";user =" +dbUsername+ ";password =" + dbPassword;
								System.out.println("MSSQL DB url: "+dbUrl);
								dbDriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                            }
                             Class.forName(dbDriverClass);
                             con = DriverManager.getConnection(dbUrl);
                             if (con != null) {System.out.println("Connected to DB server, DB IP:"+dbHost );}
                             stmt = con.createStatement();
                             if (stmt != null) { System.out.println("Statement Created"); }
                    } catch (ClassNotFoundException e) {
                                    System.err.println("Driver Class not Found, Please check Classpath and Jar File for DB Connection");
                                    e.printStackTrace();
                    } catch (SQLException se) {
                                    System.err.println("Please check DB Host name and Db User and Password");
                                    se.printStackTrace();
                    }
     return stmt;
    }

    public String getMessageAuditedCups(String msg) {
                    String returnVal = null;
                    Object objVal;
                    String checkDataQuery = "select * from Messages where text like '%"+msg+"%'";
                   try {
                	   	  rSet = stmt.executeQuery(checkDataQuery);
                          rSet.next();
                          returnVal = rSet.getString("text");
                          objVal=rSet.getObject("text");
                         // returnVal = rSet.getString("value");
                       } catch (Exception e) { e.printStackTrace();  }
                   
                    return returnVal;
    }
}