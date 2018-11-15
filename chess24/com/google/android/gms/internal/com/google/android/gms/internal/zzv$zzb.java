/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzv;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

private static class zzv.zzb
extends FilterInputStream {
    private int zzaB = 0;

    private zzv.zzb(InputStream inputStream) {
        super(inputStream);
    }

    static /* synthetic */ int zza(zzv.zzb zzb2) {
        return zzb2.zzaB;
    }

    @Override
    public int read() throws IOException {
        int n = super.read();
        if (n != -1) {
            ++this.zzaB;
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n = super.read(arrby, n, n2)) != -1) {
            this.zzaB += n;
        }
        return n;
    }
}
