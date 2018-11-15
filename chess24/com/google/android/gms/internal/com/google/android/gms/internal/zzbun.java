/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbuo;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbuq;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuv;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public abstract class zzbun<M extends zzbun<M>>
extends zzbut {
    protected zzbup zzcrX;

    @Override
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzacN();
    }

    public final <T> T zza(zzbuo<M, T> zzbuo2) {
        if (this.zzcrX == null) {
            return null;
        }
        zzbuq zzbuq2 = this.zzcrX.zzqx(zzbuw.zzqB(zzbuo2.tag));
        if (zzbuq2 == null) {
            return null;
        }
        return zzbuq2.zzb(zzbuo2);
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzcrX == null) {
            return;
        }
        for (int i = 0; i < this.zzcrX.size(); ++i) {
            this.zzcrX.zzqy(i).zza(zzbum2);
        }
    }

    protected final boolean zza(zzbul object, int n) throws IOException {
        int n2 = object.getPosition();
        if (!object.zzqh(n)) {
            return false;
        }
        int n3 = zzbuw.zzqB(n);
        zzbuv zzbuv2 = new zzbuv(n, object.zzE(n2, object.getPosition() - n2));
        object = null;
        if (this.zzcrX == null) {
            this.zzcrX = new zzbup();
        } else {
            object = this.zzcrX.zzqx(n3);
        }
        Object object2 = object;
        if (object == null) {
            object2 = new zzbuq();
            this.zzcrX.zza(n3, (zzbuq)object2);
        }
        object2.zza(zzbuv2);
        return true;
    }

    public M zzacN() throws CloneNotSupportedException {
        zzbun zzbun2 = (zzbun)super.zzacO();
        zzbur.zza(this, zzbun2);
        return (M)zzbun2;
    }

    @Override
    public /* synthetic */ zzbut zzacO() throws CloneNotSupportedException {
        return (zzbun)this.clone();
    }

    @Override
    protected int zzv() {
        int n;
        zzbup zzbup2 = this.zzcrX;
        int n2 = 0;
        if (zzbup2 != null) {
            int n3 = 0;
            do {
                n = n3;
                if (n2 < this.zzcrX.size()) {
                    n3 += this.zzcrX.zzqy(n2).zzv();
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            n = 0;
        }
        return n;
    }
}
