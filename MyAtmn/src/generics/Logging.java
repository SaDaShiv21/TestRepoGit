package generics;

import org.apache.log4j.Logger;

public class Logging 
{
	private static final Logger log = Logger.getLogger(Logging.class);
	//logs every thing in logs/automation.log and details in log-props/log4j.props
	
	public static void main(String[] args)
	{
		log.debug("Sample line to test append");
	}

}
