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

final class zzsf
implements Parcelable.Creator<com.google.android.gms.internal.zzsf> {
    zzsf() {
    }

    @Deprecated
    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzw(parcel);
    }

    @Deprecated
    public /* synthetic */ Object[] newArray(int n) {
        return this.zzaF(n);
    }

    @Deprecated
    public com.google.android.gms.internal.zzsf[] zzaF(int n) {
        return new com.google.android.gms.internal.zzsf[n];
    }

    @Deprecated
    public com.google.android.gms.internal.zzsf zzw(Parcel parcel) {
        return new com.google.android.gms.internal.zzsf(parcel);
    }
}
