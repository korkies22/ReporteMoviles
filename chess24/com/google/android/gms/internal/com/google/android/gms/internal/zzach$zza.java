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
import com.google.android.gms.internal.zzach;
import com.google.android.gms.internal.zzacj;

public static final class zzach.zza
extends zza {
    public static final Parcelable.Creator<zzach.zza> CREATOR = new zzacj();
    final int versionCode;
    final String zzaFy;
    final int zzaFz;

    zzach.zza(int n, String string, int n2) {
        this.versionCode = n;
        this.zzaFy = string;
        this.zzaFz = n2;
    }

    zzach.zza(String string, int n) {
        this.versionCode = 1;
        this.zzaFy = string;
        this.zzaFz = n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacj.zza(this, parcel, n);
    }
}
