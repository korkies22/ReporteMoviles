/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.tagmanager.zzan;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcg;
import java.io.File;
import java.util.HashSet;

class zzcg.zzb
extends SQLiteOpenHelper {
    private boolean zzbEX;
    private long zzbEY;

    zzcg.zzb(Context context, String string) {
        super(context, string, null, 1);
        this.zzbEY = 0L;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean zza(String string, SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2;
        block10 : {
            Object var5_7;
            void var1_4;
            block9 : {
                boolean bl;
                var5_7 = null;
                sQLiteDatabase2 = null;
                sQLiteDatabase = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{string}, null, null, null);
                try {
                    bl = sQLiteDatabase.moveToFirst();
                    if (sQLiteDatabase == null) return bl;
                }
                catch (Throwable throwable) {
                    sQLiteDatabase2 = sQLiteDatabase;
                    break block9;
                }
                catch (SQLiteException sQLiteException) {
                    break block10;
                }
                sQLiteDatabase.close();
                return bl;
                catch (Throwable throwable) {}
            }
            if (sQLiteDatabase2 == null) throw var1_4;
            sQLiteDatabase2.close();
            throw var1_4;
            catch (SQLiteException sQLiteException) {
                sQLiteDatabase = var5_7;
            }
        }
        sQLiteDatabase2 = sQLiteDatabase;
        {
            string = String.valueOf(string);
            sQLiteDatabase2 = sQLiteDatabase;
            if (string.length() != 0) {
                sQLiteDatabase2 = sQLiteDatabase;
                string = "Error querying for table ".concat(string);
            } else {
                sQLiteDatabase2 = sQLiteDatabase;
                string = new String("Error querying for table ");
            }
            sQLiteDatabase2 = sQLiteDatabase;
            zzbo.zzbe(string);
            if (sQLiteDatabase == null) return false;
        }
        sQLiteDatabase.close();
        return false;
    }

    private void zzc(SQLiteDatabase sQLiteDatabase) {
        String[] arrstring;
        int n;
        sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
        HashSet<String> hashSet = new HashSet<String>();
        try {
            arrstring = sQLiteDatabase.getColumnNames();
            n = 0;
        }
        catch (Throwable throwable) {
            sQLiteDatabase.close();
            throw throwable;
        }
        do {
            if (n >= arrstring.length) break;
            hashSet.add(arrstring[n]);
            ++n;
        } while (true);
        sQLiteDatabase.close();
        if (hashSet.remove("hit_id") && hashSet.remove("hit_url") && hashSet.remove("hit_time") && hashSet.remove("hit_first_send_time")) {
            if (!hashSet.isEmpty()) {
                throw new SQLiteException("Database has extra columns");
            }
            return;
        }
        throw new SQLiteException("Database column missing");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase sQLiteDatabase;
        SQLiteDatabase sQLiteDatabase2;
        block4 : {
            if (this.zzbEX && this.zzbEY + 3600000L > zzcg.this.zzuI.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            sQLiteDatabase = null;
            this.zzbEX = true;
            this.zzbEY = zzcg.this.zzuI.currentTimeMillis();
            try {
                sQLiteDatabase = sQLiteDatabase2 = super.getWritableDatabase();
                break block4;
            }
            catch (SQLiteException sQLiteException) {}
            zzcg.this.mContext.getDatabasePath(zzcg.this.zzbET).delete();
        }
        sQLiteDatabase2 = sQLiteDatabase;
        if (sQLiteDatabase == null) {
            sQLiteDatabase2 = super.getWritableDatabase();
        }
        this.zzbEX = false;
        return sQLiteDatabase2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzan.zzbZ(sQLiteDatabase.getPath());
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (Build.VERSION.SDK_INT < 15) {
            Cursor cursor = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            try {
                cursor.moveToFirst();
            }
            finally {
                cursor.close();
            }
        }
        if (!this.zza("gtm_hits", sQLiteDatabase)) {
            sQLiteDatabase.execSQL(zzadt);
            return;
        }
        this.zzc(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }
}
