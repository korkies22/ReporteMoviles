/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.common.api.zzd;

public class zzb {
    @NonNull
    public static zza zzF(@NonNull Status status) {
        if (status.hasResolution()) {
            return new zzd(status);
        }
        return new zza(status);
    }

    @NonNull
    public static zza zzl(@NonNull ConnectionResult connectionResult) {
        return zzb.zzF(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution()));
    }
}
