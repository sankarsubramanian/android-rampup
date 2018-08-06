package com.att.sample;

import com.att.sample.Logger;
import java.util.Map;
import java.util.HashMap;


public class LogManager {
	
	private static LogManager logManager = new LogManager( );
	private static String defaultLogLevel = "warn";
	
	private LogManager() { }
	Map<String, Logger> loggers = new HashMap<String, Logger>();
	
   /* Static 'instance' method */
   public static LogManager getInstance( ) {
      return logManager;
   }
    
   protected Logger createLogger(String module, String level) {
	   
	   Logger logger = loggers.get(module);
	
	   System.out.println("Logger:" + logger);

	   if ( logger == null ) {
		   logger = new Logger(module, level);
		   loggers.put(module, logger);
	   }
	   return logger;
   }
   
   protected static String[] getLogLevels() {

	   String[] logLevels = {"error", "warn", "info", "debug", "trace"};
	    return logLevels;
   }
   
   protected static String getDefaultLogLevel() {
	   return defaultLogLevel;
   }

}
