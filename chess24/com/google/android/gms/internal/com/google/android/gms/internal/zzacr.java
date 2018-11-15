/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzack;
import com.google.android.gms.internal.zzacl;
import com.google.android.gms.internal.zzaco;
import com.google.android.gms.internal.zzacs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class zzacr
extends zzacl {
    public static final Parcelable.Creator<zzacr> CREATOR = new zzacs();
    private final String mClassName;
    private final int mVersionCode;
    private final zzaco zzaFI;
    private final Parcel zzaFP;
    private final int zzaFQ;
    private int zzaFR;
    private int zzaFS;

    /*
     * Enabled aggressive block sorting
     */
    zzacr(int n, Parcel object, zzaco zzaco2) {
        void var3_6;
        void var2_4;
        this.mVersionCode = n;
        this.zzaFP = zzac.zzw(object);
        this.zzaFQ = 2;
        this.zzaFI = var3_6;
        if (this.zzaFI == null) {
            Object var2_3 = null;
        } else {
            String string = this.zzaFI.zzxY();
        }
        this.mClassName = var2_4;
        this.zzaFR = 2;
    }

    private static SparseArray<Map.Entry<String, zzack.zza<?, ?>>> zzX(Map<String, zzack.zza<?, ?>> object) {
        SparseArray sparseArray = new SparseArray();
        for (Map.Entry entry : object.entrySet()) {
            sparseArray.put(((zzack.zza)entry.getValue()).zzxQ(), entry);
        }
        return sparseArray;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void zza(StringBuilder var1_1, int var2_2, Object var3_3) {
        switch (var2_2) {
            default: {
                var1_1 = new StringBuilder(26);
                var1_1.append("Unknown type = ");
                var1_1.append(var2_2);
                throw new IllegalArgumentException(var1_1.toString());
            }
            case 11: {
                throw new IllegalArgumentException("Method does not accept concrete type.");
            }
            case 10: {
                zzq.zza(var1_1, (HashMap)var3_3);
                return;
            }
            case 9: {
                var1_1.append("\"");
                var3_3 = com.google.android.gms.common.util.zzc.zzr((byte[])var3_3);
                ** GOTO lbl23
            }
            case 8: {
                var1_1.append("\"");
                var3_3 = com.google.android.gms.common.util.zzc.zzq((byte[])var3_3);
                ** GOTO lbl23
            }
            case 7: {
                var1_1.append("\"");
                var3_3 = zzp.zzdC(var3_3.toString());
lbl23: // 3 sources:
                var1_1.append((String)var3_3);
                var1_1.append("\"");
                return;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        var1_1.append(var3_3);
    }

    private void zza(StringBuilder stringBuilder, zzack.zza<?, ?> zza2, Parcel object, int n) {
        switch (zza2.zzxN()) {
            default: {
                n = zza2.zzxN();
                stringBuilder = new StringBuilder(36);
                stringBuilder.append("Unknown field out type = ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 11: {
                throw new IllegalArgumentException("Method does not accept concrete type.");
            }
            case 10: {
                object = zzacr.zzr(com.google.android.gms.common.internal.safeparcel.zzb.zzs((Parcel)object, n));
                break;
            }
            case 8: 
            case 9: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzt((Parcel)object, n);
                break;
            }
            case 7: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzq((Parcel)object, n);
                break;
            }
            case 6: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzc((Parcel)object, n);
                break;
            }
            case 5: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzp((Parcel)object, n);
                break;
            }
            case 4: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzn((Parcel)object, n);
                break;
            }
            case 3: {
                object = Float.valueOf(com.google.android.gms.common.internal.safeparcel.zzb.zzl((Parcel)object, n));
                break;
            }
            case 2: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzi((Parcel)object, n);
                break;
            }
            case 1: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzk((Parcel)object, n);
                break;
            }
            case 0: {
                object = com.google.android.gms.common.internal.safeparcel.zzb.zzg((Parcel)object, n);
            }
        }
        this.zzb(stringBuilder, zza2, this.zza(zza2, object));
    }

    private void zza(StringBuilder stringBuilder, String string, zzack.zza<?, ?> zza2, Parcel parcel, int n) {
        stringBuilder.append("\"");
        stringBuilder.append(string);
        stringBuilder.append("\":");
        if (zza2.zzxT()) {
            this.zza(stringBuilder, zza2, parcel, n);
            return;
        }
        this.zzb(stringBuilder, zza2, parcel, n);
    }

    private void zza(StringBuilder stringBuilder, Map<String, zzack.zza<?, ?>> map, Parcel parcel) {
        map = zzacr.zzX(map);
        stringBuilder.append('{');
        int n = com.google.android.gms.common.internal.safeparcel.zzb.zzaU(parcel);
        boolean bl = false;
        while (parcel.dataPosition() < n) {
            int n2 = com.google.android.gms.common.internal.safeparcel.zzb.zzaT(parcel);
            Map.Entry entry = (Map.Entry)map.get(com.google.android.gms.common.internal.safeparcel.zzb.zzcW(n2));
            if (entry == null) continue;
            if (bl) {
                stringBuilder.append(",");
            }
            this.zza(stringBuilder, (String)entry.getKey(), (zzack.zza)entry.getValue(), parcel, n2);
            bl = true;
        }
        if (parcel.dataPosition() != n) {
            stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(n);
            throw new zzb.zza(stringBuilder.toString(), parcel);
        }
        stringBuilder.append('}');
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void zzb(StringBuilder var1_1, zzack.zza<?, ?> var2_2, Parcel var3_3, int var4_4) {
        block30 : {
            block31 : {
                if (!var2_2.zzxO()) break block30;
                var1_1.append("[");
                switch (var2_2.zzxN()) {
                    default: {
                        throw new IllegalStateException("Unknown field type out.");
                    }
                    case 11: {
                        var3_3 = com.google.android.gms.common.internal.safeparcel.zzb.zzG((Parcel)var3_3, var4_4);
                        var5_5 = ((Object)var3_3).length;
                        for (var4_4 = 0; var4_4 < var5_5; ++var4_4) {
                            if (var4_4 > 0) {
                                var1_1.append(",");
                            }
                            var3_3[var4_4].setDataPosition(0);
                            this.zza(var1_1, var2_2.zzxV(), (Parcel)var3_3[var4_4]);
                        }
                        break block31;
                    }
                    case 8: 
                    case 9: 
                    case 10: {
                        throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                    }
                    case 7: {
                        zzb.zza(var1_1, com.google.android.gms.common.internal.safeparcel.zzb.zzC((Parcel)var3_3, var4_4));
                        break block31;
                    }
                    case 6: {
                        zzb.zza(var1_1, com.google.android.gms.common.internal.safeparcel.zzb.zzv((Parcel)var3_3, var4_4));
                        break block31;
                    }
                    case 5: {
                        var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzB((Parcel)var3_3, var4_4);
                        ** GOTO lbl38
                    }
                    case 4: {
                        zzb.zza(var1_1, com.google.android.gms.common.internal.safeparcel.zzb.zzA((Parcel)var3_3, var4_4));
                        break block31;
                    }
                    case 3: {
                        zzb.zza(var1_1, com.google.android.gms.common.internal.safeparcel.zzb.zzz((Parcel)var3_3, var4_4));
                        break block31;
                    }
                    case 2: {
                        zzb.zza(var1_1, com.google.android.gms.common.internal.safeparcel.zzb.zzx((Parcel)var3_3, var4_4));
                        break block31;
                    }
                    case 1: {
                        var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzy((Parcel)var3_3, var4_4);
lbl38: // 2 sources:
                        zzb.zza(var1_1, var2_2);
                        break block31;
                    }
                    case 0: 
                }
                zzb.zza(var1_1, com.google.android.gms.common.internal.safeparcel.zzb.zzw((Parcel)var3_3, var4_4));
            }
            var2_2 = "]";
            ** GOTO lbl92
        }
        switch (var2_2.zzxN()) {
            default: {
                throw new IllegalStateException("Unknown field type out");
            }
            case 11: {
                var3_3 = com.google.android.gms.common.internal.safeparcel.zzb.zzF((Parcel)var3_3, var4_4);
                var3_3.setDataPosition(0);
                this.zza(var1_1, var2_2.zzxV(), (Parcel)var3_3);
                return;
            }
            case 10: {
                var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzs((Parcel)var3_3, var4_4);
                var3_3 = var2_2.keySet();
                var3_3.size();
                var1_1.append("{");
                var3_3 = var3_3.iterator();
                var4_4 = 1;
                while (var3_3.hasNext()) {
                    var6_6 = (String)var3_3.next();
                    if (var4_4 == 0) {
                        var1_1.append(",");
                    }
                    var1_1.append("\"");
                    var1_1.append(var6_6);
                    var1_1.append("\"");
                    var1_1.append(":");
                    var1_1.append("\"");
                    var1_1.append(zzp.zzdC(var2_2.getString(var6_6)));
                    var1_1.append("\"");
                    var4_4 = 0;
                }
                var2_2 = "}";
                ** GOTO lbl92
            }
            case 9: {
                var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzt((Parcel)var3_3, var4_4);
                var1_1.append("\"");
                var2_2 = com.google.android.gms.common.util.zzc.zzr((byte[])var2_2);
                ** GOTO lbl90
            }
            case 8: {
                var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzt((Parcel)var3_3, var4_4);
                var1_1.append("\"");
                var2_2 = com.google.android.gms.common.util.zzc.zzq((byte[])var2_2);
                ** GOTO lbl90
            }
            case 7: {
                var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzq((Parcel)var3_3, var4_4);
                var1_1.append("\"");
                var2_2 = zzp.zzdC((String)var2_2);
lbl90: // 3 sources:
                var1_1.append((String)var2_2);
                var2_2 = "\"";
lbl92: // 3 sources:
                var1_1.append((String)var2_2);
                return;
            }
            case 6: {
                var1_1.append(com.google.android.gms.common.internal.safeparcel.zzb.zzc((Parcel)var3_3, var4_4));
                return;
            }
            case 5: {
                var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzp((Parcel)var3_3, var4_4);
                ** GOTO lbl111
            }
            case 4: {
                var1_1.append(com.google.android.gms.common.internal.safeparcel.zzb.zzn((Parcel)var3_3, var4_4));
                return;
            }
            case 3: {
                var1_1.append(com.google.android.gms.common.internal.safeparcel.zzb.zzl((Parcel)var3_3, var4_4));
                return;
            }
            case 2: {
                var1_1.append(com.google.android.gms.common.internal.safeparcel.zzb.zzi((Parcel)var3_3, var4_4));
                return;
            }
            case 1: {
                var2_2 = com.google.android.gms.common.internal.safeparcel.zzb.zzk((Parcel)var3_3, var4_4);
lbl111: // 2 sources:
                var1_1.append(var2_2);
                return;
            }
            case 0: 
        }
        var1_1.append(com.google.android.gms.common.internal.safeparcel.zzb.zzg((Parcel)var3_3, var4_4));
    }

    private void zzb(StringBuilder stringBuilder, zzack.zza<?, ?> zza2, Object object) {
        if (zza2.zzxM()) {
            this.zzb(stringBuilder, zza2, (ArrayList)object);
            return;
        }
        this.zza(stringBuilder, zza2.zzxL(), object);
    }

    private void zzb(StringBuilder stringBuilder, zzack.zza<?, ?> zza2, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            this.zza(stringBuilder, zza2.zzxL(), arrayList.get(i));
        }
        stringBuilder.append("]");
    }

    public static HashMap<String, String> zzr(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String string : bundle.keySet()) {
            hashMap.put(string, bundle.getString(string));
        }
        return hashMap;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    @Override
    public String toString() {
        zzac.zzb(this.zzaFI, (Object)"Cannot convert to JSON on client side.");
        Parcel parcel = this.zzya();
        parcel.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        this.zza(stringBuilder, this.zzaFI.zzdA(this.mClassName), parcel);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacs.zza(this, parcel, n);
    }

    @Override
    public Object zzdw(String string) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override
    public boolean zzdx(String string) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override
    public Map<String, zzack.zza<?, ?>> zzxK() {
        if (this.zzaFI == null) {
            return null;
        }
        return this.zzaFI.zzdA(this.mClassName);
    }

    public Parcel zzya() {
        switch (this.zzaFR) {
            default: {
                break;
            }
            case 0: {
                this.zzaFS = zzc.zzaV(this.zzaFP);
            }
            case 1: {
                zzc.zzJ(this.zzaFP, this.zzaFS);
                this.zzaFR = 2;
            }
        }
        return this.zzaFP;
    }

    zzaco zzyb() {
        switch (this.zzaFQ) {
            default: {
                int n = this.zzaFQ;
                StringBuilder stringBuilder = new StringBuilder(34);
                stringBuilder.append("Invalid creation type: ");
                stringBuilder.append(n);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 2: {
                return this.zzaFI;
            }
            case 1: {
                return this.zzaFI;
            }
            case 0: 
        }
        return null;
    }
}
