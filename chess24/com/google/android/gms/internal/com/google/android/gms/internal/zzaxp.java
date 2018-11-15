/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaxq;

public class zzaxp
extends zza
implements Result {
    public static final Parcelable.Creator<zzaxp> CREATOR = new zzaxq();
    final int mVersionCode;
    private int zzbCl;
    private Intent zzbCm;

    public zzaxp() {
        this(0, null);
    }

    zzaxp(int n, int n2, Intent intent) {
        this.mVersionCode = n;
        this.zzbCl = n2;
        this.zzbCm = intent;
    }

    public zzaxp(int n, Intent intent) {
        this(2, n, intent);
    }

    @Override
    public Status getStatus() {
        if (this.zzbCl == 0) {
            return Status.zzayh;
        }
        return Status.zzayl;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzaxq.zza(this, parcel, n);
    }

    public int zzOk() {
        return this.zzbCl;
    }

    public Intent zzOl() {
        return this.zzbCm;
    }
}
