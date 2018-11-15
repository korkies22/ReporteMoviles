/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.CharArrayBuffer
 *  android.database.CursorIndexOutOfBoundsException
 *  android.database.CursorWindow
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.data.zze;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzc;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@KeepName
public final class DataHolder
extends com.google.android.gms.common.internal.safeparcel.zza
implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zze();
    private static final zza zzaCx = new zza(new String[0], null){

        @Override
        public zza zza(ContentValues contentValues) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        @Override
        public zza zzb(HashMap<String, Object> hashMap) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    };
    boolean mClosed = false;
    final int mVersionCode;
    private final String[] zzaCq;
    Bundle zzaCr;
    private final CursorWindow[] zzaCs;
    private final Bundle zzaCt;
    int[] zzaCu;
    int zzaCv;
    private boolean zzaCw = true;
    private final int zzauz;

    DataHolder(int n, String[] arrstring, CursorWindow[] arrcursorWindow, int n2, Bundle bundle) {
        this.mVersionCode = n;
        this.zzaCq = arrstring;
        this.zzaCs = arrcursorWindow;
        this.zzauz = n2;
        this.zzaCt = bundle;
    }

    private DataHolder(zza zza2, int n, Bundle bundle) {
        this(zza2.zzaCq, DataHolder.zza(zza2, -1), n, bundle);
    }

    public DataHolder(String[] arrstring, CursorWindow[] arrcursorWindow, int n, Bundle bundle) {
        this.mVersionCode = 1;
        this.zzaCq = zzac.zzw(arrstring);
        this.zzaCs = zzac.zzw(arrcursorWindow);
        this.zzauz = n;
        this.zzaCt = bundle;
        this.zzwD();
    }

    public static DataHolder zza(int n, Bundle bundle) {
        return new DataHolder(zzaCx, n, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static CursorWindow[] zza(zza object, int n) {
        int n2;
        Object object2 = ((zza)object).zzaCq;
        int n3 = 0;
        if (((String[])object2).length == 0) {
            return new CursorWindow[0];
        }
        List list = n >= 0 && n < ((zza)object).zzaCy.size() ? ((zza)object).zzaCy.subList(0, n) : ((zza)object).zzaCy;
        int n4 = list.size();
        Object object3 = new CursorWindow(false);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(object3);
        object3.setNumColumns(((zza)object).zzaCq.length);
        n = n2 = 0;
        block2 : while (n < n4) {
            boolean bl;
            Map map;
            int n5;
            object2 = object3;
            try {
                if (!object3.allocRow()) {
                    object2 = new StringBuilder(72);
                    object2.append("Allocating additional cursor window for large data set (row ");
                    object2.append(n);
                    object2.append(")");
                    Log.d((String)"DataHolder", (String)object2.toString());
                    object3 = new CursorWindow(false);
                    object3.setStartPosition(n);
                    object3.setNumColumns(((zza)object).zzaCq.length);
                    arrayList.add(object3);
                    object2 = object3;
                    if (!object3.allocRow()) {
                        Log.e((String)"DataHolder", (String)"Unable to allocate row to hold data.");
                        arrayList.remove(object3);
                        return arrayList.toArray((T[])new CursorWindow[arrayList.size()]);
                    }
                }
                map = (Map)list.get(n);
                n5 = 0;
                bl = true;
            }
            catch (RuntimeException runtimeException) {
                n2 = arrayList.size();
                n = n3;
                do {
                    if (n >= n2) {
                        throw runtimeException;
                    }
                    ((CursorWindow)arrayList.get(n)).close();
                    ++n;
                } while (true);
            }
            do {
                block23 : {
                    long l;
                    block26 : {
                        block21 : {
                            Object v;
                            block25 : {
                                block24 : {
                                    block22 : {
                                        if (n5 >= ((zza)object).zzaCq.length || !bl) break block21;
                                        object3 = ((zza)object).zzaCq[n5];
                                        v = map.get(object3);
                                        if (v != null) break block22;
                                        bl = object2.putNull(n, n5);
                                        break block23;
                                    }
                                    if (!(v instanceof String)) break block24;
                                    bl = object2.putString((String)v, n, n5);
                                    break block23;
                                }
                                if (!(v instanceof Long)) break block25;
                                l = (Long)v;
                                break block26;
                            }
                            if (v instanceof Integer) {
                                bl = object2.putLong((long)((Integer)v).intValue(), n, n5);
                            } else {
                                if (v instanceof Boolean) {
                                    l = (Boolean)v != false ? 1L : 0L;
                                }
                                if (v instanceof byte[]) {
                                    bl = object2.putBlob((byte[])v, n, n5);
                                } else if (v instanceof Double) {
                                    bl = object2.putDouble(((Double)v).doubleValue(), n, n5);
                                } else {
                                    if (!(v instanceof Float)) {
                                        object = String.valueOf(v);
                                        object2 = new StringBuilder(32 + String.valueOf(object3).length() + String.valueOf(object).length());
                                        object2.append("Unsupported object for column ");
                                        object2.append((String)object3);
                                        object2.append(": ");
                                        object2.append((String)object);
                                        throw new IllegalArgumentException(object2.toString());
                                    }
                                    bl = object2.putDouble((double)((Float)v).floatValue(), n, n5);
                                }
                            }
                            break block23;
                        }
                        if (!bl) {
                            if (n2 != 0) {
                                throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                            }
                            object3 = new StringBuilder(74);
                            object3.append("Couldn't populate window data for row ");
                            object3.append(n);
                            object3.append(" - allocating new window.");
                            Log.d((String)"DataHolder", (String)object3.toString());
                            object2.freeLastRow();
                            object2 = new CursorWindow(false);
                            object2.setStartPosition(n);
                            object2.setNumColumns(((zza)object).zzaCq.length);
                            arrayList.add(object2);
                            --n;
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        ++n;
                        object3 = object2;
                        continue block2;
                    }
                    bl = object2.putLong(l, n, n5);
                }
                ++n5;
            } while (true);
            break;
        }
        return arrayList.toArray((T[])new CursorWindow[arrayList.size()]);
    }

    public static zza zzc(String[] arrstring) {
        return new zza(arrstring, null);
    }

    public static DataHolder zzcD(int n) {
        return DataHolder.zza(n, null);
    }

    private void zzi(String string, int n) {
        if (this.zzaCr != null && this.zzaCr.containsKey(string)) {
            if (this.isClosed()) {
                throw new IllegalArgumentException("Buffer is closed.");
            }
            if (n >= 0 && n < this.zzaCv) {
                return;
            }
            throw new CursorIndexOutOfBoundsException(n, this.zzaCv);
        }
        string = (string = String.valueOf(string)).length() != 0 ? "No such column: ".concat(string) : new String("No such column: ");
        throw new IllegalArgumentException(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i = 0; i < this.zzaCs.length; ++i) {
                    this.zzaCs[i].close();
                }
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.zzaCw && this.zzaCs.length > 0 && !this.isClosed()) {
                this.close();
                String string = String.valueOf(this.toString());
                StringBuilder stringBuilder = new StringBuilder(178 + String.valueOf(string).length());
                stringBuilder.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                stringBuilder.append(string);
                stringBuilder.append(")");
                Log.e((String)"DataBuffer", (String)stringBuilder.toString());
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    public int getCount() {
        return this.zzaCv;
    }

    public int getStatusCode() {
        return this.zzauz;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isClosed() {
        synchronized (this) {
            return this.mClosed;
        }
    }

    public void writeToParcel(Parcel parcel, int n) {
        zze.zza(this, parcel, n);
    }

    public void zza(String string, int n, int n2, CharArrayBuffer charArrayBuffer) {
        this.zzi(string, n);
        this.zzaCs[n2].copyStringToBuffer(n, this.zzaCr.getInt(string), charArrayBuffer);
    }

    public long zzb(String string, int n, int n2) {
        this.zzi(string, n);
        return this.zzaCs[n2].getLong(n, this.zzaCr.getInt(string));
    }

    public int zzc(String string, int n, int n2) {
        this.zzi(string, n);
        return this.zzaCs[n2].getInt(n, this.zzaCr.getInt(string));
    }

    public int zzcC(int n) {
        int n2;
        int n3 = 0;
        boolean bl = n >= 0 && n < this.zzaCv;
        zzac.zzar(bl);
        do {
            n2 = n3;
            if (n3 >= this.zzaCu.length) break;
            if (n < this.zzaCu[n3]) {
                n2 = n3 - 1;
                break;
            }
            ++n3;
        } while (true);
        n = n2;
        if (n2 == this.zzaCu.length) {
            n = n2 - 1;
        }
        return n;
    }

    public String zzd(String string, int n, int n2) {
        this.zzi(string, n);
        return this.zzaCs[n2].getString(n, this.zzaCr.getInt(string));
    }

    public boolean zzdj(String string) {
        return this.zzaCr.containsKey(string);
    }

    public boolean zze(String string, int n, int n2) {
        this.zzi(string, n);
        if (Long.valueOf(this.zzaCs[n2].getLong(n, this.zzaCr.getInt(string))) == 1L) {
            return true;
        }
        return false;
    }

    public float zzf(String string, int n, int n2) {
        this.zzi(string, n);
        return this.zzaCs[n2].getFloat(n, this.zzaCr.getInt(string));
    }

    public byte[] zzg(String string, int n, int n2) {
        this.zzi(string, n);
        return this.zzaCs[n2].getBlob(n, this.zzaCr.getInt(string));
    }

    public Uri zzh(String string, int n, int n2) {
        if ((string = this.zzd(string, n, n2)) == null) {
            return null;
        }
        return Uri.parse((String)string);
    }

    public boolean zzi(String string, int n, int n2) {
        this.zzi(string, n);
        return this.zzaCs[n2].isNull(n, this.zzaCr.getInt(string));
    }

    public void zzwD() {
        int n;
        this.zzaCr = new Bundle();
        int n2 = 0;
        for (n = 0; n < this.zzaCq.length; ++n) {
            this.zzaCr.putInt(this.zzaCq[n], n);
        }
        this.zzaCu = new int[this.zzaCs.length];
        int n3 = 0;
        for (n = n2; n < this.zzaCs.length; ++n) {
            this.zzaCu[n] = n3;
            n2 = this.zzaCs[n].getStartPosition();
            n3 += this.zzaCs[n].getNumRows() - (n3 - n2);
        }
        this.zzaCv = n3;
    }

    String[] zzwE() {
        return this.zzaCq;
    }

    CursorWindow[] zzwF() {
        return this.zzaCs;
    }

    public Bundle zzwy() {
        return this.zzaCt;
    }

    public static class zza {
        private final HashMap<Object, Integer> zzaCA;
        private boolean zzaCB;
        private String zzaCC;
        private final String[] zzaCq;
        private final ArrayList<HashMap<String, Object>> zzaCy;
        private final String zzaCz;

        private zza(String[] arrstring, String string) {
            this.zzaCq = zzac.zzw(arrstring);
            this.zzaCy = new ArrayList();
            this.zzaCz = string;
            this.zzaCA = new HashMap();
            this.zzaCB = false;
            this.zzaCC = null;
        }

        private int zzc(HashMap<String, Object> object) {
            if (this.zzaCz == null) {
                return -1;
            }
            if ((object = object.get(this.zzaCz)) == null) {
                return -1;
            }
            Integer n = this.zzaCA.get(object);
            if (n == null) {
                this.zzaCA.put(object, this.zzaCy.size());
                return -1;
            }
            return n;
        }

        public zza zza(ContentValues object) {
            zzc.zzt(object);
            HashMap<String, Object> hashMap = new HashMap<String, Object>(object.size());
            for (Map.Entry entry : object.valueSet()) {
                hashMap.put((String)entry.getKey(), entry.getValue());
            }
            return this.zzb(hashMap);
        }

        public zza zzb(HashMap<String, Object> hashMap) {
            zzc.zzt(hashMap);
            int n = this.zzc(hashMap);
            if (n == -1) {
                this.zzaCy.add(hashMap);
            } else {
                this.zzaCy.remove(n);
                this.zzaCy.add(n, hashMap);
            }
            this.zzaCB = false;
            return this;
        }

        public DataHolder zzcE(int n) {
            return new DataHolder(this, n, null);
        }
    }

    public static class zzb
    extends RuntimeException {
        public zzb(String string) {
            super(string);
        }
    }

}
