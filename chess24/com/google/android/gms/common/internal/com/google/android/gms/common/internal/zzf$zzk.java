/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;

protected final class zzf.zzk
extends zzf.zza {
    @BinderThread
    public zzf.zzk(int n, @Nullable Bundle bundle) {
        super(zzf.this, n, bundle);
    }

    @Override
    protected void zzn(ConnectionResult connectionResult) {
        zzf.this.zzaDJ.zzg(connectionResult);
        zzf.this.onConnectionFailed(connectionResult);
    }

    @Override
    protected boolean zzwZ() {
        zzf.this.zzaDJ.zzg(ConnectionResult.zzawX);
        return true;
    }
}
