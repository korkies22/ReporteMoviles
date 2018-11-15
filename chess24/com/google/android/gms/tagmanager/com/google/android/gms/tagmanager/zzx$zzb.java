/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzx;
import java.util.Arrays;

private static class zzx.zzb {
    final String zzAH;
    final byte[] zzbDS;

    zzx.zzb(String string, byte[] arrby) {
        this.zzAH = string;
        this.zzbDS = arrby;
    }

    public String toString() {
        String string = this.zzAH;
        int n = Arrays.hashCode(this.zzbDS);
        StringBuilder stringBuilder = new StringBuilder(54 + String.valueOf(string).length());
        stringBuilder.append("KeyAndSerialized: key = ");
        stringBuilder.append(string);
        stringBuilder.append(" serialized hash = ");
        stringBuilder.append(n);
        return stringBuilder.toString();
    }
}
