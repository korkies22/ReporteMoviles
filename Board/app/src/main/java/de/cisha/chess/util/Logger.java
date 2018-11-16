// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

public abstract class Logger
{
    private static Logger _instance;
    
    public static Logger getInstance() {
        synchronized (Logger.class) {
            if (Logger._instance == null) {
                Logger._instance = new SystemOutLogger();
            }
            return Logger._instance;
        }
    }
    
    public static void setLogger(final Logger instance) {
        if (instance != null) {
            Logger._instance = instance;
        }
    }
    
    public abstract void debug(final String p0);
    
    public abstract void debug(final String p0, final String p1);
    
    public abstract void debug(final String p0, final String p1, final Throwable p2);
    
    public abstract void error(final String p0);
    
    public abstract void error(final String p0, final String p1);
    
    public abstract void error(final String p0, final String p1, final Throwable p2);
}
