/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Message
 */
package com.google.android.gms.internal;

import android.os.Message;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzzx;

class zzabp
implements Runnable {
    final /* synthetic */ Result zzaBT;

    zzabp(Result result) {
        this.zzaBT = result;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @WorkerThread
    @Override
    public void run() {
        zzzx.zzayN.set(true);
        object = com.google.android.gms.internal.zzabp.zzc(zzabp.this).onSuccess(this.zzaBT);
        com.google.android.gms.internal.zzabp.zzd(zzabp.this).sendMessage(com.google.android.gms.internal.zzabp.zzd(zzabp.this).obtainMessage(0, object));
        zzzx.zzayN.set(false);
        com.google.android.gms.internal.zzabp.zza(zzabp.this, this.zzaBT);
        object = (GoogleApiClient)com.google.android.gms.internal.zzabp.zze(zzabp.this).get();
        if (object == null) return;
lbl9: // 2 sources:
        do {
            object.zzb(zzabp.this);
            return;
            break;
        } while (true);
        {
            catch (Throwable throwable22) {
            }
            catch (RuntimeException runtimeException) {}
            {
                com.google.android.gms.internal.zzabp.zzd(zzabp.this).sendMessage(com.google.android.gms.internal.zzabp.zzd(zzabp.this).obtainMessage(1, (Object)runtimeException));
                zzzx.zzayN.set(false);
            }
            com.google.android.gms.internal.zzabp.zza(zzabp.this, this.zzaBT);
            object = (GoogleApiClient)com.google.android.gms.internal.zzabp.zze(zzabp.this).get();
            if (object == null) return;
            ** continue;
        }
        zzzx.zzayN.set(false);
        com.google.android.gms.internal.zzabp.zza(zzabp.this, this.zzaBT);
        googleApiClient = (GoogleApiClient)com.google.android.gms.internal.zzabp.zze(zzabp.this).get();
        if (googleApiClient == null) throw throwable22;
        googleApiClient.zzb(zzabp.this);
        throw throwable22;
    }
}
