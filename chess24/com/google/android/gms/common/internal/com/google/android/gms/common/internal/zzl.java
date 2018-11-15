/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.common.zzc;
import java.util.Iterator;
import java.util.Set;

public abstract class zzl<T extends IInterface>
extends zzf<T>
implements Api.zze,
zzm.zza {
    private final Account zzagg;
    private final Set<Scope> zzajm;
    private final zzg zzazs;

    protected zzl(Context context, Looper looper, int n, zzg zzg2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzn.zzaC(context), GoogleApiAvailability.getInstance(), n, zzg2, zzac.zzw(connectionCallbacks), zzac.zzw(onConnectionFailedListener));
    }

    protected zzl(Context context, Looper looper, zzn zzn2, GoogleApiAvailability googleApiAvailability, int n, zzg zzg2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, zzn2, googleApiAvailability, n, zzl.zza(connectionCallbacks), zzl.zza(onConnectionFailedListener), zzg2.zzxi());
        this.zzazs = zzg2;
        this.zzagg = zzg2.getAccount();
        this.zzajm = this.zzb(zzg2.zzxf());
    }

    @Nullable
    private static zzf.zzb zza(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        if (connectionCallbacks == null) {
            return null;
        }
        return new zzf.zzb(){

            @Override
            public void onConnected(@Nullable Bundle bundle) {
                ConnectionCallbacks.this.onConnected(bundle);
            }

            @Override
            public void onConnectionSuspended(int n) {
                ConnectionCallbacks.this.onConnectionSuspended(n);
            }
        };
    }

    @Nullable
    private static zzf.zzc zza(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if (onConnectionFailedListener == null) {
            return null;
        }
        return new zzf.zzc(){

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                OnConnectionFailedListener.this.onConnectionFailed(connectionResult);
            }
        };
    }

    private Set<Scope> zzb(@NonNull Set<Scope> set) {
        Set<Scope> set2 = this.zzc(set);
        Iterator<Scope> iterator = set2.iterator();
        while (iterator.hasNext()) {
            if (set.contains(iterator.next())) continue;
            throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
        }
        return set2;
    }

    @Override
    public final Account getAccount() {
        return this.zzagg;
    }

    @NonNull
    protected Set<Scope> zzc(@NonNull Set<Scope> set) {
        return set;
    }

    @Override
    protected final Set<Scope> zzwY() {
        return this.zzajm;
    }

    protected final zzg zzxp() {
        return this.zzazs;
    }

}
