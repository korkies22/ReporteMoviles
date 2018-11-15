/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzr;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final String mName;
    private final zza<?, O> zzaxu;
    private final zzh<?, O> zzaxv;
    private final zzf<?> zzaxw;
    private final zzi<?> zzaxx;

    public <C extends zze> Api(String string, zza<C, O> zza2, zzf<C> zzf2) {
        zzac.zzb(zza2, (Object)"Cannot construct an Api with a null ClientBuilder");
        zzac.zzb(zzf2, (Object)"Cannot construct an Api with a null ClientKey");
        this.mName = string;
        this.zzaxu = zza2;
        this.zzaxv = null;
        this.zzaxw = zzf2;
        this.zzaxx = null;
    }

    public String getName() {
        return this.mName;
    }

    public zzd<?, O> zzuF() {
        return this.zzaxu;
    }

    public zza<?, O> zzuG() {
        boolean bl = this.zzaxu != null;
        zzac.zza(bl, (Object)"This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zzaxu;
    }

    public zzc<?> zzuH() {
        if (this.zzaxw != null) {
            return this.zzaxw;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    public static interface ApiOptions {
    }

    public static interface ApiOptions$HasOptions
    extends ApiOptions {
    }

    public static final class ApiOptions$NoOptions
    implements ApiOptions$NotRequiredOptions {
        private ApiOptions$NoOptions() {
        }
    }

    public static interface ApiOptions$NotRequiredOptions
    extends ApiOptions {
    }

    public static interface ApiOptions$Optional
    extends ApiOptions$HasOptions,
    ApiOptions$NotRequiredOptions {
    }

    public static abstract class zza<T extends zze, O>
    extends zzd<T, O> {
        public abstract T zza(Context var1, Looper var2, com.google.android.gms.common.internal.zzg var3, O var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6);
    }

    public static interface zzb {
    }

    public static class zzc<C extends zzb> {
    }

    public static abstract class zzd<T extends zzb, O> {
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public List<Scope> zzp(O o) {
            return Collections.emptyList();
        }
    }

    public static interface zze
    extends zzb {
        public void disconnect();

        public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

        public boolean isConnected();

        public boolean isConnecting();

        public void zza(zzf.zzf var1);

        public void zza(zzr var1, Set<Scope> var2);

        public boolean zzqD();

        public boolean zzqS();

        public Intent zzqT();

        public boolean zzuI();

        @Nullable
        public IBinder zzuJ();
    }

    public static final class zzf<C extends zze>
    extends zzc<C> {
    }

    public static interface zzg<T extends IInterface>
    extends zzb {
        public String zzeu();

        public String zzev();

        public T zzh(IBinder var1);
    }

    public static abstract class zzh<T extends zzg, O>
    extends zzd<T, O> {
    }

    public static final class zzi<C extends zzg>
    extends zzc<C> {
    }

}
