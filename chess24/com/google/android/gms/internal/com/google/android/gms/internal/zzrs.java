/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzrx;
import com.google.android.gms.internal.zzry;
import com.google.android.gms.internal.zzsc;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zztb;
import com.google.android.gms.internal.zztc;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzrs
extends zzru {
    private final zzsc zzacF;

    public zzrs(zzrw zzrw2, zzrx zzrx2) {
        super(zzrw2);
        zzac.zzw(zzrx2);
        this.zzacF = zzrx2.zzj(zzrw2);
    }

    void onServiceConnected() {
        this.zzmq();
        this.zzacF.onServiceConnected();
    }

    public void setLocalDispatchPeriod(final int n) {
        this.zznA();
        this.zzb("setLocalDispatchPeriod (sec)", n);
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzrs.this.zzacF.zzw((long)n * 1000L);
            }
        });
    }

    public void start() {
        this.zzacF.start();
    }

    public void zzV(final boolean bl) {
        this.zza("Network connectivity status changed", bl);
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzrs.this.zzacF.zzV(bl);
            }
        });
    }

    public long zza(zzry zzry2) {
        this.zznA();
        zzac.zzw(zzry2);
        this.zzmq();
        long l = this.zzacF.zza(zzry2, true);
        if (l == 0L) {
            this.zzacF.zzc(zzry2);
        }
        return l;
    }

    public void zza(final zzso zzso2) {
        this.zznA();
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzrs.this.zzacF.zzb(zzso2);
            }
        });
    }

    public void zza(final zzst zzst2) {
        zzac.zzw(zzst2);
        this.zznA();
        this.zzb("Hit delivery requested", zzst2);
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzrs.this.zzacF.zza(zzst2);
            }
        });
    }

    public void zza(final String string, final Runnable runnable) {
        zzac.zzh(string, "campaign param can't be empty");
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzrs.this.zzacF.zzbW(string);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    @Override
    protected void zzmr() {
        this.zzacF.initialize();
    }

    public void zznj() {
        this.zznA();
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzrs.this.zzacF.zznj();
            }
        });
    }

    public void zznk() {
        this.zznA();
        Context context = this.getContext();
        if (zztb.zzT(context) && zztc.zzU(context)) {
            Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            context.startService(intent);
            return;
        }
        this.zza((zzso)null);
    }

    public boolean zznl() {
        this.zznA();
        Future future = this.zznt().zzc(new Callable<Void>(){

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.zzbl();
            }

            public Void zzbl() throws Exception {
                zzrs.this.zzacF.zzof();
                return null;
            }
        });
        try {
            future.get(4L, TimeUnit.SECONDS);
            return true;
        }
        catch (TimeoutException timeoutException) {
            this.zzd("syncDispatchLocalHits timed out", timeoutException);
            return false;
        }
        catch (ExecutionException executionException) {
            this.zze("syncDispatchLocalHits failed", executionException);
            return false;
        }
        catch (InterruptedException interruptedException) {
            this.zzd("syncDispatchLocalHits interrupted", interruptedException);
            return false;
        }
    }

    public void zznm() {
        this.zznA();
        zzh.zzmq();
        this.zzacF.zznm();
    }

    public void zznn() {
        this.zzbO("Radio powered up");
        this.zznk();
    }

    void zzno() {
        this.zzmq();
        this.zzacF.zzno();
    }

}
