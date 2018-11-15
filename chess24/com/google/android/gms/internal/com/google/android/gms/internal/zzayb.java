/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.internal.zzayc;

public class zzayb
extends zza {
    public static final Parcelable.Creator<zzayb> CREATOR = new zzayc();
    final int mVersionCode;
    private final ConnectionResult zzaFh;
    private final zzaf zzbCs;

    public zzayb(int n) {
        this(new ConnectionResult(n, null), null);
    }

    zzayb(int n, ConnectionResult connectionResult, zzaf zzaf2) {
        this.mVersionCode = n;
        this.zzaFh = connectionResult;
        this.zzbCs = zzaf2;
    }

    public zzayb(ConnectionResult connectionResult, zzaf zzaf2) {
        this(1, connectionResult, zzaf2);
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzayc.zza(this, parcel, n);
    }

    public zzaf zzOp() {
        return this.zzbCs;
    }

    public ConnectionResult zzxA() {
        return this.zzaFh;
    }
}
