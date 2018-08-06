package com.att.sample;

//import java.util.logging.Logger;
import com.att.sample.LogManager;
import com.att.sample.Logger;

public class Helloworld {
	
	public static void main(String[] args) {

		LogManager logManager = LogManager.getInstance( );

		Logger logger = logManager.createLogger("Hello", "info");
		int testInt = 6;
		logger.logWarn("Test WARN message " + testInt, null);
		logger.logInfo("Test INFO message " + testInt, null);
		logger.logDebug("Test DEBUG message " + testInt, null);

		try {
            throw new NullPointerException("demo");
        } catch (Exception e) {
        		logger.logError("Test Error message with Trace", e);
        		logger.logTrace("Test TRACE message with Trace", e);
        }

	}

}
