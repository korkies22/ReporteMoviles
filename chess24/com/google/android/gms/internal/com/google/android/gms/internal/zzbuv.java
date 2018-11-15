/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbum;
import java.io.IOException;
import java.util.Arrays;

final class zzbuv {
    final int tag;
    final byte[] zzcsh;

    zzbuv(int n, byte[] arrby) {
        this.tag = n;
        this.zzcsh = arrby;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzbuv)) {
            return false;
        }
        object = (zzbuv)object;
        if (this.tag == object.tag && Arrays.equals(this.zzcsh, object.zzcsh)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 * (527 + this.tag) + Arrays.hashCode(this.zzcsh);
    }

    void zza(zzbum zzbum2) throws IOException {
        zzbum2.zzqt(this.tag);
        zzbum2.zzah(this.zzcsh);
    }

    int zzv() {
        return 0 + zzbum.zzqu(this.tag) + this.zzcsh.length;
    }
}
