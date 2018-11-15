/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzf;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class zzabq {
    public static final Status zzaBV = new Status(8, "The connection to Google Play services was lost");
    private static final zzzx<?>[] zzaBW = new zzzx[0];
    private final Map<Api.zzc<?>, Api.zze> zzaAr;
    final Set<zzzx<?>> zzaBX = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzb zzaBY = new zzb(){

        @Override
        public void zzc(zzzx<?> zzzx2) {
            zzabq.this.zzaBX.remove(zzzx2);
            if (zzzx2.zzuR() != null) {
                zzabq.zza(zzabq.this);
            }
        }
    };

    public zzabq(Map<Api.zzc<?>, Api.zze> map) {
        this.zzaAr = map;
    }

    static /* synthetic */ zzf zza(zzabq zzabq2) {
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void zza(zzzx<?> zzzx2, zzf zzf2, IBinder iBinder) {
        block4 : {
            if (zzzx2.isReady()) {
                zzzx2.zza(new zza(zzzx2, zzf2, iBinder));
                return;
            }
            if (iBinder != null && iBinder.isBinderAlive()) {
                zza zza2 = new zza(zzzx2, zzf2, iBinder);
                zzzx2.zza(zza2);
                iBinder.linkToDeath((IBinder.DeathRecipient)zza2, 0);
                return;
            }
            zzzx2.zza((zzb)null);
            break block4;
            catch (RemoteException remoteException) {}
        }
        zzzx2.cancel();
        zzf2.remove(zzzx2.zzuR());
    }

    public void dump(PrintWriter printWriter) {
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zzaBX.size());
    }

    public void release() {
        for (zzzx<?> zzzx2 : this.zzaBX.toArray(zzaBW)) {
            zzzx2.zza((zzb)null);
            if (zzzx2.zzuR() == null) {
                if (!zzzx2.zzvc()) continue;
            } else {
                zzzx2.zzve();
                zzabq.zza(zzzx2, null, this.zzaAr.get(((zzzv.zza)zzzx2).zzuH()).zzuJ());
            }
            this.zzaBX.remove(zzzx2);
        }
    }

    void zzb(zzzx<? extends Result> zzzx2) {
        this.zzaBX.add(zzzx2);
        zzzx2.zza(this.zzaBY);
    }

    public void zzww() {
        zzzx<?>[] arrzzzx = this.zzaBX.toArray(zzaBW);
        int n = arrzzzx.length;
        for (int i = 0; i < n; ++i) {
            arrzzzx[i].zzB(zzaBV);
        }
    }

    private static class zza
    implements IBinder.DeathRecipient,
    zzb {
        private final WeakReference<zzzx<?>> zzaCa;
        private final WeakReference<zzf> zzaCb;
        private final WeakReference<IBinder> zzaCc;

        private zza(zzzx<?> zzzx2, zzf zzf2, IBinder iBinder) {
            this.zzaCb = new WeakReference<zzf>(zzf2);
            this.zzaCa = new WeakReference(zzzx2);
            this.zzaCc = new WeakReference<IBinder>(iBinder);
        }

        private void zzwx() {
            zzzx zzzx2 = (zzzx)this.zzaCa.get();
            zzf zzf2 = (zzf)this.zzaCb.get();
            if (zzf2 != null && zzzx2 != null) {
                zzf2.remove(zzzx2.zzuR());
            }
            if ((zzzx2 = (IBinder)this.zzaCc.get()) != null) {
                zzzx2.unlinkToDeath((IBinder.DeathRecipient)this, 0);
            }
        }

        public void binderDied() {
            this.zzwx();
        }

        @Override
        public void zzc(zzzx<?> zzzx2) {
            this.zzwx();
        }
    }

    static interface zzb {
        public void zzc(zzzx<?> var1);
    }

}
