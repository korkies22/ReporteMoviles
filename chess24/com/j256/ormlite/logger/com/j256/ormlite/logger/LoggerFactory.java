/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import java.lang.reflect.Constructor;

public class LoggerFactory {
    public static final String LOG_TYPE_SYSTEM_PROPERTY = "com.j256.ormlite.logger.type";
    private static LogType logType;

    private LoggerFactory() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static LogType findLogType() {
        LogType[] arrlogType = System.getProperty(LOG_TYPE_SYSTEM_PROPERTY);
        if (arrlogType != null) {
            try {
                return LogType.valueOf((String)arrlogType);
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            LocalLog localLog = new LocalLog(LoggerFactory.class.getName());
            Log.Level level = Log.Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find valid log-type from system property 'com.j256.ormlite.logger.type', value '");
            stringBuilder.append((String)arrlogType);
            stringBuilder.append("'");
            localLog.log(level, stringBuilder.toString());
        }
        arrlogType = LogType.values();
        int n = arrlogType.length;
        int n2 = 0;
        while (n2 < n) {
            LogType logType = arrlogType[n2];
            if (logType.isAvailable()) {
                return logType;
            }
            ++n2;
        }
        return LogType.LOCAL;
    }

    public static Logger getLogger(Class<?> class_) {
        return LoggerFactory.getLogger(class_.getName());
    }

    public static Logger getLogger(String string) {
        if (logType == null) {
            logType = LoggerFactory.findLogType();
        }
        return new Logger(logType.createLog(string));
    }

    public static String getSimpleClassName(String string) {
        String[] arrstring = string.split("\\.");
        if (arrstring.length <= 1) {
            return string;
        }
        return arrstring[arrstring.length - 1];
    }

    public static enum LogType {
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

        private LogType(String string2, String string3) {
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

}
