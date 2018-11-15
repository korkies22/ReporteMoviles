/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 */
package com.j256.ormlite.android.compat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface ApiCompatibility {
    public CancellationHook createCancellationHook();

    public Cursor rawQuery(SQLiteDatabase var1, String var2, String[] var3, CancellationHook var4);

    public static interface CancellationHook {
        public void cancel();
    }

}
