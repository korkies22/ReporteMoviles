/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.internal.zzsx;

class CampaignTrackingService
implements Runnable {
    final /* synthetic */ zzsx zzaay;
    final /* synthetic */ int zzaaz;

    CampaignTrackingService(int n, zzsx zzsx2) {
        this.zzaaz = n;
        this.zzaay = zzsx2;
    }

    @Override
    public void run() {
        boolean bl = CampaignTrackingService.this.stopSelfResult(this.zzaaz);
        if (bl) {
            this.zzaay.zza("Install campaign broadcast processed", bl);
        }
    }
}
