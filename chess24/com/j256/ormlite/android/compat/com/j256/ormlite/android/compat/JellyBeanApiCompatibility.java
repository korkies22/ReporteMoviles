/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.os.CancellationSignal
 */
package com.j256.ormlite.android.compat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;
import com.j256.ormlite.android.compat.ApiCompatibility;
import com.j256.ormlite.android.compat.BasicApiCompatibility;

public class JellyBeanApiCompatibility
extends BasicApiCompatibility {
    @Override
    public ApiCompatibility.CancellationHook createCancellationHook() {
        return new JellyBeanCancellationHook();
    }

    @Override
    public Cursor rawQuery(SQLiteDatabase sQLiteDatabase, String string, String[] arrstring, ApiCompatibility.CancellationHook cancellationHook) {
        if (cancellationHook == null) {
            return sQLiteDatabase.rawQuery(string, arrstring);
        }
        return sQLiteDatabase.rawQuery(string, arrstring, ((JellyBeanCancellationHook)cancellationHook).cancellationSignal);
    }

    protected static class JellyBeanCancellationHook
    implements ApiCompatibility.CancellationHook {
        private final CancellationSignal cancellationSignal = new CancellationSignal();

        @Override
        public void cancel() {
            this.cancellationSignal.cancel();
        }
    }

}
