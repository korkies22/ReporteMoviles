/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.Log;
import java.util.regex.Pattern;

private static class LocalLog.PatternLevel {
    Log.Level level;
    Pattern pattern;

    public LocalLog.PatternLevel(Pattern pattern, Log.Level level) {
        this.pattern = pattern;
        this.level = level;
    }
}
