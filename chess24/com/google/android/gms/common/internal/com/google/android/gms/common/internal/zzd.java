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
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zze;

public class zzd
extends zza {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    final int mVersionCode;
    Integer zzaDA;
    final IBinder zzaDx;
    final Scope[] zzaDy;
    Integer zzaDz;

    zzd(int n, IBinder iBinder, Scope[] arrscope, Integer n2, Integer n3) {
        this.mVersionCode = n;
        this.zzaDx = iBinder;
        this.zzaDy = arrscope;
        this.zzaDz = n2;
        this.zzaDA = n3;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zze.zza(this, parcel, n);
    }
}
