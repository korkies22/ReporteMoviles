/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzsf;
import com.google.android.gms.internal.zztg;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzst {
    private final Map<String, String> zzFs;
    private final List<zzsf> zzaff;
    private final long zzafg;
    private final long zzafh;
    private final int zzafi;
    private final boolean zzafj;
    private final String zzafk;

    public zzst(zzrt zzrt2, Map<String, String> map, long l, boolean bl) {
        this(zzrt2, map, l, bl, 0L, 0, null);
    }

    public zzst(zzrt zzrt2, Map<String, String> map, long l, boolean bl, long l2, int n) {
        this(zzrt2, map, l, bl, l2, n, null);
    }

    public zzst(zzrt zzrt2, Map<String, String> object, long l, boolean bl, long l2, int n, List<zzsf> object2) {
        void var10_10;
        zzac.zzw(zzrt2);
        zzac.zzw(object);
        this.zzafh = l;
        this.zzafj = bl;
        this.zzafg = l2;
        this.zzafi = n;
        if (object2 != null) {
            HashMap<String, String> object32 = object2;
        } else {
            List list = Collections.emptyList();
        }
        this.zzaff = var10_10;
        this.zzafk = zzst.zzs((List<zzsf>)((Object)object2));
        object2 = new HashMap<String, String>();
        for (Map.Entry entry : object.entrySet()) {
            String string;
            if (!zzst.zzl(entry.getKey()) || (string = zzst.zza(zzrt2, entry.getKey())) == null) continue;
            object2.put(string, zzst.zzb(zzrt2, entry.getValue()));
        }
        for (Map.Entry entry : object.entrySet()) {
            String string;
            if (zzst.zzl(entry.getKey()) || (string = zzst.zza(zzrt2, entry.getKey())) == null) continue;
            object2.put(string, zzst.zzb(zzrt2, entry.getValue()));
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzafk)) {
            zztg.zzc(object2, "_v", this.zzafk);
            if (this.zzafk.equals("ma4.0.0") || this.zzafk.equals("ma4.0.1")) {
                object2.remove("adid");
            }
        }
        this.zzFs = Collections.unmodifiableMap(object2);
    }

    public static zzst zza(zzrt zzrt2, zzst zzst2, Map<String, String> map) {
        return new zzst(zzrt2, map, zzst2.zzpq(), zzst2.zzps(), zzst2.zzpp(), zzst2.zzpo(), zzst2.zzpr());
    }

    private static String zza(zzrt zzrt2, Object object) {
        Object object2;
        if (object == null) {
            return null;
        }
        object = object2 = object.toString();
        if (object2.startsWith("&")) {
            object = object2.substring(1);
        }
        int n = object.length();
        object2 = object;
        if (n > 256) {
            object2 = object.substring(0, 256);
            zzrt2.zzc("Hit param name is too long and will be trimmed", n, object2);
        }
        if (TextUtils.isEmpty((CharSequence)object2)) {
            return null;
        }
        return object2;
    }

    private static String zzb(zzrt zzrt2, Object object) {
        object = object == null ? "" : object.toString();
        int n = object.length();
        Object object2 = object;
        if (n > 8192) {
            object2 = object.substring(0, 8192);
            zzrt2.zzc("Hit param value is too long and will be trimmed", n, object2);
        }
        return object2;
    }

    private static boolean zzl(Object object) {
        if (object == null) {
            return false;
        }
        return object.toString().startsWith("&");
    }

    private String zzr(String string, String string2) {
        zzac.zzdv(string);
        zzac.zzb(string.startsWith("&") ^ true, (Object)"Short param name required");
        string = this.zzFs.get(string);
        if (string != null) {
            return string;
        }
        return string2;
    }

    private static String zzs(List<zzsf> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                zzsf zzsf2 = (zzsf)object.next();
                if (!"appendVersion".equals(zzsf2.getId())) continue;
                object = zzsf2.getValue();
                break;
            }
        } else {
            object = null;
        }
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        return object;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=");
        stringBuffer.append(this.zzafh);
        if (this.zzafg != 0L) {
            stringBuffer.append(", dbId=");
            stringBuffer.append(this.zzafg);
        }
        if (this.zzafi != 0) {
            stringBuffer.append(", appUID=");
            stringBuffer.append(this.zzafi);
        }
        Object object = new ArrayList<String>(this.zzFs.keySet());
        Collections.sort(object);
        object = object.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            stringBuffer.append(", ");
            stringBuffer.append(string);
            stringBuffer.append("=");
            stringBuffer.append(this.zzFs.get(string));
        }
        return stringBuffer.toString();
    }

    public Map<String, String> zzfz() {
        return this.zzFs;
    }

    public int zzpo() {
        return this.zzafi;
    }

    public long zzpp() {
        return this.zzafg;
    }

    public long zzpq() {
        return this.zzafh;
    }

    public List<zzsf> zzpr() {
        return this.zzaff;
    }

    public boolean zzps() {
        return this.zzafj;
    }

    public long zzpt() {
        return zztg.zzce(this.zzr("_s", "0"));
    }

    public String zzpu() {
        return this.zzr("_m", "");
    }
}
