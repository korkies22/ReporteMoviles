/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.LoggerFactory;

static final class LoggerFactory.LogType
extends LoggerFactory.LogType {
    LoggerFactory.LogType(String string2, int n2, String string3, String string4) {
    }

    @Override
    public Log createLog(String string) {
        return new LocalLog(string);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
