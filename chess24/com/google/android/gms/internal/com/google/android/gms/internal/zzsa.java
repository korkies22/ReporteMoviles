/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzry;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsp;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztd;
import com.google.android.gms.internal.zztg;
import java.io.Closeable;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzsa
extends zzru
implements Closeable {
    private static final String zzadt = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzadu = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zza zzadv;
    private final zztd zzadw = new zztd(this.zznq());
    private final zztd zzadx = new zztd(this.zznq());

    zzsa(zzrw zzrw2) {
        super(zzrw2);
        String string = this.zznV();
        this.zzadv = new zza(zzrw2.getContext(), string);
    }

    private static String zzS(Map<String, String> object) {
        zzac.zzw(object);
        Object object2 = new Uri.Builder();
        for (Map.Entry entry : object.entrySet()) {
            object2.appendQueryParameter((String)entry.getKey(), (String)entry.getValue());
        }
        object = object2 = object2.build().getEncodedQuery();
        if (object2 == null) {
            object = "";
        }
        return object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private long zza(String string, String[] cursor, long l) {
        Cursor cursor2;
        void var1_4;
        block8 : {
            block9 : {
                Object var7_11;
                block7 : {
                    SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
                    var7_11 = null;
                    cursor2 = null;
                    cursor = sQLiteDatabase.rawQuery(string, (String[])cursor);
                    try {
                        if (!cursor.moveToFirst()) break block7;
                        l = cursor.getLong(0);
                        if (cursor == null) return l;
                    }
                    catch (Throwable throwable) {
                        cursor2 = cursor;
                        break block8;
                    }
                    catch (SQLiteException sQLiteException) {
                        break block9;
                    }
                    cursor.close();
                    return l;
                }
                if (cursor == null) return l;
                cursor.close();
                return l;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (SQLiteException sQLiteException) {
                    cursor = var7_11;
                }
            }
            cursor2 = cursor;
            {
                void var6_10;
                this.zzd("Database error", string, var6_10);
                cursor2 = cursor;
                throw var6_10;
            }
        }
        if (cursor2 == null) throw var1_4;
        cursor2.close();
        throw var1_4;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private long zzb(String string, String[] cursor) {
        void var1_4;
        block10 : {
            SQLiteException sQLiteException;
            Cursor cursor2;
            block9 : {
                SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
                sQLiteException = null;
                cursor2 = null;
                cursor = sQLiteDatabase.rawQuery(string, (String[])cursor);
                if (!cursor.moveToFirst()) break block9;
                long l = cursor.getLong(0);
                if (cursor == null) return l;
                cursor.close();
                return l;
            }
            try {
                throw new SQLiteException("Database returned empty set");
            }
            catch (Throwable throwable) {}
            catch (SQLiteException sQLiteException2) {}
            finally {
                break block10;
            }
            catch (Throwable throwable) {
                cursor = cursor2;
                break block10;
            }
            catch (SQLiteException sQLiteException3) {
                cursor = sQLiteException;
                sQLiteException = sQLiteException3;
            }
            cursor2 = cursor;
            {
                this.zzd("Database error", string, (Object)sQLiteException);
                cursor2 = cursor;
                throw sQLiteException;
            }
        }
        if (cursor == null) throw var1_4;
        cursor.close();
        throw var1_4;
    }

    private String zzd(zzst zzst2) {
        if (zzst2.zzps()) {
            return this.zzns().zzoJ();
        }
        return this.zzns().zzoK();
    }

    private static String zze(zzst object) {
        zzac.zzw(object);
        Object object2 = new Uri.Builder();
        for (Map.Entry<String, String> entry : object.zzfz().entrySet()) {
            String string = entry.getKey();
            if ("ht".equals(string) || "qt".equals(string) || "AppUID".equals(string)) continue;
            object2.appendQueryParameter(string, entry.getValue());
        }
        object = object2 = object2.build().getEncodedQuery();
        if (object2 == null) {
            object = "";
        }
        return object;
    }

    private void zznU() {
        int n = this.zzns().zzoT();
        long l = this.zznL();
        if (l > (long)(n - 1)) {
            List<Long> list = this.zzs(l - (long)n + 1L);
            this.zzd("Store full, deleting hits to make room, count", list.size());
            this.zzr(list);
        }
    }

    private String zznV() {
        return this.zzns().zzoV();
    }

    public void beginTransaction() {
        this.zznA();
        this.getWritableDatabase().beginTransaction();
    }

    @Override
    public void close() {
        String string;
        void var1_3;
        try {
            this.zzadv.close();
            return;
        }
        catch (IllegalStateException illegalStateException) {
            string = "Error closing database";
        }
        catch (SQLiteException sQLiteException) {
            string = "Sql error closing database";
        }
        this.zze(string, var1_3);
    }

    public void endTransaction() {
        this.zznA();
        this.getWritableDatabase().endTransaction();
    }

    SQLiteDatabase getWritableDatabase() {
        try {
            SQLiteDatabase sQLiteDatabase = this.zzadv.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            this.zzd("Error opening database", (Object)sQLiteException);
            throw sQLiteException;
        }
    }

    boolean isEmpty() {
        if (this.zznL() == 0L) {
            return true;
        }
        return false;
    }

    public void setTransactionSuccessful() {
        this.zznA();
        this.getWritableDatabase().setTransactionSuccessful();
    }

    public long zza(long l, String string, String string2) {
        zzac.zzdv(string);
        zzac.zzdv(string2);
        this.zznA();
        this.zzmq();
        return this.zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(l), string, string2}, 0L);
    }

    public void zza(long l, String string) {
        zzac.zzdv(string);
        this.zznA();
        this.zzmq();
        int n = this.getWritableDatabase().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(l), string});
        if (n > 0) {
            this.zza("Deleted property records", n);
        }
    }

    public void zzb(zzry zzry2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    Map<String, String> zzbT(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return new HashMap<String, String>(0);
        }
        try {
            if (!object.startsWith("?")) {
                object = (object = String.valueOf(object)).length() != 0 ? "?".concat((String)object) : new String("?");
            }
            object = zzn.zza(new URI((String)object), "UTF-8");
            return object;
        }
        catch (URISyntaxException uRISyntaxException) {
            this.zze("Error parsing hit parameters", uRISyntaxException);
            return new HashMap<String, String>(0);
        }
    }

    Map<String, String> zzbU(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return new HashMap<String, String>(0);
        }
        try {
            object = String.valueOf(object);
            object = object.length() != 0 ? "?".concat((String)object) : new String("?");
            object = zzn.zza(new URI((String)object), "UTF-8");
            return object;
        }
        catch (URISyntaxException uRISyntaxException) {
            this.zze("Error parsing property parameters", uRISyntaxException);
            return new HashMap<String, String>(0);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzc(zzst zzst2) {
        zzac.zzw(zzst2);
        this.zzmq();
        this.zznA();
        String string = zzsa.zze(zzst2);
        if (string.length() > 8192) {
            this.zznr().zza(zzst2, "Hit length exceeds the maximum allowed size");
            return;
        }
        this.zznU();
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", string);
        contentValues.put("hit_time", Long.valueOf(zzst2.zzpq()));
        contentValues.put("hit_app_id", Integer.valueOf(zzst2.zzpo()));
        contentValues.put("hit_url", this.zzd(zzst2));
        try {
            long l = sQLiteDatabase.insert("hits2", null, contentValues);
            if (l == -1L) {
                this.zzbS("Failed to insert a hit (got -1)");
                return;
            }
            this.zzb("Hit saved to database. db-id, hit", l, zzst2);
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Error storing a hit", (Object)sQLiteException);
            return;
        }
    }

    @Override
    protected void zzmr() {
    }

    public long zznL() {
        this.zzmq();
        this.zznA();
        return this.zzb("SELECT COUNT(*) FROM hits2", null);
    }

    public void zznQ() {
        this.zzmq();
        this.zznA();
        this.getWritableDatabase().delete("hits2", null, null);
    }

    public void zznR() {
        this.zzmq();
        this.zznA();
        this.getWritableDatabase().delete("properties", null, null);
    }

    public int zznS() {
        this.zzmq();
        this.zznA();
        if (!this.zzadw.zzz(86400000L)) {
            return 0;
        }
        this.zzadw.start();
        this.zzbO("Deleting stale hits (if any)");
        int n = this.getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(this.zznq().currentTimeMillis() - 2592000000L)});
        this.zza("Deleted stale hits, count", n);
        return n;
    }

    public long zznT() {
        this.zzmq();
        this.zznA();
        return this.zza(zzadu, null, 0L);
    }

    public void zzr(List<Long> list) {
        Long l;
        int n;
        zzac.zzw(list);
        this.zzmq();
        this.zznA();
        if (list.isEmpty()) {
            return;
        }
        CharSequence charSequence = new StringBuilder("hit_id");
        charSequence.append(" in (");
        for (n = 0; n < list.size(); ++n) {
            l = list.get(n);
            if (l != null && l != 0L) {
                if (n > 0) {
                    charSequence.append(",");
                }
                charSequence.append(l);
                continue;
            }
            throw new SQLiteException("Invalid hit id");
        }
        charSequence.append(")");
        charSequence = ((StringBuilder)charSequence).toString();
        try {
            l = this.getWritableDatabase();
            this.zza("Deleting dispatched hits. count", list.size());
            n = l.delete("hits2", (String)charSequence, null);
            if (n != list.size()) {
                this.zzb("Deleted fewer hits then expected", list.size(), n, charSequence);
            }
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Error deleting hits", (Object)sQLiteException);
            throw sQLiteException;
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
    public List<Long> zzs(long l) {
        Cursor cursor;
        Object object;
        block10 : {
            ArrayList<Long> arrayList;
            block11 : {
                this.zzmq();
                this.zznA();
                if (l <= 0L) {
                    return Collections.emptyList();
                }
                SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
                arrayList = new ArrayList<Long>();
                Cursor cursor2 = null;
                cursor = object = null;
                String string = String.format("%s ASC", "hit_id");
                cursor = object;
                String string2 = Long.toString(l);
                cursor = object;
                object = sQLiteDatabase.query("hits2", new String[]{"hit_id"}, null, null, null, null, string, string2);
                try {
                    if (object.moveToFirst()) {
                        boolean bl;
                        do {
                            arrayList.add(object.getLong(0));
                        } while (bl = object.moveToNext());
                    }
                    if (object == null) return arrayList;
                }
                catch (Throwable throwable) {
                    cursor = object;
                    object = throwable;
                    break block10;
                }
                catch (SQLiteException sQLiteException) {
                    break block11;
                }
                object.close();
                return arrayList;
                catch (Throwable throwable) {
                    break block10;
                }
                catch (SQLiteException sQLiteException) {
                    object = cursor2;
                }
            }
            cursor = object;
            {
                void var6_6;
                this.zzd("Error selecting hit ids", var6_6);
                if (object == null) return arrayList;
            }
            object.close();
            return arrayList;
        }
        if (cursor == null) throw object;
        cursor.close();
        throw object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<zzst> zzt(long l) {
        Object object;
        void var11_9;
        block10 : {
            String string;
            block9 : {
                boolean bl = l >= 0L;
                zzac.zzas(bl);
                this.zzmq();
                this.zznA();
                object = this.getWritableDatabase();
                int n = 2;
                int n2 = 3;
                int n3 = 4;
                string = String.format("%s ASC", "hit_id");
                Object object2 = Long.toString(l);
                string = object.query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, string, (String)object2);
                object = string;
                try {
                    object2 = new ArrayList();
                    object = string;
                    if (string.moveToFirst()) {
                        do {
                            object = string;
                            l = string.getLong(0);
                            object = string;
                            long l2 = string.getLong(1);
                            object = string;
                            String string2 = string.getString(n);
                            object = string;
                            String string3 = string.getString(n2);
                            object = string;
                            int n4 = string.getInt(n3);
                            object = string;
                            object2.add(new zzst(this, this.zzbT(string2), l2, zztg.zzci(string3), l, n4));
                            object = string;
                        } while (bl = string.moveToNext());
                    }
                    if (string == null) return object2;
                }
                catch (SQLiteException sQLiteException) {
                    break block9;
                }
                string.close();
                return object2;
                catch (Throwable throwable) {
                    object = null;
                    break block10;
                }
                catch (SQLiteException sQLiteException) {
                    string = null;
                }
            }
            object = string;
            try {
                void var12_14;
                this.zze("Error loading hits from the database", var12_14);
                object = string;
                throw var12_14;
            }
            catch (Throwable throwable) {}
        }
        if (object == null) throw var11_9;
        object.close();
        throw var11_9;
    }

    public void zzu(long l) {
        this.zzmq();
        this.zznA();
        ArrayList<Long> arrayList = new ArrayList<Long>(1);
        arrayList.add(l);
        this.zza("Deleting hit, id", l);
        this.zzr(arrayList);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<zzry> zzv(long l) {
        void var8_11;
        SQLiteDatabase sQLiteDatabase;
        block12 : {
            block13 : {
                ArrayList<zzry> arrayList;
                this.zznA();
                this.zzmq();
                sQLiteDatabase = this.getWritableDatabase();
                int n = this.zzns().zzoU();
                sQLiteDatabase = sQLiteDatabase.query("properties", new String[]{"cid", "tid", "adid", "hits_count", "params"}, "app_uid=?", new String[]{String.valueOf(l)}, null, null, null, String.valueOf(n));
                try {
                    arrayList = new ArrayList<zzry>();
                    if (sQLiteDatabase.moveToFirst()) {
                        do {
                            String string = sQLiteDatabase.getString(0);
                            String string2 = sQLiteDatabase.getString(1);
                            boolean bl = sQLiteDatabase.getInt(2) != 0;
                            long l2 = sQLiteDatabase.getInt(3);
                            Map<String, String> map = this.zzbU(sQLiteDatabase.getString(4));
                            if (!TextUtils.isEmpty((CharSequence)string) && !TextUtils.isEmpty((CharSequence)string2)) {
                                arrayList.add(new zzry(l, string, string2, bl, l2, map));
                                continue;
                            }
                            this.zzc("Read property with empty client id or tracker id", string, string2);
                        } while (sQLiteDatabase.moveToNext());
                    }
                    if (arrayList.size() >= n) {
                        this.zzbR("Sending hits to too many properties. Campaign report might be incorrect");
                    }
                    if (sQLiteDatabase == null) return arrayList;
                }
                catch (Throwable throwable) {
                    break block12;
                }
                catch (SQLiteException sQLiteException) {
                    break block13;
                }
                sQLiteDatabase.close();
                return arrayList;
                catch (Throwable throwable) {
                    sQLiteDatabase = null;
                    break block12;
                }
                catch (SQLiteException sQLiteException) {
                    sQLiteDatabase = null;
                }
            }
            try {
                void var8_9;
                this.zze("Error loading hits from the database", var8_9);
                throw var8_9;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (sQLiteDatabase == null) throw var8_11;
        sQLiteDatabase.close();
        throw var8_11;
    }

    class zza
    extends SQLiteOpenHelper {
        zza(Context context, String string) {
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

}
