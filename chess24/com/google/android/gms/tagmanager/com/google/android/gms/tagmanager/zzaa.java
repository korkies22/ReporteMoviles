/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.zzat;
import com.google.android.gms.tagmanager.zzau;
import com.google.android.gms.tagmanager.zzav;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcl;
import com.google.android.gms.tagmanager.zzda;

public class zzaa
implements zzat {
    private static final Object zzbCG = new Object();
    private static zzaa zzbDU;
    private zzau zzbDV;
    private zzcl zzbDi;

    private zzaa(Context context) {
        this(zzav.zzbI(context), new zzda());
    }

    zzaa(zzau zzau2, zzcl zzcl2) {
        this.zzbDV = zzau2;
        this.zzbDi = zzcl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzat zzbB(Context object) {
        Object object2 = zzbCG;
        synchronized (object2) {
            if (zzbDU != null) return zzbDU;
            zzbDU = new zzaa((Context)object);
            return zzbDU;
        }
    }

    @Override
    public boolean zzhi(String string) {
        if (!this.zzbDi.zzpv()) {
            zzbo.zzbe("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        this.zzbDV.zzhm(string);
        return true;
    }
}
