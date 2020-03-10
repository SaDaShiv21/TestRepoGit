package generics;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DBdata
{
    public static void main(String[] args)
    //public void checkDB()
    {  int cnt=0,cnt1=0;   
        try
        { 
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        String connectionUrl = "jdbc:sqlserver://192.168.119.24:1433;" + 
                       "databaseName=IbmTs;user=sa;password=FaceTime@123"; 
                    Connection con = DriverManager.getConnection(connectionUrl);
        if (con != null)
        {
            System.out.println("Connected");
        }
         ArrayList list=new ArrayList();
         ArrayList list1=new ArrayList();
            //get all interaction procesessed --select DISTINCT interID from ExportHistory where exportStateID =1  and check count of those
//            String queryString="Select * from Employees";  "select count(*) from ExportHistory where exportstateID=1"

            System.out.println("Getting count of exported group interactions......");
            Statement stmt1=con.createStatement();
            String qry="select count(*) from interactions where groupIDs='a9b7ba70783b617e9998dc4dd82eb3c5'";
            ResultSet rs1=stmt1.executeQuery(qry);
            while(rs1.next())
            {            list1.add(rs1.getInt(1));
            			cnt1++;
            }
            System.out.println("Interactions in group exported:"+list1);
         //give number of exporter executed
    for(int i=0;i<1;i++)
     {
        Statement stmt=con.createStatement();
        String queryString="select * from ExportHistory where exportStateID =1 and exporterNum="+i+"";
        System.out.println("Query Executed : "+queryString);
        ResultSet rs=stmt.executeQuery(queryString);
        cnt=0;
        while(rs.next())
            {
            list.add(rs.getInt(1));
            cnt++;
            }
        System.out.println("Total number of interactions Exported for Exporter"+i+" : "+cnt);
        //System.out.println(list);
      }
        con.close(); 
        } catch(Exception e){ System.out.println(e);}
    } 
}