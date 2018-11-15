/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzu;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class zzaa
extends ByteArrayOutputStream {
    private final zzu zzap;

    public zzaa(zzu zzu2, int n) {
        this.zzap = zzu2;
        this.buf = this.zzap.zzb(Math.max(n, 256));
    }

    private void zzd(int n) {
        if (this.count + n <= this.buf.length) {
            return;
        }
        byte[] arrby = this.zzap.zzb((this.count + n) * 2);
        System.arraycopy(this.buf, 0, arrby, 0, this.count);
        this.zzap.zza(this.buf);
        this.buf = arrby;
    }

    @Override
    public void close() throws IOException {
        this.zzap.zza(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() {
        this.zzap.zza(this.buf);
    }

    @Override
    public void write(int n) {
        synchronized (this) {
            this.zzd(1);
            super.write(n);
            return;
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) {
        synchronized (this) {
            this.zzd(n2);
            super.write(arrby, n, n2);
            return;
        }
    }
}
