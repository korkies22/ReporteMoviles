/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzh;
import com.google.android.gms.internal.zzrj;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;

public class zzsm
extends zzru {
    zzsm(zzrw zzrw2) {
        super(zzrw2);
    }

    @Override
    protected void zzmr() {
    }

    public zzrj zzpb() {
        this.zznA();
        return this.zznt().zzmp();
    }

    public String zzpc() {
        this.zznA();
        Object object = this.zzpb();
        int n = object.zzmK();
        int n2 = object.zzmL();
        object = new StringBuilder(23);
        object.append(n);
        object.append("x");
        object.append(n2);
        return object.toString();
    }
}
