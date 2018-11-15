/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbum;
import java.io.IOException;

public static class zzbum.zza
extends IOException {
    zzbum.zza(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(108);
        stringBuilder.append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ");
        stringBuilder.append(n);
        stringBuilder.append(" limit ");
        stringBuilder.append(n2);
        stringBuilder.append(").");
        super(stringBuilder.toString());
    }
}
