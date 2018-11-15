/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.CursorWindow
 *  android.database.sqlite.SQLiteCursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.zzad;
import com.google.android.gms.tagmanager.zzan;
import com.google.android.gms.tagmanager.zzas;
import com.google.android.gms.tagmanager.zzaw;
import com.google.android.gms.tagmanager.zzax;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdc;
import com.google.android.gms.tagmanager.zzdf;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

class zzcg
implements zzaw {
    private static final String zzadt = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private final Context mContext;
    private final zzb zzbEQ;
    private volatile zzad zzbER;
    private final zzax zzbES;
    private final String zzbET;
    private long zzbEU;
    private final int zzbEV;
    private zze zzuI;

    zzcg(zzax zzax2, Context context) {
        this(zzax2, context, "gtm_urls.db", 2000);
    }

    zzcg(zzax zzax2, Context context, String string, int n) {
        this.mContext = context.getApplicationContext();
        this.zzbET = string;
        this.zzbES = zzax2;
        this.zzuI = zzh.zzyv();
        this.zzbEQ = new zzb(this.mContext, this.zzbET);
        this.zzbER = new zzdf(this.mContext, new zza());
        this.zzbEU = 0L;
        this.zzbEV = n;
    }

    private void zzPv() {
        int n = this.zzPw() - this.zzbEV + 1;
        if (n > 0) {
            List<String> list = this.zzmV(n);
            n = list.size();
            StringBuilder stringBuilder = new StringBuilder(51);
            stringBuilder.append("Store full, deleting ");
            stringBuilder.append(n);
            stringBuilder.append(" hits to make room.");
            zzbo.v(stringBuilder.toString());
            this.zzh(list.toArray(new String[0]));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzh(long l, long l2) {
        SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for getNumStoredHits.");
        if (sQLiteDatabase == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(l2));
        try {
            sQLiteDatabase.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(l)});
            return;
        }
        catch (SQLiteException sQLiteException) {}
        StringBuilder stringBuilder = new StringBuilder(69);
        stringBuilder.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
        stringBuilder.append(l);
        zzbo.zzbe(stringBuilder.toString());
        this.zzu(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzh(long l, String string) {
        SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for putHit");
        if (sQLiteDatabase == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(l));
        contentValues.put("hit_url", string);
        contentValues.put("hit_first_send_time", Integer.valueOf(0));
        try {
            sQLiteDatabase.insert("gtm_hits", null, contentValues);
            this.zzbES.zzaM(false);
            return;
        }
        catch (SQLiteException sQLiteException) {}
        zzbo.zzbe("Error storing hit");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SQLiteDatabase zzhh(String string) {
        try {
            return this.zzbEQ.getWritableDatabase();
        }
        catch (SQLiteException sQLiteException) {}
        zzbo.zzbe(string);
        return null;
    }

    private void zzu(long l) {
        this.zzh(new String[]{String.valueOf(l)});
    }

    @Override
    public void dispatch() {
        zzbo.v("GTM Dispatch running...");
        if (!this.zzbER.zzPa()) {
            return;
        }
        List<zzas> list = this.zzmW(40);
        if (list.isEmpty()) {
            zzbo.v("...nothing to dispatch");
            this.zzbES.zzaM(true);
            return;
        }
        this.zzbER.zzM(list);
        if (this.zzPx() > 0) {
            zzdc.zzPT().dispatch();
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
    int zzPw() {
        Object object;
        SQLiteDatabase sQLiteDatabase;
        int n2;
        int n;
        block10 : {
            Object var8_5;
            block9 : {
                int n3;
                block8 : {
                    long l;
                    object = this.zzhh("Error opening database for getNumStoredHits.");
                    n2 = 0;
                    n3 = 0;
                    if (object == null) {
                        return 0;
                    }
                    var8_5 = null;
                    sQLiteDatabase = null;
                    object = object.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                    try {
                        if (!object.moveToFirst()) break block8;
                        l = object.getLong(0);
                    }
                    catch (Throwable throwable) {
                        sQLiteDatabase = object;
                        object = throwable;
                        break block9;
                    }
                    catch (SQLiteException sQLiteException) {
                        break block10;
                    }
                    n3 = (int)l;
                }
                n = n3;
                if (object == null) return n;
                object.close();
                return n3;
                catch (Throwable throwable) {}
            }
            if (sQLiteDatabase == null) throw object;
            sQLiteDatabase.close();
            throw object;
            catch (SQLiteException sQLiteException) {
                object = var8_5;
            }
        }
        sQLiteDatabase = object;
        {
            zzbo.zzbe("Error getting numStoredHits");
            n = n2;
            if (object == null) return n;
        }
        object.close();
        return n2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    int zzPx() {
        Object object;
        SQLiteDatabase sQLiteDatabase;
        block9 : {
            Object var4_3;
            block8 : {
                int n;
                object = this.zzhh("Error opening database for getNumStoredHits.");
                if (object == null) {
                    return 0;
                }
                var4_3 = null;
                sQLiteDatabase = null;
                object = object.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
                try {
                    n = object.getCount();
                    if (object == null) return n;
                }
                catch (Throwable throwable) {
                    sQLiteDatabase = object;
                    object = throwable;
                    break block8;
                }
                catch (SQLiteException sQLiteException) {
                    break block9;
                }
                object.close();
                return n;
                catch (Throwable throwable) {}
            }
            if (sQLiteDatabase == null) throw object;
            sQLiteDatabase.close();
            throw object;
            catch (SQLiteException sQLiteException) {
                object = var4_3;
            }
        }
        sQLiteDatabase = object;
        {
            zzbo.zzbe("Error getting num untried hits");
            if (object == null) return 0;
        }
        object.close();
        return 0;
    }

    @Override
    public void zzg(long l, String string) {
        this.zznS();
        this.zzPv();
        this.zzh(l, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void zzh(String[] object) {
        boolean bl;
        block5 : {
            block6 : {
                if (object != null) {
                    if (((Object)object).length == 0) {
                        return;
                    }
                    SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for deleteHits.");
                    if (sQLiteDatabase == null) {
                        return;
                    }
                    bl = true;
                    String string = String.format("HIT_ID in (%s)", TextUtils.join((CharSequence)",", Collections.nCopies(((Object)object).length, "?")));
                    try {
                        sQLiteDatabase.delete("gtm_hits", string, (String[])object);
                        object = this.zzbES;
                        if (this.zzPw() == 0) break block5;
                        break block6;
                    }
                    catch (SQLiteException sQLiteException) {}
                    zzbo.zzbe("Error deleting hits");
                }
                return;
            }
            bl = false;
        }
        object.zzaM(bl);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    List<String> zzmV(int n) {
        Object object;
        Cursor cursor;
        block13 : {
            ArrayList<String> arrayList;
            block14 : {
                arrayList = new ArrayList<String>();
                if (n <= 0) {
                    zzbo.zzbe("Invalid maxHits specified. Skipping");
                    return arrayList;
                }
                SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for peekHitIds.");
                if (sQLiteDatabase == null) {
                    return arrayList;
                }
                Cursor cursor2 = null;
                cursor = object = null;
                String string = String.format("%s ASC", "hit_id");
                cursor = object;
                String string2 = Integer.toString(n);
                cursor = object;
                object = sQLiteDatabase.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, string, string2);
                try {
                    if (object.moveToFirst()) {
                        boolean bl;
                        do {
                            arrayList.add(String.valueOf(object.getLong(0)));
                        } while (bl = object.moveToNext());
                    }
                    if (object == null) return arrayList;
                }
                catch (Throwable throwable) {
                    cursor = object;
                    object = throwable;
                    break block13;
                }
                catch (SQLiteException sQLiteException) {
                    break block14;
                }
                object.close();
                return arrayList;
                catch (Throwable throwable) {
                    break block13;
                }
                catch (SQLiteException sQLiteException) {
                    object = cursor2;
                }
            }
            cursor = object;
            {
                String string;
                string = String.valueOf(string.getMessage());
                cursor = object;
                if (string.length() != 0) {
                    cursor = object;
                    string = "Error in peekHits fetching hitIds: ".concat(string);
                } else {
                    cursor = object;
                    string = new String("Error in peekHits fetching hitIds: ");
                }
                cursor = object;
                zzbo.zzbe(string);
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
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<zzas> zzmW(int var1_1) {
        block43 : {
            block44 : {
                block40 : {
                    block41 : {
                        block42 : {
                            block39 : {
                                block36 : {
                                    block37 : {
                                        block38 : {
                                            var7_2 = new ArrayList<zzas>();
                                            var8_4 = this.zzhh("Error opening database for peekHits");
                                            if (var8_4 == null) {
                                                return var7_2;
                                            }
                                            var2_5 = 0;
                                            var4_6 = String.format("%s ASC", new Object[]{"hit_id"});
                                            var5_8 = Integer.toString(var1_1);
                                            var4_6 = var8_4.query("gtm_hits", new String[]{"hit_id", "hit_time", "hit_first_send_time"}, null, null, null, null, (String)var4_6, (String)var5_8);
                                            try {
                                                var6_22 = new ArrayList<E>();
                                                try {
                                                    var3_25 = var4_6.moveToFirst();
                                                    if (!var3_25) break block36;
                                                    break block37;
                                                }
                                                catch (SQLiteException var5_18) {
                                                    ** GOTO lbl69
                                                }
                                            }
                                            catch (Throwable var5_19) {
                                                var6_23 = var4_6;
                                                var4_6 = var5_19;
                                                var5_10 = var6_23;
                                                ** GOTO lbl79
                                            }
                                            catch (SQLiteException var5_20) {
                                                break block38;
                                            }
                                            catch (Throwable var4_7) {
                                                var5_10 = null;
                                                ** GOTO lbl79
                                            }
                                            catch (SQLiteException var5_21) {
                                                var4_6 = null;
                                            }
                                        }
                                        var6_22 = var7_2;
                                        ** GOTO lbl69
                                    }
                                    try {
                                        do {
                                            var6_22.add((zzas)new zzas(var4_6.getLong(0), var4_6.getLong(1), var4_6.getLong(2)));
                                        } while (var3_25 = var4_6.moveToNext());
                                    }
                                    catch (Throwable var5_9) {
                                        var6_22 = var4_6;
                                        var4_6 = var5_9;
                                        var5_10 = var6_22;
                                        ** GOTO lbl79
                                    }
                                    catch (SQLiteException var5_11) {
                                        ** GOTO lbl69
                                    }
                                }
                                if (var4_6 != null) {
                                    var4_6.close();
                                }
                                var7_2 = String.format("%s ASC", new Object[]{"hit_id"});
                                var9_26 = Integer.toString(var1_1);
                                var5_8 = var6_22;
                                var4_6 = var7_2 = var8_4.query("gtm_hits", new String[]{"hit_id", "hit_url"}, null, null, null, null, (String)var7_2, var9_26);
                                try {
                                    block45 : {
                                        if (!var7_2.moveToFirst()) break block39;
                                        break block45;
                                        catch (Throwable var5_13) {
                                            break block40;
                                        }
                                        catch (SQLiteException var7_3) {
                                            var5_8 = var4_6;
                                            break block41;
                                        }
                                        catch (Throwable var5_14) {
                                            break block40;
                                        }
                                        catch (SQLiteException var5_16) {
                                            // empty catch block
                                            break block42;
                                        }
lbl69: // 3 sources:
                                        try {
                                            var5_10 = String.valueOf(var5_10.getMessage());
                                            var5_10 = var5_10.length() != 0 ? "Error in peekHits fetching hitIds: ".concat((String)var5_10) : new String("Error in peekHits fetching hitIds: ");
                                            zzbo.zzbe((String)var5_10);
                                            if (var4_6 == null) return var6_22;
                                        }
                                        catch (Throwable var6_24) {
                                            var5_10 = var4_6;
                                            var4_6 = var6_24;
                                        }
                                        var4_6.close();
                                        return var6_22;
lbl79: // 4 sources:
                                        if (var5_10 == null) throw var4_6;
                                        var5_10.close();
                                        throw var4_6;
                                    }
                                    var1_1 = 0;
                                    do {
                                        var4_6 = var7_2;
                                        if (((SQLiteCursor)var7_2).getWindow().getNumRows() > 0) {
                                            var4_6 = var7_2;
                                            ((zzas)var5_8.get(var1_1)).zzhl(var7_2.getString(1));
                                        } else {
                                            var4_6 = var7_2;
                                            zzbo.zzbe(String.format("HitString for hitId %d too large.  Hit will be deleted.", new Object[]{((zzas)var5_8.get(var1_1)).zzPi()}));
                                        }
                                        ++var1_1;
                                        var4_6 = var7_2;
                                    } while (var3_25 = var7_2.moveToNext());
                                }
                                catch (SQLiteException var5_12) {
                                    var4_6 = var7_2;
                                }
                            }
                            if (var7_2 == null) return var5_8;
                            var7_2.close();
                            return var5_8;
                        }
                        var7_2 = var5_8;
                        var5_8 = var4_6;
                    }
                    var4_6 = var5_8;
                    try {
                        var7_2 = String.valueOf(var7_2.getMessage());
                        var4_6 = var5_8;
                        if (var7_2.length() != 0) {
                            var4_6 = var5_8;
                            var7_2 = "Error in peekHits fetching hit url: ".concat((String)var7_2);
                        } else {
                            var4_6 = var5_8;
                            var7_2 = new String("Error in peekHits fetching hit url: ");
                        }
                        var4_6 = var5_8;
                        zzbo.zzbe((String)var7_2);
                        var4_6 = var5_8;
                        var7_2 = new ArrayList<zzas>();
                        var4_6 = var5_8;
                        var6_22 = var6_22.iterator();
                        var1_1 = var2_5;
lbl122: // 2 sources:
                        var4_6 = var5_8;
                        if (var6_22.hasNext()) {
                            var4_6 = var5_8;
                            var8_4 = (zzas)var6_22.next();
                            var2_5 = var1_1;
                            var4_6 = var5_8;
                            if (!TextUtils.isEmpty((CharSequence)var8_4.zzPk())) break block43;
                            if (var1_1 == 0) break block44;
                        }
                        if (var5_8 == null) return var7_2;
                    }
                    catch (Throwable var5_17) {}
                    var5_8.close();
                    return var7_2;
                }
                if (var4_6 == null) throw var5_15;
                var4_6.close();
                throw var5_15;
            }
            var2_5 = 1;
        }
        var4_6 = var5_8;
        var7_2.add((zzas)var8_4);
        var1_1 = var2_5;
        ** GOTO lbl122
    }

    int zznS() {
        long l = this.zzuI.currentTimeMillis();
        long l2 = this.zzbEU;
        boolean bl = false;
        if (l <= l2 + 86400000L) {
            return 0;
        }
        this.zzbEU = l;
        Object object = this.zzhh("Error opening database for deleteStaleHits.");
        if (object == null) {
            return 0;
        }
        int n = object.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzuI.currentTimeMillis() - 2592000000L)});
        object = this.zzbES;
        if (this.zzPw() == 0) {
            bl = true;
        }
        object.zzaM(bl);
        return n;
    }

    class zza
    implements zzdf.zza {
        zza() {
        }

        @Override
        public void zza(zzas zzas2) {
            zzcg.this.zzu(zzas2.zzPi());
        }

        @Override
        public void zzb(zzas object) {
            zzcg.this.zzu(object.zzPi());
            long l = object.zzPi();
            object = new StringBuilder(57);
            object.append("Permanent failure dispatching hitId: ");
            object.append(l);
            zzbo.v(object.toString());
        }

        @Override
        public void zzc(zzas object) {
            long l = object.zzPj();
            if (l == 0L) {
                zzcg.this.zzh(object.zzPi(), zzcg.this.zzuI.currentTimeMillis());
                return;
            }
            if (l + 14400000L < zzcg.this.zzuI.currentTimeMillis()) {
                zzcg.this.zzu(object.zzPi());
                l = object.zzPi();
                object = new StringBuilder(47);
                object.append("Giving up on failed hitId: ");
                object.append(l);
                zzbo.v(object.toString());
            }
        }
    }

    class zzb
    extends SQLiteOpenHelper {
        private boolean zzbEX;
        private long zzbEY;

        zzb(Context context, String string) {
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
                void var1_4;
                Object var5_7;
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
            int n;
            String[] arrstring;
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

}
