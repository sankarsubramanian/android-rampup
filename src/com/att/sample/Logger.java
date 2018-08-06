package com.att.sample;

import java.util.Date;
import com.att.sample.LogManager;

public class Logger {
	
	private String module, level;
	private String[] logLevels; 
	
	public Logger(String module, String level) {
		
		this.module = module;
		this.level = level;
		this.logLevels = LogManager.getLogLevels();
	}
	
	public void logError(String message, Throwable trace) {
		
		if (this.indexOf("error") <= this.indexOf(this.level)) {
			this.log("error", message, trace);
		}
	}
	
	public void logWarn(String message, Throwable trace) {
		
		if (this.indexOf("warn") <= this.indexOf(this.level)) {
			this.log("warn", message, trace);
		}
	}
	
	public void logInfo(String message, Throwable trace) {
		
		if (this.indexOf("info") <= this.indexOf(this.level)) {
			this.log("info", message, trace);
		}
	}
	
	public void logDebug(String message, Throwable trace) {
		
		if (this.indexOf("debug") <= this.indexOf(this.level)) {
			this.log("debug", message, trace);
		}
	}
	
	public void logTrace(String message, Throwable trace) {
		
		if (this.indexOf("trace") <= this.indexOf(this.level)) {
			this.log("trace", message, trace);
		}
	}
		
	private void log( String level, String message, Throwable trace) {
		
		String[] logLevelValues = {"[ERROR]", "[WARNING]", "[INFO]", "[DEBUG]", "[TRACE]"};
		
		String msgLogLevel = logLevelValues[this.indexOf(level)];
		
		System.out.println(msgLogLevel + " " + (new Date().toGMTString() ) + " " + message);
		
		if ( trace != null) {
			trace.printStackTrace();
		}
		
	}
	
    private int indexOf(String logLevel) {

        int index = -1;

        for ( int level = 0; level < this.logLevels.length; level++ ) {
            if ( logLevels[level] == logLevel ) {
                index = level;
                break;
            }
        }
        return index;
    }
}
