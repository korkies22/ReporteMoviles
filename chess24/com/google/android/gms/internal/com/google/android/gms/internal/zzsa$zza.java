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
package com.google.android.gms.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.google.android.gms.internal.zzsa;
import com.google.android.gms.internal.zzsp;
import com.google.android.gms.internal.zztd;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

class zzsa.zza
extends SQLiteOpenHelper {
    zzsa.zza(Context context, String string) {
        super(context, string, null, 1);
    }

    private void zza(SQLiteDatabase object) {
        Set<String> set = this.zzb((SQLiteDatabase)object, "hits2");
        for (int i = 0; i < 4; ++i) {
            String string = new String[]{"hit_id", "hit_string", "hit_time", "hit_url"}[i];
            if (set.remove(string)) continue;
            object = String.valueOf(string);
            object = object.length() != 0 ? "Database hits2 is missing required column: ".concat((String)object) : new String("Database hits2 is missing required column: ");
            throw new SQLiteException((String)object);
        }
        boolean bl = set.remove("hit_app_id");
        if (!set.isEmpty()) {
            throw new SQLiteException("Database hits2 has extra columns");
        }
        if (bl ^ true) {
            object.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER");
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean zza(SQLiteDatabase object, String string) {
        Object sQLiteDatabase;
        block7 : {
            block8 : {
                boolean bl;
                Object var6_5 = null;
                sQLiteDatabase = null;
                object = object.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{string}, null, null, null);
                try {
                    bl = object.moveToFirst();
                    if (object == null) return bl;
                }
                catch (Throwable throwable) {
                    sQLiteDatabase = object;
                    object = throwable;
                    break block7;
                }
                catch (SQLiteException sQLiteException) {
                    break block8;
                }
                object.close();
                return bl;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (SQLiteException sQLiteException) {
                    object = var6_5;
                }
            }
            sQLiteDatabase = object;
            {
                void var5_10;
                zzsa.this.zzc("Error querying for table", string, var5_10);
                if (object == null) return false;
            }
            object.close();
            return false;
        }
        if (sQLiteDatabase == null) throw object;
        sQLiteDatabase.close();
        throw object;
    }

    private Set<String> zzb(SQLiteDatabase sQLiteDatabase, String arrstring) {
        int n;
        HashSet<String> hashSet = new HashSet<String>();
        StringBuilder stringBuilder = new StringBuilder(22 + String.valueOf(arrstring).length());
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append((String)arrstring);
        stringBuilder.append(" LIMIT 0");
        sQLiteDatabase = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
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
        return hashSet;
    }

    private void zzb(SQLiteDatabase object) {
        object = this.zzb((SQLiteDatabase)object, "properties");
        for (int i = 0; i < 6; ++i) {
            String string = new String[]{"app_uid", "cid", "tid", "params", "adid", "hits_count"}[i];
            if (object.remove(string)) continue;
            object = String.valueOf(string);
            object = object.length() != 0 ? "Database properties is missing required column: ".concat((String)object) : new String("Database properties is missing required column: ");
            throw new SQLiteException((String)object);
        }
        if (!object.isEmpty()) {
            throw new SQLiteException("Database properties table has extra columns");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SQLiteDatabase getWritableDatabase() {
        if (!zzsa.this.zzadx.zzz(3600000L)) {
            throw new SQLiteException("Database open failed");
        }
        try {
            return super.getWritableDatabase();
        }
        catch (SQLiteException sQLiteException) {}
        zzsa.this.zzadx.start();
        zzsa.this.zzbS("Opening the database failed, dropping the table and recreating it");
        String string = zzsa.this.zznV();
        zzsa.this.getContext().getDatabasePath(string).delete();
        try {
            string = super.getWritableDatabase();
            zzsa.this.zzadx.clear();
            return string;
        }
        catch (SQLiteException sQLiteException) {
            zzsa.this.zze("Failed to open freshly created database", (Object)sQLiteException);
            throw sQLiteException;
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzsp.zzbZ(sQLiteDatabase.getPath());
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
        if (!this.zza(sQLiteDatabase, "hits2")) {
            sQLiteDatabase.execSQL(zzadt);
        } else {
            this.zza(sQLiteDatabase);
        }
        if (!this.zza(sQLiteDatabase, "properties")) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
            return;
        }
        this.zzb(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }
}
