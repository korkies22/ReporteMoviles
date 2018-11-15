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
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzaxs;
import java.io.Serializable;
import java.util.List;

public class zzaxt
implements Parcelable.Creator<zzaxs> {
    static void zza(zzaxs zzaxs2, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzaxs2.mVersionCode);
        zzc.zza(parcel, 2, zzaxs2.zzbCn);
        zzc.zzc(parcel, 3, zzaxs2.zzbCo, false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziP(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzmJ(n);
    }

    public zzaxs zziP(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        Serializable serializable = null;
        boolean bl = false;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block5;
                }
                case 3: {
                    serializable = zzb.zzc(parcel, n3, Scope.CREATOR);
                    continue block5;
                }
                case 2: {
                    bl = zzb.zzc(parcel, n3);
                    continue block5;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            serializable = new StringBuilder(37);
            serializable.append("Overread allowed size end=");
            serializable.append(n);
            throw new zzb.zza(serializable.toString(), parcel);
        }
        return new zzaxs(n2, bl, (List<Scope>)((Object)serializable));
    }

    public zzaxs[] zzmJ(int n) {
        return new zzaxs[n];
    }
}
