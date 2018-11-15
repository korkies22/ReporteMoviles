/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbuu;
import java.io.IOException;

public abstract class zzbut {
    protected volatile int zzcsg = -1;

    public static final <T extends zzbut> T zza(T t, byte[] arrby) throws zzbus {
        return zzbut.zzb(t, arrby, 0, arrby.length);
    }

    public static final void zza(zzbut zzbut2, byte[] object, int n, int n2) {
        try {
            object = zzbum.zzc((byte[])object, n, n2);
            zzbut2.zza((zzbum)object);
            object.zzacM();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", iOException);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final <T extends zzbut> T zzb(T t, byte[] object, int n, int n2) throws zzbus {
        try {
            object = zzbul.zzb((byte[])object, n, n2);
            t.zzb((zzbul)object);
            object.zzqg(0);
        }
        catch (zzbus zzbus2) {
            throw zzbus2;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
        return t;
    }

    public static final byte[] zzf(zzbut zzbut2) {
        byte[] arrby = new byte[zzbut2.zzacZ()];
        zzbut.zza(zzbut2, arrby, 0, arrby.length);
        return arrby;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzacO();
    }

    public String toString() {
        return zzbuu.zzg(this);
    }

    public void zza(zzbum zzbum2) throws IOException {
    }

    public zzbut zzacO() throws CloneNotSupportedException {
        return (zzbut)super.clone();
    }

    public int zzacY() {
        if (this.zzcsg < 0) {
            this.zzacZ();
        }
        return this.zzcsg;
    }

    public int zzacZ() {
        int n;
        this.zzcsg = n = this.zzv();
        return n;
    }

    public abstract zzbut zzb(zzbul var1) throws IOException;

    protected int zzv() {
        return 0;
    }
}
