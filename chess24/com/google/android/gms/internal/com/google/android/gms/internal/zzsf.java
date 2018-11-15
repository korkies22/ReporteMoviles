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

public class zzsf
implements Parcelable {
    @Deprecated
    public static final Parcelable.Creator<zzsf> CREATOR = new Parcelable.Creator<zzsf>(){

        @Deprecated
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.zzw(parcel);
        }

        @Deprecated
        public /* synthetic */ Object[] newArray(int n) {
            return this.zzaF(n);
        }

        @Deprecated
        public zzsf[] zzaF(int n) {
            return new zzsf[n];
        }

        @Deprecated
        public zzsf zzw(Parcel parcel) {
            return new zzsf(parcel);
        }
    };
    private String mValue;
    private String zzGu;
    private String zzadT;

    @Deprecated
    public zzsf() {
    }

    @Deprecated
    zzsf(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Deprecated
    private void readFromParcel(Parcel parcel) {
        this.zzGu = parcel.readString();
        this.zzadT = parcel.readString();
        this.mValue = parcel.readString();
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.zzGu;
    }

    public String getValue() {
        return this.mValue;
    }

    @Deprecated
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.zzGu);
        parcel.writeString(this.zzadT);
        parcel.writeString(this.mValue);
    }

}
