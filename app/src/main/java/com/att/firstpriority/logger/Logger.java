package com.att.firstpriority.logger;

import android.util.Log;


public class Logger {

    private int logLevel = Log.WARN;
    private static final String[] logLevels = getLogLevels();

    public Logger(String logLevel) {

        int logIndex = this.indexOf(logLevel);
        if (logIndex != -1) {
            this.logLevel = logIndex;
        }
    }

    public void setLogLevel(String logLevel) {

        int logIndex = this.indexOf(logLevel);
        if (logIndex != -1)  {
            this.logLevel = logIndex;
        }
    }

    public void verbose(String tag, String message) {

        this.verbose(tag, message, null);
    }

    public void debug(String tag, String message) {

        this.debug(tag, message, null);
    }

    public void info(String tag, String message) {

        this.info(tag, message, null);
    }

    public void warn(String tag, String message) {

        this.warn(tag, message, null);
    }

    public void error(String tag, String message) {

        this.error(tag, message, null);
    }

    public void verbose(String tag, String message, Throwable trace) {

        if (Log.VERBOSE >= this.logLevel ) {
            Log.v(tag, message + '\n' + Log.getStackTraceString(trace));
        }
    }

    public void debug(String tag, String message, Throwable trace) {

        if (Log.DEBUG >= this.logLevel ) {
            Log.d(tag, message + '\n' + Log.getStackTraceString(trace));
        }
    }

    public void info(String tag, String message, Throwable trace) {

        if (Log.INFO >= this.logLevel ) {
            Log.i(tag, message + '\n' + Log.getStackTraceString(trace));
        }
    }

    public void warn(String tag, String message, Throwable trace) {

        if (Log.WARN >= this.logLevel ) {
            Log.w(tag, message + '\n' + Log.getStackTraceString(trace));
        }
    }

    public void error(String tag, String message, Throwable trace) {

        if (Log.ERROR >= this.logLevel ) {
            Log.e(tag, message + '\n' + Log.getStackTraceString(trace));
        }
    }


    private static String[] getLogLevels() {

        String[] logLevels = { "verbose", "debug", "info", "warn", "error" };
        return logLevels;
    }

    private int indexOf(String logLevel) {

        int index = -1,
            verboseLogLevel = 2; // Minimum loglevel index is 2 from "android.util.Log" package

        for ( int level = 0; level < logLevels.length; level++ ) {
            if ( logLevels[level] == logLevel ) {
                index = level + verboseLogLevel;
                break;
            }
        }
        return index;
    }
}
