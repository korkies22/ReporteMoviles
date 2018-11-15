/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzaaa;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaau;
import java.util.concurrent.locks.Lock;

private class zzaaa.zza
implements zzaau.zza {
    private zzaaa.zza() {
    }

    @Override
    public void zzc(int n, boolean bl) {
        zzaaa.this.zzazn.lock();
        try {
            if (!zzaaa.this.zzazm && zzaaa.this.zzazl != null && zzaaa.this.zzazl.isSuccess()) {
                zzaaa.this.zzazm = true;
                zzaaa.this.zzazf.onConnectionSuspended(n);
                return;
            }
            zzaaa.this.zzazm = false;
            zzaaa.this.zzb(n, bl);
            return;
        }
        finally {
            zzaaa.this.zzazn.unlock();
        }
    }

    @Override
    public void zzc(@NonNull ConnectionResult connectionResult) {
        zzaaa.this.zzazn.lock();
        try {
            zzaaa.this.zzazk = connectionResult;
            zzaaa.this.zzvm();
            return;
        }
        finally {
            zzaaa.this.zzazn.unlock();
        }
    }

    @Override
    public void zzo(@Nullable Bundle bundle) {
        zzaaa.this.zzazn.lock();
        try {
            zzaaa.this.zzn(bundle);
            zzaaa.this.zzazk = ConnectionResult.zzawX;
            zzaaa.this.zzvm();
            return;
        }
        finally {
            zzaaa.this.zzazn.unlock();
        }
    }
}
