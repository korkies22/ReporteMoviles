/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class zzd<T extends SafeParcelable>
extends AbstractDataBuffer<T> {
    private static final String[] zzaCo = new String[]{"data"};
    private final Parcelable.Creator<T> zzaCp;

    public zzd(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.zzaCp = creator;
    }

    public static <T extends SafeParcelable> void zza(DataHolder.zza zza2, T object) {
        Parcel parcel = Parcel.obtain();
        object.writeToParcel(parcel, 0);
        object = new ContentValues();
        object.put("data", parcel.marshall());
        zza2.zza((ContentValues)object);
        parcel.recycle();
    }

    public static DataHolder.zza zzwC() {
        return DataHolder.zzc(zzaCo);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.zzcB(n);
    }

    public T zzcB(int n) {
        Object object = this.zzazI.zzg("data", n, this.zzazI.zzcC(n));
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (SafeParcelable)this.zzaCp.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }
}
