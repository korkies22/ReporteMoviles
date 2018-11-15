/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzack;
import com.google.android.gms.internal.zzacn;
import com.google.android.gms.internal.zzaco;

public static class zzaco.zzb
extends zza {
    public static final Parcelable.Creator<zzaco.zzb> CREATOR = new zzacn();
    final int versionCode;
    final String zzaA;
    final zzack.zza<?, ?> zzaFO;

    zzaco.zzb(int n, String string, zzack.zza<?, ?> zza2) {
        this.versionCode = n;
        this.zzaA = string;
        this.zzaFO = zza2;
    }

    zzaco.zzb(String string, zzack.zza<?, ?> zza2) {
        this.versionCode = 1;
        this.zzaA = string;
        this.zzaFO = zza2;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacn.zza(this, parcel, n);
    }
}
