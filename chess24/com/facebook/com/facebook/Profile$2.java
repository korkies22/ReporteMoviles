/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.Profile;

static final class Profile
implements Parcelable.Creator {
    Profile() {
    }

    public com.facebook.Profile createFromParcel(Parcel parcel) {
        return new com.facebook.Profile(parcel, null);
    }

    public com.facebook.Profile[] newArray(int n) {
        return new com.facebook.Profile[n];
    }
}
