/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.stats.WakeLockEvent;
import java.io.Serializable;
import java.util.List;

public class zzf
implements Parcelable.Creator<WakeLockEvent> {
    static void zza(WakeLockEvent wakeLockEvent, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, wakeLockEvent.mVersionCode);
        zzc.zza(parcel, 2, wakeLockEvent.getTimeMillis());
        zzc.zza(parcel, 4, wakeLockEvent.zzyg(), false);
        zzc.zzc(parcel, 5, wakeLockEvent.zzyj());
        zzc.zzb(parcel, 6, wakeLockEvent.zzyk(), false);
        zzc.zza(parcel, 8, wakeLockEvent.zzym());
        zzc.zza(parcel, 10, wakeLockEvent.zzyh(), false);
        zzc.zzc(parcel, 11, wakeLockEvent.getEventType());
        zzc.zza(parcel, 12, wakeLockEvent.zzyl(), false);
        zzc.zza(parcel, 13, wakeLockEvent.zzyo(), false);
        zzc.zzc(parcel, 14, wakeLockEvent.zzyn());
        zzc.zza(parcel, 15, wakeLockEvent.zzyp());
        zzc.zza(parcel, 16, wakeLockEvent.zzyq());
        zzc.zza(parcel, 17, wakeLockEvent.zzyi(), false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzbf(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzdh(n);
    }

    public WakeLockEvent zzbf(Parcel parcel) {
        CharSequence charSequence;
        int n;
        int n2;
        CharSequence charSequence2;
        CharSequence charSequence3;
        long l;
        long l2;
        int n3;
        int n4 = zzb.zzaU(parcel);
        long l3 = l = (l2 = 0L);
        int n5 = n2 = (n3 = (n = 0));
        CharSequence charSequence4 = null;
        Serializable serializable = charSequence4;
        CharSequence charSequence5 = charSequence3 = (charSequence2 = (charSequence = serializable));
        float f = 0.0f;
        block16 : while (parcel.dataPosition() < n4) {
            int n6 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n6)) {
                default: {
                    zzb.zzb(parcel, n6);
                    continue block16;
                }
                case 17: {
                    charSequence5 = zzb.zzq(parcel, n6);
                    continue block16;
                }
                case 16: {
                    l3 = zzb.zzi(parcel, n6);
                    continue block16;
                }
                case 15: {
                    f = zzb.zzl(parcel, n6);
                    continue block16;
                }
                case 14: {
                    n5 = zzb.zzg(parcel, n6);
                    continue block16;
                }
                case 13: {
                    charSequence3 = zzb.zzq(parcel, n6);
                    continue block16;
                }
                case 12: {
                    charSequence = zzb.zzq(parcel, n6);
                    continue block16;
                }
                case 11: {
                    n3 = zzb.zzg(parcel, n6);
                    continue block16;
                }
                case 10: {
                    charSequence2 = zzb.zzq(parcel, n6);
                    continue block16;
                }
                case 8: {
                    l = zzb.zzi(parcel, n6);
                    continue block16;
                }
                case 6: {
                    serializable = zzb.zzE(parcel, n6);
                    continue block16;
                }
                case 5: {
                    n2 = zzb.zzg(parcel, n6);
                    continue block16;
                }
                case 4: {
                    charSequence4 = zzb.zzq(parcel, n6);
                    continue block16;
                }
                case 2: {
                    l2 = zzb.zzi(parcel, n6);
                    continue block16;
                }
                case 1: 
            }
            n = zzb.zzg(parcel, n6);
        }
        if (parcel.dataPosition() != n4) {
            serializable = new StringBuilder(37);
            serializable.append("Overread allowed size end=");
            serializable.append(n4);
            throw new zzb.zza(serializable.toString(), parcel);
        }
        return new WakeLockEvent(n, l2, n3, (String)charSequence4, n2, (List<String>)((Object)serializable), (String)charSequence, l, n5, (String)charSequence2, (String)charSequence3, f, l3, (String)charSequence5);
    }

    public WakeLockEvent[] zzdh(int n) {
        return new WakeLockEvent[n];
    }
}
