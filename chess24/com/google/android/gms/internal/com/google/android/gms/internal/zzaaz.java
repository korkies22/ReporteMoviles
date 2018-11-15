/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;

public final class zzaaz<L> {
    private volatile L mListener;
    private final zza zzaBy;
    private final zzb<L> zzaBz;

    zzaaz(@NonNull Looper looper, @NonNull L l, @NonNull String string) {
        this.zzaBy = new zza(looper);
        this.mListener = zzac.zzb(l, (Object)"Listener must not be null");
        this.zzaBz = new zzb<L>(l, zzac.zzdv(string));
    }

    public void clear() {
        this.mListener = null;
    }

    public void zza(zzc<? super L> message) {
        zzac.zzb(message, (Object)"Notifier must not be null");
        message = this.zzaBy.obtainMessage(1, message);
        this.zzaBy.sendMessage(message);
    }

    void zzb(zzc<? super L> zzc2) {
        L l = this.mListener;
        if (l == null) {
            zzc2.zzvy();
            return;
        }
        try {
            zzc2.zzs(l);
            return;
        }
        catch (RuntimeException runtimeException) {
            zzc2.zzvy();
            throw runtimeException;
        }
    }

    @NonNull
    public zzb<L> zzwp() {
        return this.zzaBz;
    }

    private final class zza
    extends Handler {
        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int n = message.what;
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            zzac.zzas(bl);
            zzaaz.this.zzb((zzc)message.obj);
        }
    }

    public static final class zzb<L> {
        private final L mListener;
        private final String zzaBB;

        zzb(L l, String string) {
            this.mListener = l;
            this.zzaBB = string;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof zzb)) {
                return false;
            }
            object = (zzb)object;
            if (this.mListener == object.mListener && this.zzaBB.equals(object.zzaBB)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return 31 * System.identityHashCode(this.mListener) + this.zzaBB.hashCode();
        }
    }

    public static interface zzc<L> {
        public void zzs(L var1);

        public void zzvy();
    }

}
