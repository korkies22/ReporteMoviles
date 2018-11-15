/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public static class DataHolder.zza {
    private final HashMap<Object, Integer> zzaCA;
    private boolean zzaCB;
    private String zzaCC;
    private final String[] zzaCq;
    private final ArrayList<HashMap<String, Object>> zzaCy;
    private final String zzaCz;

    private DataHolder.zza(String[] arrstring, String string) {
        this.zzaCq = zzac.zzw(arrstring);
        this.zzaCy = new ArrayList();
        this.zzaCz = string;
        this.zzaCA = new HashMap();
        this.zzaCB = false;
        this.zzaCC = null;
    }

    static /* synthetic */ String[] zza(DataHolder.zza zza2) {
        return zza2.zzaCq;
    }

    static /* synthetic */ ArrayList zzb(DataHolder.zza zza2) {
        return zza2.zzaCy;
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

    public DataHolder.zza zza(ContentValues object) {
        zzc.zzt(object);
        HashMap<String, Object> hashMap = new HashMap<String, Object>(object.size());
        for (Map.Entry entry : object.valueSet()) {
            hashMap.put((String)entry.getKey(), entry.getValue());
        }
        return this.zzb(hashMap);
    }

    public DataHolder.zza zzb(HashMap<String, Object> hashMap) {
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
        return new DataHolder(this, n, null, null);
    }
}
