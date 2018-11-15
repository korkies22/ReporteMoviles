/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzu;

public static final class zzf.zzg
extends zzu.zza {
    private zzf zzaDX;
    private final int zzaDY;

    public zzf.zzg(@NonNull zzf zzf2, int n) {
        this.zzaDX = zzf2;
        this.zzaDY = n;
    }

    private void zzxc() {
        this.zzaDX = null;
    }

    @BinderThread
    @Override
    public void zza(int n, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
        zzac.zzb(this.zzaDX, (Object)"onPostInitComplete can be called only once per call to getRemoteService");
        this.zzaDX.zza(n, iBinder, bundle, this.zzaDY);
        this.zzxc();
    }

    @BinderThread
    @Override
    public void zzb(int n, @Nullable Bundle bundle) {
        Log.wtf((String)"GmsClient", (String)"received deprecated onAccountValidationComplete callback, ignoring", (Throwable)new Exception());
    }
}
