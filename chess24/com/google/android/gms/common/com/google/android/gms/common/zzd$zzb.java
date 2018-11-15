/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzd;
import java.util.Arrays;

static class zzd.zzb
extends zzd.zza {
    private final byte[] zzaxh;

    zzd.zzb(byte[] arrby) {
        super(Arrays.copyOfRange(arrby, 0, 25));
        this.zzaxh = arrby;
    }

    @Override
    byte[] getBytes() {
        return this.zzaxh;
    }
}
