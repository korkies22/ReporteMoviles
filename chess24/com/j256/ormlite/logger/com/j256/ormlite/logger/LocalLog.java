/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.LoggerFactory;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalLog
implements Log {
    private static final Log.Level DEFAULT_LEVEL = Log.Level.DEBUG;
    public static final String LOCAL_LOG_FILE_PROPERTY = "com.j256.ormlite.logger.file";
    public static final String LOCAL_LOG_LEVEL_PROPERTY = "com.j256.ormlite.logger.level";
    public static final String LOCAL_LOG_PROPERTIES_FILE = "/ormliteLocalLog.properties";
    private static final List<PatternLevel> classLevels;
    private static final ThreadLocal<DateFormat> dateFormatThreadLocal;
    private static PrintStream printStream;
    private final String className;
    private final Log.Level level;

    static {
        dateFormatThreadLocal = new ThreadLocal<DateFormat>(){

            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            }
        };
        classLevels = LocalLog.readLevelResourceFile(LocalLog.class.getResourceAsStream(LOCAL_LOG_PROPERTIES_FILE));
        LocalLog.openLogFile(System.getProperty(LOCAL_LOG_FILE_PROPERTY));
    }

    public LocalLog(String object) {
        this.className = LoggerFactory.getSimpleClassName(object);
        Object object2 = classLevels;
        Object object3 = null;
        Object object4 = null;
        if (object2 != null) {
            object2 = classLevels.iterator();
            do {
                object3 = object4;
                if (!object2.hasNext()) break;
                object3 = (PatternLevel)object2.next();
                if (!object3.pattern.matcher((CharSequence)object).matches() || object4 != null && object3.level.ordinal() >= object4.ordinal()) continue;
                object4 = object3.level;
            } while (true);
        }
        object = object3;
        if (object3 == null) {
            object3 = System.getProperty(LOCAL_LOG_LEVEL_PROPERTY);
            if (object3 == null) {
                object = DEFAULT_LEVEL;
            } else {
                try {
                    object = Log.Level.valueOf(object3.toUpperCase());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    object4 = new StringBuilder();
                    object4.append("Level '");
                    object4.append((String)object3);
                    object4.append("' was not found");
                    throw new IllegalArgumentException(object4.toString(), illegalArgumentException);
                }
            }
        }
        this.level = object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<PatternLevel> configureClassLevels(InputStream closeable) throws IOException {
        closeable = new BufferedReader(new InputStreamReader((InputStream)closeable));
        ArrayList<PatternLevel> arrayList = new ArrayList<PatternLevel>();
        Object object;
        while ((object = closeable.readLine()) != null) {
            Object object2;
            if (object.length() == 0 || object.charAt(0) == '#') continue;
            Object object3 = object.split("=");
            if (((String[])object3).length != 2) {
                object3 = System.err;
                object2 = new StringBuilder();
                object2.append("Line is not in the format of 'pattern = level': ");
                object2.append((String)object);
                object3.println(object2.toString());
                continue;
            }
            object = Pattern.compile(object3[0].trim());
            try {
                object2 = Log.Level.valueOf(object3[1].trim());
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            arrayList.add(new PatternLevel((Pattern)object, (Log.Level)((Object)object2)));
            continue;
            object = System.err;
            object2 = new StringBuilder();
            object2.append("Level '");
            object2.append((String)object3[1]);
            object2.append("' was not found");
            object.println(object2.toString());
        }
        return arrayList;
    }

    public static void openLogFile(String string) {
        if (string == null) {
            printStream = System.out;
            return;
        }
        try {
            printStream = new PrintStream(new File(string));
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Log file ");
            stringBuilder.append(string);
            stringBuilder.append(" was not found");
            throw new IllegalArgumentException(stringBuilder.toString(), fileNotFoundException);
        }
    }

    private void printMessage(Log.Level level, String string, Throwable throwable) {
        if (!this.isLevelEnabled(level)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(dateFormatThreadLocal.get().format(new Date()));
        stringBuilder.append(" [");
        stringBuilder.append(level.name());
        stringBuilder.append("] ");
        stringBuilder.append(this.className);
        stringBuilder.append(' ');
        stringBuilder.append(string);
        printStream.println(stringBuilder.toString());
        if (throwable != null) {
            throwable.printStackTrace(printStream);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static List<PatternLevel> readLevelResourceFile(InputStream inputStream) {
        Throwable throwable2222;
        if (inputStream == null) return null;
        List<PatternLevel> list = LocalLog.configureClassLevels(inputStream);
        try {
            inputStream.close();
            return list;
        }
        catch (IOException iOException) {
            return list;
        }
        {
            block10 : {
                catch (Throwable throwable2222) {
                    break block10;
                }
                catch (IOException iOException) {}
                {
                    PrintStream printStream = System.err;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("IO exception reading the log properties file '/ormliteLocalLog.properties': ");
                    stringBuilder.append(iOException);
                    printStream.println(stringBuilder.toString());
                }
                inputStream.close();
                return null;
            }
            inputStream.close();
        }
        catch (IOException iOException) {
            throw throwable2222;
        }
        {
            throw throwable2222;
            catch (IOException iOException) {
                return null;
            }
        }
    }

    void flush() {
        printStream.flush();
    }

    @Override
    public boolean isLevelEnabled(Log.Level level) {
        return this.level.isEnabled(level);
    }

    @Override
    public void log(Log.Level level, String string) {
        this.printMessage(level, string, null);
    }

    @Override
    public void log(Log.Level level, String string, Throwable throwable) {
        this.printMessage(level, string, throwable);
    }

    private static class PatternLevel {
        Log.Level level;
        Pattern pattern;

        public PatternLevel(Pattern pattern, Log.Level level) {
            this.pattern = pattern;
            this.level = level;
        }
    }

}
