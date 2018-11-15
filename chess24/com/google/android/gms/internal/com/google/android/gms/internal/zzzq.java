/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 *  android.os.TransactionTooLargeException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzabe;
import com.google.android.gms.internal.zzabf;
import com.google.android.gms.internal.zzabk;
import com.google.android.gms.internal.zzabn;
import com.google.android.gms.internal.zzabr;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

public abstract class zzzq {
    public final int zzanR;

    public zzzq(int n) {
        this.zzanR = n;
    }

    private static Status zza(RemoteException remoteException) {
        StringBuilder stringBuilder = new StringBuilder();
        if (zzs.zzyB() && remoteException instanceof TransactionTooLargeException) {
            stringBuilder.append("TransactionTooLargeException: ");
        }
        stringBuilder.append(remoteException.getLocalizedMessage());
        return new Status(8, stringBuilder.toString());
    }

    public abstract void zza(@NonNull zzaad var1, boolean var2);

    public abstract void zza(zzaap.zza<?> var1) throws DeadObjectException;

    public abstract void zzy(@NonNull Status var1);

    private static abstract class zza
    extends zzzq {
        protected final TaskCompletionSource<Void> zzayo;

        public zza(int n, TaskCompletionSource<Void> taskCompletionSource) {
            super(n);
            this.zzayo = taskCompletionSource;
        }

        @Override
        public void zza(@NonNull zzaad zzaad2, boolean bl) {
        }

        @Override
        public final void zza(zzaap.zza<?> zza2) throws DeadObjectException {
            try {
                this.zzb(zza2);
                return;
            }
            catch (RemoteException remoteException) {
                this.zzy(zzzq.zza(remoteException));
                return;
            }
            catch (DeadObjectException deadObjectException) {
                this.zzy(zzzq.zza((RemoteException)deadObjectException));
                throw deadObjectException;
            }
        }

        protected abstract void zzb(zzaap.zza<?> var1) throws RemoteException;

        @Override
        public void zzy(@NonNull Status status) {
            this.zzayo.trySetException(new com.google.android.gms.common.api.zza(status));
        }
    }

    public static class zzb<A extends zzzv.zza<? extends Result, Api.zzb>>
    extends zzzq {
        protected final A zzayp;

        public zzb(int n, A a) {
            super(n);
            this.zzayp = a;
        }

        @Override
        public void zza(@NonNull zzaad zzaad2, boolean bl) {
            zzaad2.zza((zzzx<? extends Result>)this.zzayp, bl);
        }

        @Override
        public void zza(zzaap.zza<?> zza2) throws DeadObjectException {
            this.zzayp.zzb((Api.zze)zza2.zzvr());
        }

        @Override
        public void zzy(@NonNull Status status) {
            this.zzayp.zzA(status);
        }
    }

    public static final class zzc
    extends zza {
        public final zzabe<Api.zzb, ?> zzayq;
        public final zzabr<Api.zzb, ?> zzayr;

        public zzc(zzabf zzabf2, TaskCompletionSource<Void> taskCompletionSource) {
            super(3, taskCompletionSource);
            this.zzayq = zzabf2.zzayq;
            this.zzayr = zzabf2.zzayr;
        }

        @Override
        public void zzb(zzaap.zza<?> zza2) throws RemoteException {
            if (this.zzayq.zzwp() != null) {
                zza2.zzwc().put(this.zzayq.zzwp(), new zzabf(this.zzayq, this.zzayr));
            }
        }
    }

    public static final class zzd<TResult>
    extends zzzq {
        private final TaskCompletionSource<TResult> zzayo;
        private final zzabn<Api.zzb, TResult> zzays;
        private final zzabk zzayt;

        public zzd(int n, zzabn<Api.zzb, TResult> zzabn2, TaskCompletionSource<TResult> taskCompletionSource, zzabk zzabk2) {
            super(n);
            this.zzayo = taskCompletionSource;
            this.zzays = zzabn2;
            this.zzayt = zzabk2;
        }

        @Override
        public void zza(@NonNull zzaad zzaad2, boolean bl) {
            zzaad2.zza(this.zzayo, bl);
        }

        @Override
        public void zza(zzaap.zza<?> zza2) throws DeadObjectException {
            try {
                this.zzays.zza(zza2.zzvr(), this.zzayo);
                return;
            }
            catch (RemoteException remoteException) {
                this.zzy(zzzq.zza(remoteException));
                return;
            }
            catch (DeadObjectException deadObjectException) {
                throw deadObjectException;
            }
        }

        @Override
        public void zzy(@NonNull Status status) {
            this.zzayo.trySetException(this.zzayt.zzz(status));
        }
    }

    public static final class zze
    extends zza {
        public final zzaaz.zzb<?> zzayu;

        public zze(zzaaz.zzb<?> zzb2, TaskCompletionSource<Void> taskCompletionSource) {
            super(4, taskCompletionSource);
            this.zzayu = zzb2;
        }

        @Override
        public void zzb(zzaap.zza<?> object) throws RemoteException {
            if ((object = object.zzwc().remove(this.zzayu)) != null) {
                object.zzayq.zzwq();
                return;
            }
            Log.wtf((String)"UnregisterListenerTask", (String)"Received call to unregister a listener without a matching registration call.", (Throwable)new Exception());
            this.zzayo.trySetException(new com.google.android.gms.common.api.zza(Status.zzayj));
        }
    }

}
