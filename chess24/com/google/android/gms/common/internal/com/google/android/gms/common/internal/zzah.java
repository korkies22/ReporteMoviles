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
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzai;

public class zzah
extends zza {
    public static final Parcelable.Creator<zzah> CREATOR = new zzai();
    final int mVersionCode;
    @Deprecated
    private final Scope[] zzaDy;
    private final int zzaFj;
    private final int zzaFk;

    zzah(int n, int n2, int n3, Scope[] arrscope) {
        this.mVersionCode = n;
        this.zzaFj = n2;
        this.zzaFk = n3;
        this.zzaDy = arrscope;
    }

    public zzah(int n, int n2, Scope[] arrscope) {
        this(1, n, n2, null);
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzai.zza(this, parcel, n);
    }

    public int zzxD() {
        return this.zzaFj;
    }

    public int zzxE() {
        return this.zzaFk;
    }

    @Deprecated
    public Scope[] zzxF() {
        return this.zzaDy;
    }
}
