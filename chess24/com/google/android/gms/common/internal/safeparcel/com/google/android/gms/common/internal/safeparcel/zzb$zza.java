/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.zzb;

public static class zzb.zza
extends RuntimeException {
    public zzb.zza(String string, Parcel object) {
        int n = object.dataPosition();
        int n2 = object.dataSize();
        object = new StringBuilder(41 + String.valueOf(string).length());
        object.append(string);
        object.append(" Parcel: pos=");
        object.append(n);
        object.append(" size=");
        object.append(n2);
        super(object.toString());
    }
}
