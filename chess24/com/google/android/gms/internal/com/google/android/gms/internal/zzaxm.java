/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzaxy;

public final class zzaxm {
    public static final Api<zzaxo> API;
    public static final Api<zza> zzaJq;
    public static final Api.zzf<zzaxy> zzahc;
    public static final Api.zza<zzaxy, zzaxo> zzahd;
    public static final Scope zzajd;
    public static final Scope zzaje;
    public static final Api.zzf<zzaxy> zzbCd;
    static final Api.zza<zzaxy, zza> zzbCe;

    static {
        zzahc = new Api.zzf();
        zzbCd = new Api.zzf();
        zzahd = new Api.zza<zzaxy, zzaxo>(){

            @Override
            public zzaxy zza(Context context, Looper looper, zzg zzg2, zzaxo zzaxo2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                zzaxo zzaxo3 = zzaxo2;
                if (zzaxo2 == null) {
                    zzaxo3 = zzaxo.zzbCg;
                }
                return new zzaxy(context, looper, true, zzg2, zzaxo3, connectionCallbacks, onConnectionFailedListener);
            }
        };
        zzbCe = new Api.zza<zzaxy, zza>(){

            @Override
            public zzaxy zza(Context context, Looper looper, zzg zzg2, zza zza2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new zzaxy(context, looper, false, zzg2, zza2.zzOd(), connectionCallbacks, onConnectionFailedListener);
            }
        };
        zzajd = new Scope("profile");
        zzaje = new Scope("email");
        API = new Api<zzaxo>("SignIn.API", zzahd, zzahc);
        zzaJq = new Api<zza>("SignIn.INTERNAL_API", zzbCe, zzbCd);
    }

    public static class zza
    implements Api.ApiOptions.HasOptions {
        private final Bundle zzbCf;

        public Bundle zzOd() {
            return this.zzbCf;
        }
    }

}
