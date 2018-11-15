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
import com.google.android.gms.internal.zzacg;
import com.google.android.gms.internal.zzach;
import com.google.android.gms.internal.zzack;

public class zzacf
extends zza {
    public static final Parcelable.Creator<zzacf> CREATOR = new zzacg();
    final int mVersionCode;
    private final zzach zzaFu;

    zzacf(int n, zzach zzach2) {
        this.mVersionCode = n;
        this.zzaFu = zzach2;
    }

    private zzacf(zzach zzach2) {
        this.mVersionCode = 1;
        this.zzaFu = zzach2;
    }

    public static zzacf zza(zzack.zzb<?, ?> zzb2) {
        if (zzb2 instanceof zzach) {
            return new zzacf((zzach)zzb2);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacg.zza(this, parcel, n);
    }

    zzach zzxH() {
        return this.zzaFu;
    }

    public zzack.zzb<?, ?> zzxI() {
        if (this.zzaFu != null) {
            return this.zzaFu;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
