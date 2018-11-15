/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.server.zza;

public class FavaDiagnosticsEntity
extends com.google.android.gms.common.internal.safeparcel.zza
implements ReflectedParcelable {
    public static final Parcelable.Creator<FavaDiagnosticsEntity> CREATOR = new zza();
    final int mVersionCode;
    public final String zzaFs;
    public final int zzaFt;

    public FavaDiagnosticsEntity(int n, String string, int n2) {
        this.mVersionCode = n;
        this.zzaFs = string;
        this.zzaFt = n2;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zza.zza(this, parcel, n);
    }
}
