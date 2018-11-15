/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbuo;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class zzbuq
implements Cloneable {
    private Object value;
    private zzbuo<?, ?> zzcsd;
    private List<zzbuv> zzcse = new ArrayList<zzbuv>();

    zzbuq() {
    }

    private byte[] toByteArray() throws IOException {
        byte[] arrby = new byte[this.zzv()];
        this.zza(zzbum.zzae(arrby));
        return arrby;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzacQ();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzbuq)) {
            return false;
        }
        object = (zzbuq)object;
        if (this.value != null && object.value != null) {
            if (this.zzcsd != object.zzcsd) {
                return false;
            }
            if (!this.zzcsd.zzciF.isArray()) {
                return this.value.equals(object.value);
            }
            if (this.value instanceof byte[]) {
                return Arrays.equals((byte[])this.value, (byte[])object.value);
            }
            if (this.value instanceof int[]) {
                return Arrays.equals((int[])this.value, (int[])object.value);
            }
            if (this.value instanceof long[]) {
                return Arrays.equals((long[])this.value, (long[])object.value);
            }
            if (this.value instanceof float[]) {
                return Arrays.equals((float[])this.value, (float[])object.value);
            }
            if (this.value instanceof double[]) {
                return Arrays.equals((double[])this.value, (double[])object.value);
            }
            if (this.value instanceof boolean[]) {
                return Arrays.equals((boolean[])this.value, (boolean[])object.value);
            }
            return Arrays.deepEquals((Object[])this.value, (Object[])object.value);
        }
        if (this.zzcse != null && object.zzcse != null) {
            return this.zzcse.equals(object.zzcse);
        }
        try {
            boolean bl = Arrays.equals(this.toByteArray(), zzbuq.super.toByteArray());
            return bl;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    public int hashCode() {
        try {
            int n = Arrays.hashCode(this.toByteArray());
            return 527 + n;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    void zza(zzbum zzbum2) throws IOException {
        if (this.value != null) {
            this.zzcsd.zza(this.value, zzbum2);
            return;
        }
        Iterator<zzbuv> iterator = this.zzcse.iterator();
        while (iterator.hasNext()) {
            iterator.next().zza(zzbum2);
        }
    }

    void zza(zzbuv zzbuv2) {
        this.zzcse.add(zzbuv2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final zzbuq zzacQ() {
        zzbuq zzbuq2 = new zzbuq();
        try {
            int n;
            zzbut[] arrzzbut;
            block20 : {
                Object object;
                block14 : {
                    block19 : {
                        block18 : {
                            block17 : {
                                block16 : {
                                    block15 : {
                                        block13 : {
                                            zzbuq2.zzcsd = this.zzcsd;
                                            if (this.zzcse == null) {
                                                zzbuq2.zzcse = null;
                                            } else {
                                                zzbuq2.zzcse.addAll(this.zzcse);
                                            }
                                            if (this.value == null) {
                                                return zzbuq2;
                                            }
                                            if (!(this.value instanceof zzbut)) break block13;
                                            object = (zzbut)((zzbut)this.value).clone();
                                            break block14;
                                        }
                                        if (!(this.value instanceof byte[])) break block15;
                                        object = ((byte[])this.value).clone();
                                        break block14;
                                    }
                                    boolean bl = this.value instanceof byte[][];
                                    int n2 = 0;
                                    if (bl) {
                                        byte[][] arrarrby;
                                        byte[][] arrby = (byte[][])this.value;
                                        zzbuq2.value = arrarrby = new byte[arrby.length][];
                                        for (n = 0; n < arrby.length; ++n) {
                                            arrarrby[n] = (byte[])arrby[n].clone();
                                        }
                                        return zzbuq2;
                                    }
                                    if (!(this.value instanceof boolean[])) break block16;
                                    object = ((boolean[])this.value).clone();
                                    break block14;
                                }
                                if (!(this.value instanceof int[])) break block17;
                                object = ((int[])this.value).clone();
                                break block14;
                            }
                            if (!(this.value instanceof long[])) break block18;
                            object = ((long[])this.value).clone();
                            break block14;
                        }
                        if (!(this.value instanceof float[])) break block19;
                        object = ((float[])this.value).clone();
                        break block14;
                    }
                    if (!(this.value instanceof double[])) break block20;
                    object = ((double[])this.value).clone();
                }
                zzbuq2.value = object;
                return zzbuq2;
            }
            if (!(this.value instanceof zzbut[])) return zzbuq2;
            zzbut[] arrzzbut2 = (zzbut[])this.value;
            zzbuq2.value = arrzzbut = new zzbut[arrzzbut2.length];
            for (n = n2; n < arrzzbut2.length; ++n) {
                arrzzbut[n] = (zzbut)arrzzbut2[n].clone();
            }
            return zzbuq2;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    <T> T zzb(zzbuo<?, T> zzbuo2) {
        if (this.value != null) {
            if (!this.zzcsd.equals(zzbuo2)) {
                throw new IllegalStateException("Tried to getExtension with a different Extension.");
            }
        } else {
            this.zzcsd = zzbuo2;
            this.value = zzbuo2.zzZ(this.zzcse);
            this.zzcse = null;
        }
        return (T)this.value;
    }

    int zzv() {
        if (this.value != null) {
            return this.zzcsd.zzaR(this.value);
        }
        Iterator<zzbuv> iterator = this.zzcse.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            n += iterator.next().zzv();
        }
        return n;
    }
}
