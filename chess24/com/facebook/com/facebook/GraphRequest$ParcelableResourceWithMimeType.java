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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;

public static class GraphRequest.ParcelableResourceWithMimeType<RESOURCE extends Parcelable>
implements Parcelable {
    public static final Parcelable.Creator<GraphRequest.ParcelableResourceWithMimeType> CREATOR = new Parcelable.Creator<GraphRequest.ParcelableResourceWithMimeType>(){

        public GraphRequest.ParcelableResourceWithMimeType createFromParcel(Parcel parcel) {
            return new GraphRequest.ParcelableResourceWithMimeType(parcel);
        }

        public GraphRequest.ParcelableResourceWithMimeType[] newArray(int n) {
            return new GraphRequest.ParcelableResourceWithMimeType[n];
        }
    };
    private final String mimeType;
    private final RESOURCE resource;

    private GraphRequest.ParcelableResourceWithMimeType(Parcel parcel) {
        this.mimeType = parcel.readString();
        this.resource = parcel.readParcelable(FacebookSdk.getApplicationContext().getClassLoader());
    }

    public GraphRequest.ParcelableResourceWithMimeType(RESOURCE RESOURCE, String string) {
        this.mimeType = string;
        this.resource = RESOURCE;
    }

    public int describeContents() {
        return 1;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public RESOURCE getResource() {
        return this.resource;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mimeType);
        parcel.writeParcelable(this.resource, n);
    }

}
