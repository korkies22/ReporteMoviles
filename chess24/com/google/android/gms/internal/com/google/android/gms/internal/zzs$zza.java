/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.os.SystemClock;
import com.google.android.gms.internal.zzs;
import java.util.ArrayList;
import java.util.List;

static class zzs.zza {
    public static final boolean zzai = zzs.DEBUG;
    private final List<zza> zzaj = new ArrayList<zza>();
    private boolean zzak = false;

    zzs.zza() {
    }

    private long getTotalDuration() {
        if (this.zzaj.size() == 0) {
            return 0L;
        }
        long l = this.zzaj.get((int)0).time;
        return this.zzaj.get((int)(this.zzaj.size() - 1)).time - l;
    }

    protected void finalize() throws Throwable {
        if (!this.zzak) {
            this.zzd("Request on the loose");
            zzs.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
        }
    }

    public void zza(String string, long l) {
        synchronized (this) {
            if (this.zzak) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzaj.add(new zza(string, l, SystemClock.elapsedRealtime()));
            return;
        }
    }

    public void zzd(String object) {
        synchronized (this) {
            long l;
            block5 : {
                this.zzak = true;
                l = this.getTotalDuration();
                if (l > 0L) break block5;
                return;
            }
            long l2 = this.zzaj.get((int)0).time;
            zzs.zzb("(%-4d ms) %s", l, object);
            for (zza zza2 : this.zzaj) {
                l = zza2.time;
                zzs.zzb("(+%-4d) [%2d] %s", l - l2, zza2.zzal, zza2.name);
                l2 = l;
            }
            return;
        }
    }

    private static class zza {
        public final String name;
        public final long time;
        public final long zzal;

        public zza(String string, long l, long l2) {
            this.name = string;
            this.zzal = l;
            this.time = l2;
        }
    }

}
