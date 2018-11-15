/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzao;

@Deprecated
public class zzan
extends zza {
    public static final Parcelable.Creator<zzan> CREATOR = new zzao();
    final int mVersionCode;

    zzan(int n) {
        this.mVersionCode = n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzao.zza(this, parcel, n);
    }
}
