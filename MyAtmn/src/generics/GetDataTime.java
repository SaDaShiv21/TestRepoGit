package generics;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class GetDataTime 
{
	static int timer=0,tmr=0,tgap=3;
	public static void main(String[] args)
	{
		 DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mma");
	        String s1 = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
	        LocalTime lt = LocalTime.parse(s1);
	        System.out.println("current timeis =>"+s1);
	        //adding xyz minutes to current time
	        timer=tmr;//initial timer
	        tmr=timer+tgap;//time gap between the importers
	        System.out.println("adding  "+tmr+"  minutes to current time and setting the exporter");
	        String xt = df.format(lt.plusMinutes(tmr));
	        
	        
	       //SimpleDateFormat format = new SimpleDateFormat("yyyyMMMddhhmmss");
	        SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy");
	       String x = format.format(new Date());
	       System.out.println("format : "+x);
			//return format.format(new Date());
	}

}
