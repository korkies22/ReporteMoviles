/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable;

static final class ResultReceiver
implements Parcelable.Creator<android.support.v4.os.ResultReceiver> {
    ResultReceiver() {
    }

    public android.support.v4.os.ResultReceiver createFromParcel(Parcel parcel) {
        return new android.support.v4.os.ResultReceiver(parcel);
    }

    public android.support.v4.os.ResultReceiver[] newArray(int n) {
        return new android.support.v4.os.ResultReceiver[n];
    }
}
