/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log;
import java.util.Arrays;

public class Logger {
    private static final String ARG_STRING = "{}";
    private static final int ARG_STRING_LENGTH = "{}".length();
    private static final Object UNKNOWN_ARG = new Object();
    private final Log log;

    public Logger(Log log) {
        this.log = log;
    }

    private void appendArg(StringBuilder stringBuilder, Object object) {
        if (object == UNKNOWN_ARG) {
            return;
        }
        if (object == null) {
            stringBuilder.append("null");
            return;
        }
        if (object.getClass().isArray()) {
            stringBuilder.append(Arrays.toString((Object[])object));
            return;
        }
        stringBuilder.append(object);
    }

    private String buildFullMessage(String string, Object object, Object object2, Object object3, Object[] arrobject) {
        int n = 0;
        StringBuilder stringBuilder = null;
        int n2 = 0;
        do {
            int n3;
            if ((n3 = string.indexOf(ARG_STRING, n)) == -1) {
                if (stringBuilder == null) {
                    return string;
                }
                stringBuilder.append(string, n, string.length());
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder2 = stringBuilder;
            if (stringBuilder == null) {
                stringBuilder2 = new StringBuilder(128);
            }
            stringBuilder2.append(string, n, n3);
            n = ARG_STRING_LENGTH + n3;
            if (arrobject == null) {
                if (n2 == 0) {
                    this.appendArg(stringBuilder2, object);
                } else if (n2 == 1) {
                    this.appendArg(stringBuilder2, object2);
                } else if (n2 == 2) {
                    this.appendArg(stringBuilder2, object3);
                }
            } else if (n2 < arrobject.length) {
                this.appendArg(stringBuilder2, arrobject[n2]);
            }
            ++n2;
            stringBuilder = stringBuilder2;
        } while (true);
    }

    private void innerLog(Log.Level level, Throwable throwable, String string, Object object, Object object2, Object object3, Object[] arrobject) {
        if (this.log.isLevelEnabled(level)) {
            string = this.buildFullMessage(string, object, object2, object3, arrobject);
            if (throwable == null) {
                this.log.log(level, string);
                return;
            }
            this.log.log(level, string, throwable);
        }
    }

    public void debug(String string) {
        this.innerLog(Log.Level.DEBUG, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(String string, Object object) {
        this.innerLog(Log.Level.DEBUG, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(String string, Object object, Object object2) {
        this.innerLog(Log.Level.DEBUG, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void debug(String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.DEBUG, null, string, object, object2, object3, null);
    }

    public void debug(String string, Object[] arrobject) {
        this.innerLog(Log.Level.DEBUG, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void debug(Throwable throwable, String string) {
        this.innerLog(Log.Level.DEBUG, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String string, Object object) {
        this.innerLog(Log.Level.DEBUG, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(Log.Level.DEBUG, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.DEBUG, throwable, string, object, object2, object3, null);
    }

    public void debug(Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(Log.Level.DEBUG, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void error(String string) {
        this.innerLog(Log.Level.ERROR, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(String string, Object object) {
        this.innerLog(Log.Level.ERROR, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(String string, Object object, Object object2) {
        this.innerLog(Log.Level.ERROR, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void error(String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.ERROR, null, string, object, object2, object3, null);
    }

    public void error(String string, Object[] arrobject) {
        this.innerLog(Log.Level.ERROR, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void error(Throwable throwable, String string) {
        this.innerLog(Log.Level.ERROR, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String string, Object object) {
        this.innerLog(Log.Level.ERROR, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(Log.Level.ERROR, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.ERROR, throwable, string, object, object2, object3, null);
    }

    public void error(Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(Log.Level.ERROR, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void fatal(String string) {
        this.innerLog(Log.Level.FATAL, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(String string, Object object) {
        this.innerLog(Log.Level.FATAL, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(String string, Object object, Object object2) {
        this.innerLog(Log.Level.FATAL, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void fatal(String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.FATAL, null, string, object, object2, object3, null);
    }

    public void fatal(String string, Object[] arrobject) {
        this.innerLog(Log.Level.FATAL, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void fatal(Throwable throwable, String string) {
        this.innerLog(Log.Level.FATAL, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String string, Object object) {
        this.innerLog(Log.Level.FATAL, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(Log.Level.FATAL, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.FATAL, throwable, string, object, object2, object3, null);
    }

    public void fatal(Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(Log.Level.FATAL, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void info(String string) {
        this.innerLog(Log.Level.INFO, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(String string, Object object) {
        this.innerLog(Log.Level.INFO, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(String string, Object object, Object object2) {
        this.innerLog(Log.Level.INFO, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void info(String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.INFO, null, string, object, object2, object3, null);
    }

    public void info(String string, Object[] arrobject) {
        this.innerLog(Log.Level.INFO, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void info(Throwable throwable, String string) {
        this.innerLog(Log.Level.INFO, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String string, Object object) {
        this.innerLog(Log.Level.INFO, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(Log.Level.INFO, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.INFO, throwable, string, object, object2, object3, null);
    }

    public void info(Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(Log.Level.INFO, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public boolean isLevelEnabled(Log.Level level) {
        return this.log.isLevelEnabled(level);
    }

    public void log(Log.Level level, String string) {
        this.innerLog(level, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Log.Level level, String string, Object object) {
        this.innerLog(level, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Log.Level level, String string, Object object, Object object2) {
        this.innerLog(level, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void log(Log.Level level, String string, Object object, Object object2, Object object3) {
        this.innerLog(level, null, string, object, object2, object3, null);
    }

    public void log(Log.Level level, String string, Object[] arrobject) {
        this.innerLog(level, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void log(Log.Level level, Throwable throwable, String string) {
        this.innerLog(level, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Log.Level level, Throwable throwable, String string, Object object) {
        this.innerLog(level, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Log.Level level, Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(level, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void log(Log.Level level, Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(level, throwable, string, object, object2, object3, null);
    }

    public void log(Log.Level level, Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(level, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void trace(String string) {
        this.innerLog(Log.Level.TRACE, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(String string, Object object) {
        this.innerLog(Log.Level.TRACE, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(String string, Object object, Object object2) {
        this.innerLog(Log.Level.TRACE, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void trace(String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.TRACE, null, string, object, object2, object3, null);
    }

    public void trace(String string, Object[] arrobject) {
        this.innerLog(Log.Level.TRACE, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void trace(Throwable throwable, String string) {
        this.innerLog(Log.Level.TRACE, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String string, Object object) {
        this.innerLog(Log.Level.TRACE, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(Log.Level.TRACE, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.TRACE, throwable, string, object, object2, object3, null);
    }

    public void trace(Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(Log.Level.TRACE, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void warn(String string) {
        this.innerLog(Log.Level.WARNING, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(String string, Object object) {
        this.innerLog(Log.Level.WARNING, null, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(String string, Object object, Object object2) {
        this.innerLog(Log.Level.WARNING, null, string, object, object2, UNKNOWN_ARG, null);
    }

    public void warn(String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.WARNING, null, string, object, object2, object3, null);
    }

    public void warn(String string, Object[] arrobject) {
        this.innerLog(Log.Level.WARNING, null, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }

    public void warn(Throwable throwable, String string) {
        this.innerLog(Log.Level.WARNING, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String string, Object object) {
        this.innerLog(Log.Level.WARNING, throwable, string, object, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String string, Object object, Object object2) {
        this.innerLog(Log.Level.WARNING, throwable, string, object, object2, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String string, Object object, Object object2, Object object3) {
        this.innerLog(Log.Level.WARNING, throwable, string, object, object2, object3, null);
    }

    public void warn(Throwable throwable, String string, Object[] arrobject) {
        this.innerLog(Log.Level.WARNING, throwable, string, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, arrobject);
    }
}
