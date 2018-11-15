/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzac;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public final class zzd {
    public static <T extends SafeParcelable> T zza(Intent arrby, String string, Parcelable.Creator<T> creator) {
        if ((arrby = arrby.getByteArrayExtra(string)) == null) {
            return null;
        }
        return zzd.zza(arrby, creator);
    }

    public static <T extends SafeParcelable> T zza(byte[] object, Parcelable.Creator<T> creator) {
        zzac.zzw(creator);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (SafeParcelable)creator.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }

    public static <T extends SafeParcelable> void zza(T t, Intent intent, String string) {
        intent.putExtra(string, zzd.zza(t));
    }

    public static <T extends SafeParcelable> byte[] zza(T object) {
        Parcel parcel = Parcel.obtain();
        object.writeToParcel(parcel, 0);
        object = parcel.marshall();
        parcel.recycle();
        return object;
    }

    public static <T extends SafeParcelable> ArrayList<T> zzb(Intent object, String iterator, Parcelable.Creator<T> creator) {
        if ((iterator = (ArrayList)object.getSerializableExtra((String)((Object)iterator))) == null) {
            return null;
        }
        object = new ArrayList(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object.add(zzd.zza((byte[])iterator.next(), creator));
        }
        return object;
    }
}
