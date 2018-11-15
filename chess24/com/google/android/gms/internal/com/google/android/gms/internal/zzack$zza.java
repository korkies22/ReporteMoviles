/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzacf;
import com.google.android.gms.internal.zzack;
import com.google.android.gms.internal.zzacm;
import com.google.android.gms.internal.zzaco;
import com.google.android.gms.internal.zzacr;
import java.util.ArrayList;
import java.util.Map;

public static class zzack.zza<I, O>
extends zza {
    public static final zzacm CREATOR = new zzacm();
    private final int mVersionCode;
    protected final int zzaFA;
    protected final boolean zzaFB;
    protected final int zzaFC;
    protected final boolean zzaFD;
    protected final String zzaFE;
    protected final int zzaFF;
    protected final Class<? extends zzack> zzaFG;
    protected final String zzaFH;
    private zzaco zzaFI;
    private zzack.zzb<I, O> zzaFJ;

    /*
     * Enabled aggressive block sorting
     */
    zzack.zza(int n, int n2, boolean bl, int n3, boolean bl2, String zzb2, int n4, String string, zzacf zzacf2) {
        this.mVersionCode = n;
        this.zzaFA = n2;
        this.zzaFB = bl;
        this.zzaFC = n3;
        this.zzaFD = bl2;
        this.zzaFE = zzb2;
        this.zzaFF = n4;
        zzb2 = null;
        if (string == null) {
            this.zzaFG = null;
            this.zzaFH = null;
        } else {
            this.zzaFG = zzacr.class;
            this.zzaFH = string;
        }
        if (zzacf2 != null) {
            zzb2 = zzacf2.zzxI();
        }
        this.zzaFJ = zzb2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected zzack.zza(int n, boolean bl, int n2, boolean bl2, String string, int n3, Class<? extends zzack> class_, zzack.zzb<I, O> zzb2) {
        this.mVersionCode = 1;
        this.zzaFA = n;
        this.zzaFB = bl;
        this.zzaFC = n2;
        this.zzaFD = bl2;
        this.zzaFE = string;
        this.zzaFF = n3;
        this.zzaFG = class_;
        string = class_ == null ? null : class_.getCanonicalName();
        this.zzaFH = string;
        this.zzaFJ = zzb2;
    }

    public static zzack.zza zza(String string, int n, zzack.zzb<?, ?> zzb2, boolean bl) {
        return new zzack.zza(7, bl, 0, false, string, n, null, zzb2);
    }

    public static <T extends zzack> zzack.zza<T, T> zza(String string, int n, Class<T> class_) {
        return new zzack.zza<I, O>(11, false, 11, false, string, n, class_, null);
    }

    public static <T extends zzack> zzack.zza<ArrayList<T>, ArrayList<T>> zzb(String string, int n, Class<T> class_) {
        return new zzack.zza<ArrayList<T>, ArrayList<T>>(11, true, 11, true, string, n, class_, null);
    }

    static /* synthetic */ zzack.zzb zzc(zzack.zza zza2) {
        return zza2.zzaFJ;
    }

    public static zzack.zza<Integer, Integer> zzk(String string, int n) {
        return new zzack.zza<Integer, Integer>(0, false, 0, false, string, n, null, null);
    }

    public static zzack.zza<Boolean, Boolean> zzl(String string, int n) {
        return new zzack.zza<Boolean, Boolean>(6, false, 6, false, string, n, null, null);
    }

    public static zzack.zza<String, String> zzm(String string, int n) {
        return new zzack.zza<String, String>(7, false, 7, false, string, n, null, null);
    }

    public I convertBack(O o) {
        return this.zzaFJ.convertBack(o);
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public String toString() {
        zzaa.zza zza2 = zzaa.zzv(this).zzg("versionCode", this.mVersionCode).zzg("typeIn", this.zzaFA).zzg("typeInArray", this.zzaFB).zzg("typeOut", this.zzaFC).zzg("typeOutArray", this.zzaFD).zzg("outputFieldName", this.zzaFE).zzg("safeParcelFieldId", this.zzaFF).zzg("concreteTypeName", this.zzxS());
        Class<zzack> class_ = this.zzxR();
        if (class_ != null) {
            zza2.zzg("concreteType.class", class_.getCanonicalName());
        }
        if (this.zzaFJ != null) {
            zza2.zzg("converterName", this.zzaFJ.getClass().getCanonicalName());
        }
        return zza2.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacm.zza(this, parcel, n);
    }

    public void zza(zzaco zzaco2) {
        this.zzaFI = zzaco2;
    }

    public int zzxL() {
        return this.zzaFA;
    }

    public boolean zzxM() {
        return this.zzaFB;
    }

    public int zzxN() {
        return this.zzaFC;
    }

    public boolean zzxO() {
        return this.zzaFD;
    }

    public String zzxP() {
        return this.zzaFE;
    }

    public int zzxQ() {
        return this.zzaFF;
    }

    public Class<? extends zzack> zzxR() {
        return this.zzaFG;
    }

    String zzxS() {
        if (this.zzaFH == null) {
            return null;
        }
        return this.zzaFH;
    }

    public boolean zzxT() {
        if (this.zzaFJ != null) {
            return true;
        }
        return false;
    }

    zzacf zzxU() {
        if (this.zzaFJ == null) {
            return null;
        }
        return zzacf.zza(this.zzaFJ);
    }

    public Map<String, zzack.zza<?, ?>> zzxV() {
        zzac.zzw(this.zzaFH);
        zzac.zzw(this.zzaFI);
        return this.zzaFI.zzdA(this.zzaFH);
    }
}
