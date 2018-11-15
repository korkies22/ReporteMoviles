/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.LoggerFactory;
import java.lang.reflect.Constructor;

public static enum LoggerFactory.LogType {
    ANDROID("android.util.Log", "com.j256.ormlite.android.AndroidLog"),
    SLF4J("org.slf4j.LoggerFactory", "com.j256.ormlite.logger.Slf4jLoggingLog"),
    COMMONS_LOGGING("org.apache.commons.logging.LogFactory", "com.j256.ormlite.logger.CommonsLoggingLog"),
    LOG4J2("org.apache.logging.log4j.LogManager", "com.j256.ormlite.logger.Log4j2Log"),
    LOG4J("org.apache.log4j.Logger", "com.j256.ormlite.logger.Log4jLog"),
    LOCAL(LocalLog.class.getName(), LocalLog.class.getName()){

        @Override
        public Log createLog(String string) {
            return new LocalLog(string);
        }

        @Override
        public boolean isAvailable() {
            return true;
        }
    };
    
    private final String detectClassName;
    private final String logClassName;

    private LoggerFactory.LogType(String string2, String string3) {
        this.detectClassName = string2;
        this.logClassName = string3;
    }

    public Log createLog(String object) {
        try {
            Log log = this.createLogFromClassName((String)object);
            return log;
        }
        catch (Exception exception) {
            object = new LocalLog((String)object);
            Log.Level level = Log.Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to call constructor with single String argument for class ");
            stringBuilder.append(this.logClassName);
            stringBuilder.append(", so had to use local log: ");
            stringBuilder.append(exception.getMessage());
            object.log(level, stringBuilder.toString());
            return object;
        }
    }

    Log createLogFromClassName(String string) throws Exception {
        return (Log)Class.forName(this.logClassName).getConstructor(String.class).newInstance(string);
    }

    public boolean isAvailable() {
        if (!this.isAvailableTestClass()) {
            return false;
        }
        try {
            this.createLogFromClassName(this.getClass().getName()).isLevelEnabled(Log.Level.INFO);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean isAvailableTestClass() {
        try {
            Class.forName(this.detectClassName);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

}
