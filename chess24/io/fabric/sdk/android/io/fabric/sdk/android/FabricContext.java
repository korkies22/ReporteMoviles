/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.SharedPreferences
 *  android.database.DatabaseErrorHandler
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 */
package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;

class FabricContext
extends ContextWrapper {
    private final String componentName;
    private final String componentPath;

    public FabricContext(Context context, String string, String string2) {
        super(context);
        this.componentName = string;
        this.componentPath = string2;
    }

    public File getCacheDir() {
        return new File(super.getCacheDir(), this.componentPath);
    }

    public File getDatabasePath(String string) {
        File file = new File(super.getDatabasePath(string).getParentFile(), this.componentPath);
        file.mkdirs();
        return new File(file, string);
    }

    @TargetApi(value=8)
    public File getExternalCacheDir() {
        return new File(super.getExternalCacheDir(), this.componentPath);
    }

    @TargetApi(value=8)
    public File getExternalFilesDir(String string) {
        return new File(super.getExternalFilesDir(string), this.componentPath);
    }

    public File getFilesDir() {
        return new File(super.getFilesDir(), this.componentPath);
    }

    public SharedPreferences getSharedPreferences(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.componentName);
        stringBuilder.append(":");
        stringBuilder.append(string);
        return super.getSharedPreferences(stringBuilder.toString(), n);
    }

    public SQLiteDatabase openOrCreateDatabase(String string, int n, SQLiteDatabase.CursorFactory cursorFactory) {
        return SQLiteDatabase.openOrCreateDatabase((File)this.getDatabasePath(string), (SQLiteDatabase.CursorFactory)cursorFactory);
    }

    @TargetApi(value=11)
    public SQLiteDatabase openOrCreateDatabase(String string, int n, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return SQLiteDatabase.openOrCreateDatabase((String)this.getDatabasePath(string).getPath(), (SQLiteDatabase.CursorFactory)cursorFactory, (DatabaseErrorHandler)databaseErrorHandler);
    }
}
