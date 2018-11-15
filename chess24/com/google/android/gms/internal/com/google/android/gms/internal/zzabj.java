/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 *  android.view.View
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaxm;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzaxr;
import com.google.android.gms.internal.zzaxu;
import com.google.android.gms.internal.zzaxy;
import com.google.android.gms.internal.zzayb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class zzabj
extends zzaxr
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private static Api.zza<? extends zzaxn, zzaxo> zzaBH = zzaxm.zzahd;
    private final Context mContext;
    private final Handler mHandler;
    private final boolean zzaBI;
    private zza zzaBJ;
    private Set<Scope> zzajm;
    private final Api.zza<? extends zzaxn, zzaxo> zzaxu;
    private zzaxn zzazS;
    private zzg zzazs;

    @WorkerThread
    public zzabj(Context object, Handler handler) {
        this.mContext = object;
        this.mHandler = handler;
        object = zzl.zzaa(this.mContext).zzrd();
        object = object == null ? new HashSet() : new HashSet<Scope>(object.zzqJ());
        this.zzajm = object;
        this.zzazs = new zzg(null, this.zzajm, null, 0, null, null, null, zzaxo.zzbCg);
        this.zzaxu = zzaBH;
        this.zzaBI = true;
    }

    @WorkerThread
    public zzabj(Context context, Handler handler, zzg zzg2, Api.zza<? extends zzaxn, zzaxo> zza2) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzazs = zzg2;
        this.zzajm = zzg2.zzxe();
        this.zzaxu = zza2;
        this.zzaBI = false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @WorkerThread
    private void zzc(zzayb var1_1) {
        var3_2 = var1_1.zzxA();
        var2_3 = var3_2;
        if (!var3_2.isSuccess()) ** GOTO lbl11
        var2_3 = (var1_1 = var1_1.zzOp()).zzxA();
        if (!var2_3.isSuccess()) {
            var1_1 = String.valueOf(var2_3);
            var3_2 = new StringBuilder(48 + String.valueOf(var1_1).length());
            var3_2.append("Sign-in succeeded with resolve account failure: ");
            var3_2.append((String)var1_1);
            Log.wtf((String)"SignInCoordinator", (String)var3_2.toString(), (Throwable)new Exception());
lbl11: // 2 sources:
            this.zzaBJ.zzi(var2_3);
        } else {
            this.zzaBJ.zzb(var1_1.zzxz(), this.zzajm);
        }
        this.zzazS.disconnect();
    }

    @WorkerThread
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.zzazS.zza(this);
    }

    @WorkerThread
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaBJ.zzi(connectionResult);
    }

    @WorkerThread
    @Override
    public void onConnectionSuspended(int n) {
        this.zzazS.disconnect();
    }

    @WorkerThread
    public void zza(zza zza2) {
        if (this.zzazS != null) {
            this.zzazS.disconnect();
        }
        if (this.zzaBI) {
            Object object = zzl.zzaa(this.mContext).zzrd();
            object = object == null ? new HashSet() : new HashSet<Scope>(object.zzqJ());
            this.zzajm = object;
            this.zzazs = new zzg(null, this.zzajm, null, 0, null, null, null, zzaxo.zzbCg);
        }
        this.zzazS = this.zzaxu.zza(this.mContext, this.mHandler.getLooper(), this.zzazs, this.zzazs.zzxk(), this, this);
        this.zzaBJ = zza2;
        this.zzazS.connect();
    }

    @BinderThread
    @Override
    public void zzb(final zzayb zzayb2) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                zzabj.this.zzc(zzayb2);
            }
        });
    }

    public void zzwr() {
        this.zzazS.disconnect();
    }

    @WorkerThread
    public static interface zza {
        public void zzb(zzr var1, Set<Scope> var2);

        public void zzi(ConnectionResult var1);
    }

}
