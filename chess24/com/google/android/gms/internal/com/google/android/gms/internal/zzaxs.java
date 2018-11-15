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
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaxt;
import java.util.List;

public class zzaxs
extends zza {
    public static final Parcelable.Creator<zzaxs> CREATOR = new zzaxt();
    final int mVersionCode;
    final boolean zzbCn;
    final List<Scope> zzbCo;

    zzaxs(int n, boolean bl, List<Scope> list) {
        this.mVersionCode = n;
        this.zzbCn = bl;
        this.zzbCo = list;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzaxt.zza(this, parcel, n);
    }
}
