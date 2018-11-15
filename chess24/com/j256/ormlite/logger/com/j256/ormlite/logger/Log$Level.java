/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log;

public static enum Log.Level {
    TRACE(1),
    DEBUG(2),
    INFO(3),
    WARNING(4),
    ERROR(5),
    FATAL(6);
    
    private int level;

    private Log.Level(int n2) {
        this.level = n2;
    }

    public boolean isEnabled(Log.Level level) {
        if (this.level <= level.level) {
            return true;
        }
        return false;
    }
}
