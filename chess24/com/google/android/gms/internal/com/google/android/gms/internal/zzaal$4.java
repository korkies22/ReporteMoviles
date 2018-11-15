/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.zzl;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabl;

class zzaal
implements ResultCallback<Status> {
    final /* synthetic */ zzabl zzaAB;
    final /* synthetic */ boolean zzaAC;
    final /* synthetic */ GoogleApiClient zzaob;

    zzaal(zzabl zzabl2, boolean bl, GoogleApiClient googleApiClient) {
        this.zzaAB = zzabl2;
        this.zzaAC = bl;
        this.zzaob = googleApiClient;
    }

    @Override
    public /* synthetic */ void onResult(@NonNull Result result) {
        this.zzp((Status)result);
    }

    public void zzp(@NonNull Status status) {
        zzl.zzaa(zzaal.this.mContext).zzre();
        if (status.isSuccess() && zzaal.this.isConnected()) {
            zzaal.this.reconnect();
        }
        this.zzaAB.zzb(status);
        if (this.zzaAC) {
            this.zzaob.disconnect();
        }
    }
}
