/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.util.SystemOutLogger;

public abstract class Logger {
    private static Logger _instance;

    public static Logger getInstance() {
        synchronized (Logger.class) {
            if (_instance == null) {
                _instance = new SystemOutLogger();
            }
            Logger logger = _instance;
            return logger;
        }
    }

    public static void setLogger(Logger logger) {
        if (logger != null) {
            _instance = logger;
        }
    }

    public abstract void debug(String var1);

    public abstract void debug(String var1, String var2);

    public abstract void debug(String var1, String var2, Throwable var3);

    public abstract void error(String var1);

    public abstract void error(String var1, String var2);

    public abstract void error(String var1, String var2, Throwable var3);
}
