/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzabc;
import com.google.android.gms.internal.zzabl;
import com.google.android.gms.internal.zzzx;

public final class PendingResults {
    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        zzabl zzabl2 = new zzabl(Looper.getMainLooper());
        zzabl2.cancel();
        return zzabl2;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R object) {
        zzac.zzb(object, (Object)"Result must not be null");
        boolean bl = object.getStatus().getStatusCode() == 16;
        zzac.zzb(bl, (Object)"Status code must be CommonStatusCodes.CANCELED");
        object = new zza<R>(object);
        object.cancel();
        return object;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        zzac.zzb(r, (Object)"Result must not be null");
        zzc<R> zzc2 = new zzc<R>(null);
        zzc2.zzb(r);
        return new zzabc(zzc2);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzac.zzb(status, (Object)"Result must not be null");
        zzabl zzabl2 = new zzabl(Looper.getMainLooper());
        zzabl2.zzb(status);
        return zzabl2;
    }

    public static <R extends Result> PendingResult<R> zza(R r, GoogleApiClient object) {
        zzac.zzb(r, (Object)"Result must not be null");
        zzac.zzb(r.getStatus().isSuccess() ^ true, (Object)"Status code must not be SUCCESS");
        object = new zzb<R>((GoogleApiClient)object, r);
        object.zzb(r);
        return object;
    }

    public static PendingResult<Status> zza(Status status, GoogleApiClient object) {
        zzac.zzb(status, (Object)"Result must not be null");
        object = new zzabl((GoogleApiClient)object);
        object.zzb(status);
        return object;
    }

    public static <R extends Result> OptionalPendingResult<R> zzb(R r, GoogleApiClient object) {
        zzac.zzb(r, (Object)"Result must not be null");
        object = new zzc((GoogleApiClient)object);
        object.zzb(r);
        return new zzabc(object);
    }

    private static final class zza<R extends Result>
    extends zzzx<R> {
        private final R zzayc;

        public zza(R r) {
            super(Looper.getMainLooper());
            this.zzayc = r;
        }

        @Override
        protected R zzc(Status status) {
            if (status.getStatusCode() != this.zzayc.getStatus().getStatusCode()) {
                throw new UnsupportedOperationException("Creating failed results is not supported");
            }
            return this.zzayc;
        }
    }

    private static final class zzb<R extends Result>
    extends zzzx<R> {
        private final R zzayd;

        public zzb(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zzayd = r;
        }

        @Override
        protected R zzc(Status status) {
            return this.zzayd;
        }
    }

    private static final class zzc<R extends Result>
    extends zzzx<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override
        protected R zzc(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

}
