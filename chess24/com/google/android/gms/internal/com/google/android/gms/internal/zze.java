/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzm;
import com.google.android.gms.internal.zzn;
import com.google.android.gms.internal.zzr;
import java.util.concurrent.Executor;

public class zze
implements zzn {
    private final Executor zzr;

    public zze(final Handler handler) {
        this.zzr = new Executor(this){

            @Override
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        };
    }

    @Override
    public void zza(zzk<?> zzk2, zzm<?> zzm2) {
        this.zza(zzk2, zzm2, null);
    }

    @Override
    public void zza(zzk<?> zzk2, zzm<?> zzm2, Runnable runnable) {
        zzk2.zzr();
        zzk2.zzc("post-response");
        this.zzr.execute(new zza(this, zzk2, zzm2, runnable));
    }

    @Override
    public void zza(zzk<?> zzk2, zzr object) {
        zzk2.zzc("post-error");
        object = zzm.zzd((zzr)object);
        this.zzr.execute(new zza(this, zzk2, (zzm)object, null));
    }

    private class zza
    implements Runnable {
        private final zzk zzt;
        private final zzm zzu;
        private final Runnable zzv;

        public zza(zze zze2, zzk zzk2, zzm zzm2, Runnable runnable) {
            this.zzt = zzk2;
            this.zzu = zzm2;
            this.zzv = runnable;
        }

        @Override
        public void run() {
            if (this.zzu.isSuccess()) {
                this.zzt.zza(this.zzu.result);
            } else {
                this.zzt.zzc(this.zzu.zzaf);
            }
            if (this.zzu.zzag) {
                this.zzt.zzc("intermediate-response");
            } else {
                this.zzt.zzd("done");
            }
            if (this.zzv != null) {
                this.zzv.run();
            }
        }
    }

}
