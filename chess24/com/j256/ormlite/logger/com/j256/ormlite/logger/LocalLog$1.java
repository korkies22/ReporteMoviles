/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

static final class LocalLog
extends ThreadLocal<DateFormat> {
    LocalLog() {
    }

    @Override
    protected DateFormat initialValue() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    }
}
