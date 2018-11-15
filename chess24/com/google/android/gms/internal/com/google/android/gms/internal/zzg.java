/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.net.TrafficStats
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Process
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzf;
import com.google.android.gms.internal.zzi;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzm;
import com.google.android.gms.internal.zzn;
import com.google.android.gms.internal.zzr;
import com.google.android.gms.internal.zzs;
import java.util.concurrent.BlockingQueue;

public class zzg
extends Thread {
    private final zzb zzi;
    private final zzn zzj;
    private volatile boolean zzk = false;
    private final BlockingQueue<zzk<?>> zzw;
    private final zzf zzx;

    public zzg(BlockingQueue<zzk<?>> blockingQueue, zzf zzf2, zzb zzb2, zzn zzn2) {
        super("VolleyNetworkDispatcher");
        this.zzw = blockingQueue;
        this.zzx = zzf2;
        this.zzi = zzb2;
        this.zzj = zzn2;
    }

    @TargetApi(value=14)
    private void zzb(zzk<?> zzk2) {
        if (Build.VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag((int)zzk2.zzf());
        }
    }

    private void zzb(zzk<?> zzk2, zzr zzr2) {
        zzr2 = zzk2.zzb(zzr2);
        this.zzj.zza(zzk2, zzr2);
    }

    public void quit() {
        this.zzk = true;
        this.interrupt();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Process.setThreadPriority((int)10);
        do {
            zzm<?> zzm2;
            zzk<?> zzk2;
            long l = SystemClock.elapsedRealtime();
            try {
                zzk2 = this.zzw.take();
            }
            catch (InterruptedException interruptedException) {}
            try {
                zzk2.zzc("network-queue-take");
                this.zzb(zzk2);
                zzm2 = this.zzx.zza(zzk2);
                zzk2.zzc("network-http-complete");
                if (zzm2.zzz && zzk2.zzs()) {
                    zzk2.zzd("not-modified");
                    continue;
                }
                zzm2 = zzk2.zza((zzi)((Object)zzm2));
                zzk2.zzc("network-parse-complete");
                if (zzk2.zzn() && zzm2.zzae != null) {
                    this.zzi.zza(zzk2.zzg(), zzm2.zzae);
                    zzk2.zzc("network-cache-written");
                }
                zzk2.zzr();
                this.zzj.zza(zzk2, zzm2);
            }
            catch (Exception exception) {
                zzs.zza(exception, "Unhandled exception %s", exception.toString());
                zzm2 = new zzr(exception);
                zzm2.zza(SystemClock.elapsedRealtime() - l);
                this.zzj.zza(zzk2, (zzr)((Object)zzm2));
            }
            catch (zzr zzr2) {
                zzr2.zza(SystemClock.elapsedRealtime() - l);
                this.zzb(zzk2, zzr2);
            }
            continue;
            if (this.zzk) break;
        } while (true);
    }
}
