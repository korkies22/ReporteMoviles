/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.zzau;
import com.google.android.gms.tagmanager.zzaw;
import com.google.android.gms.tagmanager.zzdc;

class zzav
implements Runnable {
    final /* synthetic */ zzau zzbEq;
    final /* synthetic */ long zzbEr;
    final /* synthetic */ String zzsk;

    zzav(zzau zzau2, long l, String string) {
        this.zzbEq = zzau2;
        this.zzbEr = l;
        this.zzsk = string;
    }

    @Override
    public void run() {
        if (zzav.this.zzbEp == null) {
            zzdc zzdc2 = zzdc.zzPT();
            zzdc2.zza(zzav.this.mContext, this.zzbEq);
            zzav.this.zzbEp = zzdc2.zzPW();
        }
        zzav.this.zzbEp.zzg(this.zzbEr, this.zzsk);
    }
}
