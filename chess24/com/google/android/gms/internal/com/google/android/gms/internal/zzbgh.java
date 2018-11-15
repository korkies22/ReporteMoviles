/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzbgj;
import java.util.HashMap;
import java.util.Map;

public class zzbgh {
    private final Context mContext;
    private String zzbDw = null;
    Map<String, Object> zzbJY = new HashMap<String, Object>();
    private final Map<String, Object> zzbJZ;
    private final zzbgj zzbLj;
    private final zze zzuI;

    public zzbgh(Context context) {
        this(context, new HashMap<String, Object>(), new zzbgj(context), zzh.zzyv());
    }

    zzbgh(Context context, Map<String, Object> map, zzbgj zzbgj2, zze zze2) {
        this.mContext = context;
        this.zzuI = zze2;
        this.zzbLj = zzbgj2;
        this.zzbJZ = map;
    }

    public void zzij(String string) {
        this.zzbDw = string;
    }
}
