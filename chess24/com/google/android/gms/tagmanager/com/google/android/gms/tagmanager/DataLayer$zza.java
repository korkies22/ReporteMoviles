/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import java.util.Arrays;

static final class DataLayer.zza {
    public final String zzAH;
    public final Object zzYe;

    DataLayer.zza(String string, Object object) {
        this.zzAH = string;
        this.zzYe = object;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DataLayer.zza;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (DataLayer.zza)object;
        bl = bl2;
        if (this.zzAH.equals(object.zzAH)) {
            bl = bl2;
            if (this.zzYe.equals(object.zzYe)) {
                bl = true;
            }
        }
        return bl;
    }

    public int hashCode() {
        return Arrays.hashCode((Object[])new Integer[]{this.zzAH.hashCode(), this.zzYe.hashCode()});
    }

    public String toString() {
        String string = this.zzAH;
        String string2 = String.valueOf(this.zzYe.toString());
        StringBuilder stringBuilder = new StringBuilder(13 + String.valueOf(string).length() + String.valueOf(string2).length());
        stringBuilder.append("Key: ");
        stringBuilder.append(string);
        stringBuilder.append(" value: ");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }
}
