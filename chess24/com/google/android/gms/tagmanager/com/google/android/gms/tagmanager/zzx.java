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
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzan;
import com.google.android.gms.tagmanager.zzbo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class zzx
implements DataLayer.zzc {
    private static final String zzbDJ = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", "ID", "key", "value", "expires");
    private final Context mContext;
    private final Executor zzbDK;
    private zza zzbDL;
    private int zzbDM;
    private zze zzuI;

    public zzx(Context context) {
        this(context, zzh.zzyv(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    zzx(Context context, zze zze2, String string, int n, Executor executor) {
        this.mContext = context;
        this.zzuI = zze2;
        this.zzbDM = n;
        this.zzbDK = executor;
        this.zzbDL = new zza(this.mContext, string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private byte[] zzJ(Object var1_1) {
        block13 : {
            block12 : {
                var4_8 = new ByteArrayOutputStream();
                var3_9 = null;
                var2_10 = new ObjectOutputStream(var4_8);
                var2_10.writeObject(var1_1);
                var1_1 = var4_8.toByteArray();
                try {
                    var2_10.close();
                    var4_8.close();
                    return var1_1;
                }
                catch (IOException var2_11) {
                    return var1_1;
                }
                catch (Throwable var1_2) {
                    break block12;
                }
                catch (Throwable var1_3) {
                    var2_10 = var3_9;
                }
            }
            if (var2_10 == null) ** GOTO lbl22
            try {
                var2_10.close();
lbl22: // 2 sources:
                var4_8.close();
            }
            catch (IOException var2_12) {
                throw var1_4;
            }
            throw var1_4;
            catch (IOException var1_5) {}
            var2_10 = null;
            break block13;
            catch (IOException var1_6) {}
        }
        if (var2_10 == null) ** GOTO lbl34
        try {
            var2_10.close();
lbl34: // 2 sources:
            var4_8.close();
            return null;
        }
        catch (IOException var1_7) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Object zzK(byte[] var1_1) {
        block18 : {
            block17 : {
                block16 : {
                    var4_8 = new ByteArrayInputStream((byte[])var1_1);
                    var3_9 = null;
                    var1_1 = new ObjectInputStream(var4_8);
                    var2_10 = var1_1.readObject();
                    try {
                        var1_1.close();
                        var4_8.close();
                        return var2_10;
                    }
                    catch (IOException var1_5) {
                        return var2_10;
                    }
                    catch (Throwable var2_11) {
                        var3_9 = var1_1;
                        var1_1 = var2_11;
                        break block16;
                    }
                    catch (Throwable var1_2) {
                        // empty catch block
                    }
                }
                if (var3_9 == null) ** GOTO lbl23
                try {
                    var3_9.close();
lbl23: // 2 sources:
                    var4_8.close();
                }
                catch (IOException var2_14) {
                    throw var1_1;
                }
                throw var1_1;
                catch (IOException var1_4) {}
                var1_1 = null;
                break block17;
                catch (ClassNotFoundException var1_3) {}
                var1_1 = null;
                break block18;
                catch (IOException var2_12) {}
            }
            if (var1_1 == null) ** GOTO lbl38
            try {
                var1_1.close();
lbl38: // 2 sources:
                var4_8.close();
                return null;
            }
            catch (IOException var1_7) {
                return null;
            }
            catch (ClassNotFoundException var2_13) {}
        }
        if (var1_1 == null) ** GOTO lbl47
        try {
            var1_1.close();
lbl47: // 2 sources:
            var4_8.close();
            return null;
        }
        catch (IOException var1_6) {
            return null;
        }
    }

    private List<DataLayer.zza> zzK(List<zzb> object) {
        ArrayList<DataLayer.zza> arrayList = new ArrayList<DataLayer.zza>();
        object = object.iterator();
        while (object.hasNext()) {
            zzb zzb2 = (zzb)object.next();
            arrayList.add(new DataLayer.zza(zzb2.zzAH, this.zzK(zzb2.zzbDS)));
        }
        return arrayList;
    }

    private List<zzb> zzL(List<DataLayer.zza> object) {
        ArrayList<zzb> arrayList = new ArrayList<zzb>();
        object = object.iterator();
        while (object.hasNext()) {
            DataLayer.zza zza2 = (DataLayer.zza)object.next();
            arrayList.add(new zzb(zza2.zzAH, this.zzJ(zza2.zzYe)));
        }
        return arrayList;
    }

    private List<DataLayer.zza> zzOV() {
        try {
            this.zzaw(this.zzuI.currentTimeMillis());
            List<DataLayer.zza> list = this.zzK(this.zzOW());
            return list;
        }
        finally {
            this.zzOY();
        }
    }

    private List<zzb> zzOW() {
        SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for loadSerialized.");
        ArrayList<zzb> arrayList = new ArrayList<zzb>();
        if (sQLiteDatabase == null) {
            return arrayList;
        }
        sQLiteDatabase = sQLiteDatabase.query("datalayer", new String[]{"key", "value"}, null, null, null, null, "ID", null);
        try {
            while (sQLiteDatabase.moveToNext()) {
                arrayList.add(new zzb(sQLiteDatabase.getString(0), sQLiteDatabase.getBlob(1)));
            }
            return arrayList;
        }
        finally {
            sQLiteDatabase.close();
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
    private int zzOX() {
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
                    object = this.zzhh("Error opening database for getNumStoredEntries.");
                    n2 = 0;
                    n3 = 0;
                    if (object == null) {
                        return 0;
                    }
                    var8_5 = null;
                    sQLiteDatabase = null;
                    object = object.rawQuery("SELECT COUNT(*) from datalayer", null);
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
            zzbo.zzbe("Error getting numStoredEntries");
            n = n2;
            if (object == null) return n;
        }
        object.close();
        return n2;
    }

    private void zzOY() {
        try {
            this.zzbDL.close();
            return;
        }
        catch (SQLiteException sQLiteException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzaw(long l) {
        Object object = this.zzhh("Error opening database for deleteOlderThan.");
        if (object == null) {
            return;
        }
        try {
            int n = object.delete("datalayer", "expires <= ?", new String[]{Long.toString(l)});
            object = new StringBuilder(33);
            object.append("Deleted ");
            object.append(n);
            object.append(" expired items");
            zzbo.v(object.toString());
            return;
        }
        catch (SQLiteException sQLiteException) {}
        zzbo.zzbe("Error deleting old entries.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzb(List<zzb> list, long l) {
        synchronized (this) {
            try {
                void var2_3;
                long l2 = this.zzuI.currentTimeMillis();
                this.zzaw(l2);
                this.zzmP(list.size());
                this.zzc(list, l2 + var2_3);
                return;
            }
            finally {
                this.zzOY();
            }
        }
    }

    private void zzc(List<zzb> object, long l) {
        SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for writeEntryToDatabase.");
        if (sQLiteDatabase == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            zzb zzb2 = (zzb)object.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("expires", Long.valueOf(l));
            contentValues.put("key", zzb2.zzAH);
            contentValues.put("value", zzb2.zzbDS);
            sQLiteDatabase.insert("datalayer", null, contentValues);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzg(String[] object) {
        if (object != null) {
            if (((Object)object).length == 0) {
                return;
            }
            SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for deleteEntries.");
            if (sQLiteDatabase == null) {
                return;
            }
            String string = String.format("%s in (%s)", "ID", TextUtils.join((CharSequence)",", Collections.nCopies(((Object)object).length, "?")));
            try {
                sQLiteDatabase.delete("datalayer", string, (String[])object);
                return;
            }
            catch (SQLiteException sQLiteException) {}
            object = String.valueOf(Arrays.toString((Object[])object));
            object = object.length() != 0 ? "Error deleting entries ".concat((String)object) : new String("Error deleting entries ");
            zzbo.zzbe((String)object);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void zzhg(String var1_1) {
        var3_3 = this.zzhh("Error opening database for clearKeysWithPrefix.");
        if (var3_3 == null) {
            return;
        }
        var2_6 = var3_3.delete("datalayer", "key = ? OR key LIKE ?", new String[]{var1_1, String.valueOf(var1_1).concat(".%")});
        var3_3 = new StringBuilder(25);
        var3_3.append("Cleared ");
        var3_3.append(var2_6);
        var3_3.append(" items");
        zzbo.v(var3_3.toString());
lbl11: // 2 sources:
        do {
            this.zzOY();
            return;
            break;
        } while (true);
        {
            catch (Throwable var1_2) {
            }
            catch (SQLiteException var3_4) {}
            {
                var3_5 = String.valueOf((Object)var3_4);
                var4_7 = new StringBuilder(44 + String.valueOf(var1_1).length() + String.valueOf(var3_5).length());
                var4_7.append("Error deleting entries with key prefix: ");
                var4_7.append(var1_1);
                var4_7.append(" (");
                var4_7.append(var3_5);
                var4_7.append(").");
                zzbo.zzbe(var4_7.toString());
                ** continue;
            }
        }
        this.zzOY();
        throw var1_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SQLiteDatabase zzhh(String string) {
        try {
            return this.zzbDL.getWritableDatabase();
        }
        catch (SQLiteException sQLiteException) {}
        zzbo.zzbe(string);
        return null;
    }

    private void zzmP(int n) {
        n = this.zzOX() - this.zzbDM + n;
        if (n > 0) {
            List<String> list = this.zzmQ(n);
            n = list.size();
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("DataLayer store full, deleting ");
            stringBuilder.append(n);
            stringBuilder.append(" entries to make room.");
            zzbo.zzbd(stringBuilder.toString());
            this.zzg(list.toArray(new String[0]));
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
    private List<String> zzmQ(int n) {
        Object object;
        Cursor cursor;
        block13 : {
            ArrayList<String> arrayList;
            block14 : {
                arrayList = new ArrayList<String>();
                if (n <= 0) {
                    zzbo.zzbe("Invalid maxEntries specified. Skipping.");
                    return arrayList;
                }
                SQLiteDatabase sQLiteDatabase = this.zzhh("Error opening database for peekEntryIds.");
                if (sQLiteDatabase == null) {
                    return arrayList;
                }
                Cursor cursor2 = null;
                cursor = object = null;
                String string = String.format("%s ASC", "ID");
                cursor = object;
                String string2 = Integer.toString(n);
                cursor = object;
                object = sQLiteDatabase.query("datalayer", new String[]{"ID"}, null, null, null, null, string, string2);
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
                    string = "Error in peekEntries fetching entryIds: ".concat(string);
                } else {
                    cursor = object;
                    string = new String("Error in peekEntries fetching entryIds: ");
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

    @Override
    public void zza(final DataLayer.zzc.zza zza2) {
        this.zzbDK.execute(new Runnable(){

            @Override
            public void run() {
                zza2.zzJ(zzx.this.zzOV());
            }
        });
    }

    @Override
    public void zza(final List<DataLayer.zza> list, final long l) {
        list = this.zzL(list);
        this.zzbDK.execute(new Runnable(){

            @Override
            public void run() {
                zzx.this.zzb(list, l);
            }
        });
    }

    @Override
    public void zzhf(final String string) {
        this.zzbDK.execute(new Runnable(){

            @Override
            public void run() {
                zzx.this.zzhg(string);
            }
        });
    }

    class zza
    extends SQLiteOpenHelper {
        zza(Context context, String string) {
            super(context, string, null, 1);
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
            int n;
            String[] arrstring;
            sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
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
            if (hashSet.remove("key") && hashSet.remove("value") && hashSet.remove("ID") && hashSet.remove("expires")) {
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
            block2 : {
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                    break block2;
                }
                catch (SQLiteException sQLiteException) {}
                zzx.this.mContext.getDatabasePath("google_tagmanager.db").delete();
                sQLiteDatabase = null;
            }
            SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
            if (sQLiteDatabase != null) return sQLiteDatabase2;
            return super.getWritableDatabase();
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
            if (!this.zza("datalayer", sQLiteDatabase)) {
                sQLiteDatabase.execSQL(zzbDJ);
                return;
            }
            this.zzc(sQLiteDatabase);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        }
    }

    private static class zzb {
        final String zzAH;
        final byte[] zzbDS;

        zzb(String string, byte[] arrby) {
            this.zzAH = string;
            this.zzbDS = arrby;
        }

        public String toString() {
            String string = this.zzAH;
            int n = Arrays.hashCode(this.zzbDS);
            StringBuilder stringBuilder = new StringBuilder(54 + String.valueOf(string).length());
            stringBuilder.append("KeyAndSerialized: key = ");
            stringBuilder.append(string);
            stringBuilder.append(" serialized hash = ");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }
    }

}
