/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzayd;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztg;

public class CampaignTrackingService
extends Service {
    private static Boolean zzaax;
    private Handler mHandler;

    private Handler getHandler() {
        Handler handler;
        Handler handler2 = handler = this.mHandler;
        if (handler == null) {
            this.mHandler = handler2 = new Handler(this.getMainLooper());
        }
        return handler2;
    }

    public static boolean zzU(Context context) {
        zzac.zzw(context);
        if (zzaax != null) {
            return zzaax;
        }
        boolean bl = zztg.zzr(context, "com.google.android.gms.analytics.CampaignTrackingService");
        zzaax = bl;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzlS() {
        zzayd zzayd2;
        try {
            Object object = CampaignTrackingReceiver.zztU;
            synchronized (object) {
                zzayd2 = CampaignTrackingReceiver.zzaav;
            }
        }
        catch (SecurityException securityException) {
            return;
        }
        {
            if (zzayd2 != null && zzayd2.isHeld()) {
                zzayd2.release();
            }
            return;
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        super.onCreate();
        zzrw.zzW((Context)this).zznr().zzbO("CampaignTrackingService is starting up");
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzrw.zzW((Context)this).zznr().zzbO("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent object, int n, final int n2) {
        this.zzlS();
        zzrw zzrw2 = zzrw.zzW((Context)this);
        final zzsx zzsx2 = zzrw2.zznr();
        object = object.getStringExtra("referrer");
        final Handler handler = this.getHandler();
        if (TextUtils.isEmpty((CharSequence)object)) {
            zzsx2.zzbR("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            zzrw2.zznt().zzg(new Runnable(){

                @Override
                public void run() {
                    CampaignTrackingService.this.zza(zzsx2, handler, n2);
                }
            });
            return 2;
        }
        n = zzrw2.zzns().zzoz();
        if (object.length() > n) {
            zzsx2.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", object.length(), n);
            object = object.substring(0, n);
        }
        zzsx2.zza("CampaignTrackingService called. startId, campaign", n2, object);
        zzrw2.zzlZ().zza((String)object, new Runnable(){

            @Override
            public void run() {
                CampaignTrackingService.this.zza(zzsx2, handler, n2);
            }
        });
        return 2;
    }

    protected void zza(final zzsx zzsx2, Handler handler, final int n) {
        handler.post(new Runnable(){

            @Override
            public void run() {
                boolean bl = CampaignTrackingService.this.stopSelfResult(n);
                if (bl) {
                    zzsx2.zza("Install campaign broadcast processed", bl);
                }
            }
        });
    }

}
