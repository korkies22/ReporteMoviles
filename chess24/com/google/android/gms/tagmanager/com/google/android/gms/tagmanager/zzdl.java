/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

class zzdl
extends Number
implements Comparable<zzdl> {
    private double zzbGD;
    private long zzbGE;
    private boolean zzbGF;

    private zzdl(double d) {
        this.zzbGD = d;
        this.zzbGF = false;
    }

    private zzdl(long l) {
        this.zzbGE = l;
        this.zzbGF = true;
    }

    public static zzdl zza(Double d) {
        return new zzdl(d);
    }

    public static zzdl zzax(long l) {
        return new zzdl(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzdl zzhy(String string) throws NumberFormatException {
        try {
            return new zzdl(Long.parseLong(string));
        }
        catch (NumberFormatException numberFormatException) {}
        try {
            return new zzdl(Double.parseDouble(string));
        }
        catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException(String.valueOf(string).concat(" is not a valid TypedNumber"));
        }
    }

    @Override
    public byte byteValue() {
        return (byte)this.longValue();
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.zza((zzdl)object);
    }

    @Override
    public double doubleValue() {
        if (this.zzQc()) {
            return this.zzbGE;
        }
        return this.zzbGD;
    }

    public boolean equals(Object object) {
        if (object instanceof zzdl && this.zza((zzdl)object) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public float floatValue() {
        return (float)this.doubleValue();
    }

    public int hashCode() {
        return new Long(this.longValue()).hashCode();
    }

    @Override
    public int intValue() {
        return this.zzQe();
    }

    @Override
    public long longValue() {
        return this.zzQd();
    }

    @Override
    public short shortValue() {
        return this.zzQf();
    }

    public String toString() {
        if (this.zzQc()) {
            return Long.toString(this.zzbGE);
        }
        return Double.toString(this.zzbGD);
    }

    public boolean zzQb() {
        return this.zzQc() ^ true;
    }

    public boolean zzQc() {
        return this.zzbGF;
    }

    public long zzQd() {
        if (this.zzQc()) {
            return this.zzbGE;
        }
        return (long)this.zzbGD;
    }

    public int zzQe() {
        return (int)this.longValue();
    }

    public short zzQf() {
        return (short)this.longValue();
    }

    public int zza(zzdl zzdl2) {
        if (this.zzQc() && zzdl2.zzQc()) {
            return new Long(this.zzbGE).compareTo(zzdl2.zzbGE);
        }
        return Double.compare(this.doubleValue(), zzdl2.doubleValue());
    }
}
