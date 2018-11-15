/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzh;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzth;

public class zzsb
extends zzru {
    private final zzre zzabk = new zzre();

    zzsb(zzrw zzrw2) {
        super(zzrw2);
    }

    public void zzlV() {
        Object object = this.zzma();
        String string = object.zzmx();
        if (string != null) {
            this.zzabk.setAppName(string);
        }
        if ((object = object.zzmy()) != null) {
            this.zzabk.setAppVersion((String)object);
        }
    }

    @Override
    protected void zzmr() {
        this.zznt().zzmo().zza(this.zzabk);
        this.zzlV();
    }

    public zzre zznX() {
        this.zznA();
        return this.zzabk;
    }
}
