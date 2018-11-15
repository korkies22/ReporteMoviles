/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.tagmanager.InstallReferrerService;
import com.google.android.gms.tagmanager.zzbf;

public final class InstallReferrerReceiver
extends CampaignTrackingReceiver {
    @Override
    protected Class<? extends CampaignTrackingService> zzlR() {
        return InstallReferrerService.class;
    }

    @Override
    protected void zzp(Context context, String string) {
        zzbf.zzhn(string);
        zzbf.zzG(context, string);
    }
}
