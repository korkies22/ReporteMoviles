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
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.internal.zzaya;

public class zzaxz
extends zza {
    public static final Parcelable.Creator<zzaxz> CREATOR = new zzaya();
    final int mVersionCode;
    final zzad zzbCr;

    zzaxz(int n, zzad zzad2) {
        this.mVersionCode = n;
        this.zzbCr = zzad2;
    }

    public zzaxz(zzad zzad2) {
        this(1, zzad2);
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzaya.zza(this, parcel, n);
    }

    public zzad zzOo() {
        return this.zzbCr;
    }
}
