/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzi;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzm;
import com.google.android.gms.internal.zzn;
import com.google.android.gms.internal.zzs;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class zzc
extends Thread {
    private static final boolean DEBUG = zzs.DEBUG;
    private final BlockingQueue<zzk<?>> zzg;
    private final BlockingQueue<zzk<?>> zzh;
    private final zzb zzi;
    private final zzn zzj;
    private volatile boolean zzk = false;

    public zzc(BlockingQueue<zzk<?>> blockingQueue, BlockingQueue<zzk<?>> blockingQueue2, zzb zzb2, zzn zzn2) {
        super("VolleyCacheDispatcher");
        this.zzg = blockingQueue;
        this.zzh = blockingQueue2;
        this.zzi = zzb2;
        this.zzj = zzn2;
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
        if (DEBUG) {
            zzs.zza("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority((int)10);
        this.zzi.initialize();
        do {
            try {
                do {
                    zzk<?> zzk2;
                    zzb.zza zza2;
                    block11 : {
                        block10 : {
                            block9 : {
                                zzk2 = this.zzg.take();
                                zzk2.zzc("cache-queue-take");
                                zza2 = this.zzi.zza(zzk2.zzg());
                                if (zza2 != null) break block9;
                                zzk2.zzc("cache-miss");
                                break block10;
                            }
                            if (!zza2.zza()) break block11;
                            zzk2.zzc("cache-hit-expired");
                            zzk2.zza(zza2);
                        }
                        this.zzh.put(zzk2);
                        continue;
                    }
                    zzk2.zzc("cache-hit");
                    zzm<?> zzm2 = zzk2.zza(new zzi(zza2.data, zza2.zzf));
                    zzk2.zzc("cache-hit-parsed");
                    if (!zza2.zzb()) {
                        this.zzj.zza(zzk2, zzm2);
                        continue;
                    }
                    zzk2.zzc("cache-hit-refresh-needed");
                    zzk2.zza(zza2);
                    zzm2.zzag = true;
                    this.zzj.zza(zzk2, zzm2, new Runnable(){

                        @Override
                        public void run() {
                            try {
                                zzc.this.zzh.put(zzk2);
                                return;
                            }
                            catch (InterruptedException interruptedException) {
                                return;
                            }
                        }
                    });
                } while (true);
            }
            catch (InterruptedException interruptedException) {}
        } while (!this.zzk);
    }

}
