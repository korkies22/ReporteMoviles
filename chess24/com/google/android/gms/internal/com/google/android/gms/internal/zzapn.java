/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzapq;
import com.google.android.gms.internal.zzapr;

public abstract class zzapn<T> {
    private final int zzAG;
    private final String zzAH;
    private final T zzAI;

    private zzapn(int n, String string, T t) {
        this.zzAG = n;
        this.zzAH = string;
        this.zzAI = t;
        zzapr.zzCQ().zza(this);
    }

    public static zza zzb(int n, String string, Boolean bl) {
        return new zza(n, string, bl);
    }

    public static zzb zzb(int n, String string, int n2) {
        return new zzb(n, string, n2);
    }

    public static zzc zzb(int n, String string, long l) {
        return new zzc(n, string, l);
    }

    public static zzd zzc(int n, String string, String string2) {
        return new zzd(n, string, string2);
    }

    public T get() {
        return zzapr.zzCR().zzb(this);
    }

    public String getKey() {
        return this.zzAH;
    }

    public int getSource() {
        return this.zzAG;
    }

    protected abstract T zza(zzapq var1);

    public T zzfm() {
        return this.zzAI;
    }

    public static class zza
    extends zzapn<Boolean> {
        public zza(int n, String string, Boolean bl) {
            super(n, string, bl);
        }

        @Override
        public /* synthetic */ Object zza(zzapq zzapq2) {
            return this.zzb(zzapq2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Boolean zzb(zzapq zzapq2) {
            boolean bl;
            try {
                bl = zzapq2.getBooleanFlagValue(this.getKey(), (Boolean)this.zzfm(), this.getSource());
            }
            catch (RemoteException remoteException) {
                return (Boolean)this.zzfm();
            }
            return bl;
        }
    }

    public static class zzb
    extends zzapn<Integer> {
        public zzb(int n, String string, Integer n2) {
            super(n, string, n2);
        }

        @Override
        public /* synthetic */ Object zza(zzapq zzapq2) {
            return this.zzc(zzapq2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Integer zzc(zzapq zzapq2) {
            int n;
            try {
                n = zzapq2.getIntFlagValue(this.getKey(), (Integer)this.zzfm(), this.getSource());
            }
            catch (RemoteException remoteException) {
                return (Integer)this.zzfm();
            }
            return n;
        }
    }

    public static class zzc
    extends zzapn<Long> {
        public zzc(int n, String string, Long l) {
            super(n, string, l);
        }

        @Override
        public /* synthetic */ Object zza(zzapq zzapq2) {
            return this.zzd(zzapq2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Long zzd(zzapq zzapq2) {
            long l;
            try {
                l = zzapq2.getLongFlagValue(this.getKey(), (Long)this.zzfm(), this.getSource());
            }
            catch (RemoteException remoteException) {
                return (Long)this.zzfm();
            }
            return l;
        }
    }

    public static class zzd
    extends zzapn<String> {
        public zzd(int n, String string, String string2) {
            super(n, string, string2);
        }

        @Override
        public /* synthetic */ Object zza(zzapq zzapq2) {
            return this.zze(zzapq2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public String zze(zzapq object) {
            try {
                return object.getStringFlagValue(this.getKey(), (String)this.zzfm(), this.getSource());
            }
            catch (RemoteException remoteException) {
                return (String)this.zzfm();
            }
        }
    }

}
