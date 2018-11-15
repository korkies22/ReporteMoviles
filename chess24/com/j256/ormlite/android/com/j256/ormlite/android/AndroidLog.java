/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.j256.ormlite.android;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.LoggerFactory;

public class AndroidLog
implements Log {
    private static final String ALL_LOGS_NAME = "ORMLite";
    private static final int MAX_TAG_LENGTH = 23;
    private static final int REFRESH_LEVEL_CACHE_EVERY = 200;
    private String className;
    private final boolean[] levelCache;
    private volatile int levelCacheC;

    public AndroidLog(String arrlevel) {
        int n = 0;
        this.levelCacheC = 0;
        this.className = LoggerFactory.getSimpleClassName((String)arrlevel);
        int n2 = this.className.length();
        if (n2 > 23) {
            this.className = this.className.substring(n2 - 23, n2);
        }
        arrlevel = Log.Level.values();
        int n3 = arrlevel.length;
        n2 = 0;
        while (n < n3) {
            int n4 = this.levelToAndroidLevel(arrlevel[n]);
            int n5 = n2;
            if (n4 > n2) {
                n5 = n4;
            }
            ++n;
            n2 = n5;
        }
        this.levelCache = new boolean[n2 + 1];
        this.refreshLevelCache();
    }

    private boolean isLevelEnabledInternal(int n) {
        if (!android.util.Log.isLoggable((String)this.className, (int)n) && !android.util.Log.isLoggable((String)ALL_LOGS_NAME, (int)n)) {
            return false;
        }
        return true;
    }

    private int levelToAndroidLevel(Log.Level level) {
        switch (.$SwitchMap$com$j256$ormlite$logger$Log$Level[level.ordinal()]) {
            default: {
                return 4;
            }
            case 6: {
                return 6;
            }
            case 5: {
                return 6;
            }
            case 4: {
                return 5;
            }
            case 3: {
                return 4;
            }
            case 2: {
                return 3;
            }
            case 1: 
        }
        return 2;
    }

    private void refreshLevelCache() {
        Log.Level[] arrlevel = Log.Level.values();
        int n = arrlevel.length;
        for (int i = 0; i < n; ++i) {
            int n2 = this.levelToAndroidLevel(arrlevel[i]);
            if (n2 >= this.levelCache.length) continue;
            this.levelCache[n2] = this.isLevelEnabledInternal(n2);
        }
    }

    @Override
    public boolean isLevelEnabled(Log.Level level) {
        int n;
        this.levelCacheC = n = this.levelCacheC + 1;
        if (n >= 200) {
            this.refreshLevelCache();
            this.levelCacheC = 0;
        }
        if ((n = this.levelToAndroidLevel(level)) < this.levelCache.length) {
            return this.levelCache[n];
        }
        return this.isLevelEnabledInternal(n);
    }

    @Override
    public void log(Log.Level level, String string) {
        switch (.$SwitchMap$com$j256$ormlite$logger$Log$Level[level.ordinal()]) {
            default: {
                android.util.Log.i((String)this.className, (String)string);
                return;
            }
            case 6: {
                android.util.Log.e((String)this.className, (String)string);
                return;
            }
            case 5: {
                android.util.Log.e((String)this.className, (String)string);
                return;
            }
            case 4: {
                android.util.Log.w((String)this.className, (String)string);
                return;
            }
            case 3: {
                android.util.Log.i((String)this.className, (String)string);
                return;
            }
            case 2: {
                android.util.Log.d((String)this.className, (String)string);
                return;
            }
            case 1: 
        }
        android.util.Log.v((String)this.className, (String)string);
    }

    @Override
    public void log(Log.Level level, String string, Throwable throwable) {
        switch (.$SwitchMap$com$j256$ormlite$logger$Log$Level[level.ordinal()]) {
            default: {
                android.util.Log.i((String)this.className, (String)string, (Throwable)throwable);
                return;
            }
            case 6: {
                android.util.Log.e((String)this.className, (String)string, (Throwable)throwable);
                return;
            }
            case 5: {
                android.util.Log.e((String)this.className, (String)string, (Throwable)throwable);
                return;
            }
            case 4: {
                android.util.Log.w((String)this.className, (String)string, (Throwable)throwable);
                return;
            }
            case 3: {
                android.util.Log.i((String)this.className, (String)string, (Throwable)throwable);
                return;
            }
            case 2: {
                android.util.Log.d((String)this.className, (String)string, (Throwable)throwable);
                return;
            }
            case 1: 
        }
        android.util.Log.v((String)this.className, (String)string, (Throwable)throwable);
    }

}
