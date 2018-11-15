/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzd;
import java.lang.ref.WeakReference;

static abstract class zzd.zzc
extends zzd.zza {
    private static final WeakReference<byte[]> zzaxj = new WeakReference<Object>(null);
    private WeakReference<byte[]> zzaxi = zzaxj;

    zzd.zzc(byte[] arrby) {
        super(arrby);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    byte[] getBytes() {
        synchronized (this) {
            byte[] arrby;
            byte[] arrby2 = arrby = (byte[])this.zzaxi.get();
            if (arrby == null) {
                arrby2 = this.zzuD();
                this.zzaxi = new WeakReference<byte[]>(arrby2);
            }
            return arrby2;
        }
    }

    protected abstract byte[] zzuD();
}
