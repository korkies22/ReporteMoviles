/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.internal.zzr;

public class zzaf
extends zza {
    public static final Parcelable.Creator<zzaf> CREATOR = new zzag();
    final int mVersionCode;
    IBinder zzaDx;
    private ConnectionResult zzaFh;
    private boolean zzaFi;
    private boolean zzazX;

    zzaf(int n, IBinder iBinder, ConnectionResult connectionResult, boolean bl, boolean bl2) {
        this.mVersionCode = n;
        this.zzaDx = iBinder;
        this.zzaFh = connectionResult;
        this.zzazX = bl;
        this.zzaFi = bl2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzaf)) {
            return false;
        }
        object = (zzaf)object;
        if (this.zzaFh.equals(object.zzaFh) && this.zzxz().equals(object.zzxz())) {
            return true;
        }
        return false;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzag.zza(this, parcel, n);
    }

    public ConnectionResult zzxA() {
        return this.zzaFh;
    }

    public boolean zzxB() {
        return this.zzazX;
    }

    public boolean zzxC() {
        return this.zzaFi;
    }

    public zzr zzxz() {
        return zzr.zza.zzbr(this.zzaDx);
    }
}
