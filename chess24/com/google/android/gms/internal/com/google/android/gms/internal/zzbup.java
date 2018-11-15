/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbuq;

public final class zzbup
implements Cloneable {
    private static final zzbuq zzcrZ = new zzbuq();
    private int mSize;
    private boolean zzcsa = false;
    private int[] zzcsb;
    private zzbuq[] zzcsc;

    zzbup() {
        this(10);
    }

    zzbup(int n) {
        n = this.idealIntArraySize(n);
        this.zzcsb = new int[n];
        this.zzcsc = new zzbuq[n];
        this.mSize = 0;
    }

    private int idealByteArraySize(int n) {
        for (int i = 4; i < 32; ++i) {
            int n2 = (1 << i) - 12;
            if (n > n2) continue;
            return n2;
        }
        return n;
    }

    private int idealIntArraySize(int n) {
        return this.idealByteArraySize(n * 4) / 4;
    }

    private boolean zza(int[] arrn, int[] arrn2, int n) {
        for (int i = 0; i < n; ++i) {
            if (arrn[i] == arrn2[i]) continue;
            return false;
        }
        return true;
    }

    private boolean zza(zzbuq[] arrzzbuq, zzbuq[] arrzzbuq2, int n) {
        for (int i = 0; i < n; ++i) {
            if (arrzzbuq[i].equals(arrzzbuq2[i])) continue;
            return false;
        }
        return true;
    }

    private int zzqz(int n) {
        int n2 = this.mSize - 1;
        int n3 = 0;
        while (n3 <= n2) {
            int n4 = n3 + n2 >>> 1;
            int n5 = this.zzcsb[n4];
            if (n5 < n) {
                n3 = n4 + 1;
                continue;
            }
            if (n5 > n) {
                n2 = n4 - 1;
                continue;
            }
            return n4;
        }
        return ~ n3;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzacP();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzbup)) {
            return false;
        }
        object = (zzbup)object;
        if (this.size() != object.size()) {
            return false;
        }
        if (this.zza(this.zzcsb, object.zzcsb, this.mSize) && this.zza(this.zzcsc, object.zzcsc, this.mSize)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int n = 17;
        for (int i = 0; i < this.mSize; ++i) {
            int n2 = this.zzcsb[i];
            n = this.zzcsc[i].hashCode() + 31 * (n * 31 + n2);
        }
        return n;
    }

    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return false;
    }

    int size() {
        return this.mSize;
    }

    void zza(int n, zzbuq zzbuq2) {
        int[] arrn;
        Object[] arrobject;
        int n2;
        int n3 = this.zzqz(n);
        if (n3 >= 0) {
            this.zzcsc[n3] = zzbuq2;
            return;
        }
        if ((n3 ^= -1) < this.mSize && this.zzcsc[n3] == zzcrZ) {
            this.zzcsb[n3] = n;
            this.zzcsc[n3] = zzbuq2;
            return;
        }
        if (this.mSize >= this.zzcsb.length) {
            n2 = this.idealIntArraySize(this.mSize + 1);
            arrn = new int[n2];
            arrobject = new zzbuq[n2];
            System.arraycopy(this.zzcsb, 0, arrn, 0, this.zzcsb.length);
            System.arraycopy(this.zzcsc, 0, arrobject, 0, this.zzcsc.length);
            this.zzcsb = arrn;
            this.zzcsc = arrobject;
        }
        if (this.mSize - n3 != 0) {
            arrn = this.zzcsb;
            arrobject = this.zzcsb;
            n2 = n3 + 1;
            System.arraycopy(arrn, n3, arrobject, n2, this.mSize - n3);
            System.arraycopy(this.zzcsc, n3, this.zzcsc, n2, this.mSize - n3);
        }
        this.zzcsb[n3] = n;
        this.zzcsc[n3] = zzbuq2;
        ++this.mSize;
    }

    public final zzbup zzacP() {
        int n = this.size();
        zzbup zzbup2 = new zzbup(n);
        int[] arrn = this.zzcsb;
        int[] arrn2 = zzbup2.zzcsb;
        System.arraycopy(arrn, 0, arrn2, 0, n);
        for (int i = 0; i < n; ++i) {
            if (this.zzcsc[i] == null) continue;
            zzbup2.zzcsc[i] = (zzbuq)this.zzcsc[i].clone();
        }
        zzbup2.mSize = n;
        return zzbup2;
    }

    zzbuq zzqx(int n) {
        if ((n = this.zzqz(n)) >= 0 && this.zzcsc[n] != zzcrZ) {
            return this.zzcsc[n];
        }
        return null;
    }

    zzbuq zzqy(int n) {
        return this.zzcsc[n];
    }
}
