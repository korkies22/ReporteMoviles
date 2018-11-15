/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztc;

class zztc
implements zzso {
    final /* synthetic */ zzsx zzaay;
    final /* synthetic */ int zzaaz;
    final /* synthetic */ zzrw zzafJ;

    zztc(int n, zzrw zzrw2, zzsx zzsx2) {
        this.zzaaz = n;
        this.zzafJ = zzrw2;
        this.zzaay = zzsx2;
    }

    @Override
    public void zzf(Throwable throwable) {
        zztc.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                if (zztc.this.zzafI.callServiceStopSelfResult(1.this.zzaaz)) {
                    1.this.zzaay.zzbO("Local AnalyticsService processed last dispatch request");
                }
            }
        });
    }

}
