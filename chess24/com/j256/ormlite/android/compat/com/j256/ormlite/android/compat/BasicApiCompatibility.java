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
import com.j256.ormlite.android.compat.ApiCompatibility;

public class BasicApiCompatibility
implements ApiCompatibility {
    @Override
    public ApiCompatibility.CancellationHook createCancellationHook() {
        return null;
    }

    @Override
    public Cursor rawQuery(SQLiteDatabase sQLiteDatabase, String string, String[] arrstring, ApiCompatibility.CancellationHook cancellationHook) {
        return sQLiteDatabase.rawQuery(string, arrstring);
    }
}
