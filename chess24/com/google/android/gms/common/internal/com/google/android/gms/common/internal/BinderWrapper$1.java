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

final class BinderWrapper
implements Parcelable.Creator<com.google.android.gms.common.internal.BinderWrapper> {
    BinderWrapper() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaN(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcN(n);
    }

    public com.google.android.gms.common.internal.BinderWrapper zzaN(Parcel parcel) {
        return new com.google.android.gms.common.internal.BinderWrapper(parcel, null);
    }

    public com.google.android.gms.common.internal.BinderWrapper[] zzcN(int n) {
        return new com.google.android.gms.common.internal.BinderWrapper[n];
    }
}
