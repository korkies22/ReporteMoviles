/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.zzg;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;

public final class Scope
extends zza
implements ReflectedParcelable {
    public static final Parcelable.Creator<Scope> CREATOR = new zzg();
    final int mVersionCode;
    private final String zzayg;

    Scope(int n, String string) {
        zzac.zzh(string, "scopeUri must not be null or empty");
        this.mVersionCode = n;
        this.zzayg = string;
    }

    public Scope(String string) {
        this(1, string);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Scope)) {
            return false;
        }
        return this.zzayg.equals(((Scope)object).zzayg);
    }

    public int hashCode() {
        return this.zzayg.hashCode();
    }

    public String toString() {
        return this.zzayg;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzg.zza(this, parcel, n);
    }

    public String zzuS() {
        return this.zzayg;
    }
}
