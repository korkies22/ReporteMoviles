/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzct;
import com.google.android.gms.tagmanager.zzp;
import com.google.android.gms.tagmanager.zzt;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class zzcu
implements zzp.zze {
    private boolean mClosed;
    private final Context mContext;
    private final String zzbCS;
    private String zzbDq;
    private zzbn<zzai.zzj> zzbFr;
    private zzt zzbFs;
    private final ScheduledExecutorService zzbFu;
    private final zza zzbFv;
    private ScheduledFuture<?> zzbFw;

    public zzcu(Context context, String string, zzt zzt2) {
        this(context, string, zzt2, null, null);
    }

    zzcu(Context object, String string, zzt zzt2, zzb zzb2, zza zza2) {
        this.zzbFs = zzt2;
        this.mContext = object;
        this.zzbCS = string;
        object = zzb2;
        if (zzb2 == null) {
            object = new zzb(this){

                @Override
                public ScheduledExecutorService zzPG() {
                    return Executors.newSingleThreadScheduledExecutor();
                }
            };
        }
        this.zzbFu = object.zzPG();
        if (zza2 == null) {
            this.zzbFv = new zza(){

                @Override
                public zzct zza(zzt zzt2) {
                    return new zzct(zzcu.this.mContext, zzcu.this.zzbCS, zzt2);
                }
            };
            return;
        }
        this.zzbFv = zza2;
    }

    private void zzPF() {
        synchronized (this) {
            if (this.mClosed) {
                throw new IllegalStateException("called method after closed");
            }
            return;
        }
    }

    private zzct zzhs(String string) {
        zzct zzct2 = this.zzbFv.zza(this.zzbFs);
        zzct2.zza(this.zzbFr);
        zzct2.zzhc(this.zzbDq);
        zzct2.zzhr(string);
        return zzct2;
    }

    @Override
    public void release() {
        synchronized (this) {
            this.zzPF();
            if (this.zzbFw != null) {
                this.zzbFw.cancel(false);
            }
            this.zzbFu.shutdown();
            this.mClosed = true;
            return;
        }
    }

    @Override
    public void zza(zzbn<zzai.zzj> zzbn2) {
        synchronized (this) {
            this.zzPF();
            this.zzbFr = zzbn2;
            return;
        }
    }

    @Override
    public void zzf(long l, String string) {
        synchronized (this) {
            String string2 = this.zzbCS;
            StringBuilder stringBuilder = new StringBuilder(55 + String.valueOf(string2).length());
            stringBuilder.append("loadAfterDelay: containerId=");
            stringBuilder.append(string2);
            stringBuilder.append(" delay=");
            stringBuilder.append(l);
            zzbo.v(stringBuilder.toString());
            this.zzPF();
            if (this.zzbFr == null) {
                throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
            }
            if (this.zzbFw != null) {
                this.zzbFw.cancel(false);
            }
            this.zzbFw = this.zzbFu.schedule(this.zzhs(string), l, TimeUnit.MILLISECONDS);
            return;
        }
    }

    @Override
    public void zzhc(String string) {
        synchronized (this) {
            this.zzPF();
            this.zzbDq = string;
            return;
        }
    }

    static interface zza {
        public zzct zza(zzt var1);
    }

    static interface zzb {
        public ScheduledExecutorService zzPG();
    }

}
