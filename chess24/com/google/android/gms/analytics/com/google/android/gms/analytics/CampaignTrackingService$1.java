/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.google.android.gms.analytics;

import android.os.Handler;
import com.google.android.gms.internal.zzsx;

class CampaignTrackingService
implements Runnable {
    final /* synthetic */ zzsx zzaay;
    final /* synthetic */ int zzaaz;
    final /* synthetic */ Handler zzs;

    CampaignTrackingService(zzsx zzsx2, Handler handler, int n) {
        this.zzaay = zzsx2;
        this.zzs = handler;
        this.zzaaz = n;
    }

    @Override
    public void run() {
        CampaignTrackingService.this.zza(this.zzaay, this.zzs, this.zzaaz);
    }
}
