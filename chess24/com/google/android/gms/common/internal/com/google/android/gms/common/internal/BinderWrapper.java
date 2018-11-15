/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper
implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new Parcelable.Creator<BinderWrapper>(){

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.zzaN(parcel);
        }

        public /* synthetic */ Object[] newArray(int n) {
            return this.zzcN(n);
        }

        public BinderWrapper zzaN(Parcel parcel) {
            return new BinderWrapper(parcel);
        }

        public BinderWrapper[] zzcN(int n) {
            return new BinderWrapper[n];
        }
    };
    private IBinder zzaEa = null;

    public BinderWrapper() {
    }

    public BinderWrapper(IBinder iBinder) {
        this.zzaEa = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzaEa = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.zzaEa);
    }

}
