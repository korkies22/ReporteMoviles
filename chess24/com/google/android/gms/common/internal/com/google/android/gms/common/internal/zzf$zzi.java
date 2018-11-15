/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzr;
import java.util.Set;

protected class zzf.zzi
implements zzf.zzf {
    @Override
    public void zzg(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.isSuccess()) {
            zzf.this.zza(null, zzf.this.zzwY());
            return;
        }
        if (zzf.this.zzaDP != null) {
            zzf.this.zzaDP.onConnectionFailed(connectionResult);
        }
    }
}
