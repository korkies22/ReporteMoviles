/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.facebook.internal;

import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Validate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Logger {
    public static final String LOG_TAG_BASE = "FacebookSDK.";
    private static final HashMap<String, String> stringsToReplace = new HashMap();
    private final LoggingBehavior behavior;
    private StringBuilder contents;
    private int priority = 3;
    private final String tag;

    public Logger(LoggingBehavior object, String string) {
        Validate.notNullOrEmpty(string, "tag");
        this.behavior = object;
        object = new StringBuilder();
        object.append(LOG_TAG_BASE);
        object.append(string);
        this.tag = object.toString();
        this.contents = new StringBuilder();
    }

    public static void log(LoggingBehavior loggingBehavior, int n, String string, String charSequence) {
        if (FacebookSdk.isLoggingBehaviorEnabled(loggingBehavior)) {
            String string2 = Logger.replaceStrings((String)charSequence);
            charSequence = string;
            if (!string.startsWith(LOG_TAG_BASE)) {
                charSequence = new StringBuilder();
                charSequence.append(LOG_TAG_BASE);
                charSequence.append(string);
                charSequence = charSequence.toString();
            }
            Log.println((int)n, (String)charSequence, (String)string2);
            if (loggingBehavior == LoggingBehavior.DEVELOPER_ERRORS) {
                new Exception().printStackTrace();
            }
        }
    }

    public static /* varargs */ void log(LoggingBehavior loggingBehavior, int n, String string, String string2, Object ... arrobject) {
        if (FacebookSdk.isLoggingBehaviorEnabled(loggingBehavior)) {
            Logger.log(loggingBehavior, n, string, String.format(string2, arrobject));
        }
    }

    public static void log(LoggingBehavior loggingBehavior, String string, String string2) {
        Logger.log(loggingBehavior, 3, string, string2);
    }

    public static /* varargs */ void log(LoggingBehavior loggingBehavior, String string, String string2, Object ... arrobject) {
        if (FacebookSdk.isLoggingBehaviorEnabled(loggingBehavior)) {
            Logger.log(loggingBehavior, 3, string, String.format(string2, arrobject));
        }
    }

    public static void registerAccessToken(String string) {
        synchronized (Logger.class) {
            if (!FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
                Logger.registerStringToReplace(string, "ACCESS_TOKEN_REMOVED");
            }
            return;
        }
    }

    public static void registerStringToReplace(String string, String string2) {
        synchronized (Logger.class) {
            stringsToReplace.put(string, string2);
            return;
        }
    }

    private static String replaceStrings(String string) {
        synchronized (Logger.class) {
            for (Map.Entry<String, String> entry : stringsToReplace.entrySet()) {
                string = string.replace(entry.getKey(), entry.getValue());
            }
            return string;
        }
    }

    private boolean shouldLog() {
        return FacebookSdk.isLoggingBehaviorEnabled(this.behavior);
    }

    public void append(String string) {
        if (this.shouldLog()) {
            this.contents.append(string);
        }
    }

    public /* varargs */ void append(String string, Object ... arrobject) {
        if (this.shouldLog()) {
            this.contents.append(String.format(string, arrobject));
        }
    }

    public void append(StringBuilder stringBuilder) {
        if (this.shouldLog()) {
            this.contents.append(stringBuilder);
        }
    }

    public void appendKeyValue(String string, Object object) {
        this.append("  %s:\t%s\n", string, object);
    }

    public String getContents() {
        return Logger.replaceStrings(this.contents.toString());
    }

    public int getPriority() {
        return this.priority;
    }

    public void log() {
        this.logString(this.contents.toString());
        this.contents = new StringBuilder();
    }

    public void logString(String string) {
        Logger.log(this.behavior, this.priority, this.tag, string);
    }

    public void setPriority(int n) {
        Validate.oneOf(n, "value", 7, 3, 6, 4, 2, 5);
        this.priority = n;
    }
}
