/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

final class MessengerCompat
implements Parcelable.Creator<com.google.android.gms.iid.MessengerCompat> {
    MessengerCompat() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgn(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzjB(n);
    }

    public com.google.android.gms.iid.MessengerCompat zzgn(Parcel parcel) {
        if ((parcel = parcel.readStrongBinder()) != null) {
            return new com.google.android.gms.iid.MessengerCompat((IBinder)parcel);
        }
        return null;
    }

    public com.google.android.gms.iid.MessengerCompat[] zzjB(int n) {
        return new com.google.android.gms.iid.MessengerCompat[n];
    }
}
