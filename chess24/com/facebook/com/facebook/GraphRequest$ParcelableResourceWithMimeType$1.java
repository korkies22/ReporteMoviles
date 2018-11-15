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
import com.facebook.GraphRequest;

static final class GraphRequest.ParcelableResourceWithMimeType
implements Parcelable.Creator<GraphRequest.ParcelableResourceWithMimeType> {
    GraphRequest.ParcelableResourceWithMimeType() {
    }

    public GraphRequest.ParcelableResourceWithMimeType createFromParcel(Parcel parcel) {
        return new GraphRequest.ParcelableResourceWithMimeType(parcel, null);
    }

    public GraphRequest.ParcelableResourceWithMimeType[] newArray(int n) {
        return new GraphRequest.ParcelableResourceWithMimeType[n];
    }
}
