/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;
import java.nio.charset.Charset;

public final class zzbul {
    private final byte[] buffer;
    private int zzcrN;
    private int zzcrO;
    private int zzcrP;
    private int zzcrQ;
    private int zzcrR;
    private int zzcrS = Integer.MAX_VALUE;
    private int zzcrT;
    private int zzcrU = 64;
    private int zzcrV = 67108864;

    private zzbul(byte[] arrby, int n, int n2) {
        this.buffer = arrby;
        this.zzcrN = n;
        this.zzcrO = n2 + n;
        this.zzcrQ = n;
    }

    public static long zzaV(long l) {
        return l >>> 1 ^ - (l & 1L);
    }

    private void zzacH() {
        this.zzcrO += this.zzcrP;
        int n = this.zzcrO;
        if (n > this.zzcrS) {
            this.zzcrP = n - this.zzcrS;
            this.zzcrO -= this.zzcrP;
            return;
        }
        this.zzcrP = 0;
    }

    public static zzbul zzad(byte[] arrby) {
        return zzbul.zzb(arrby, 0, arrby.length);
    }

    public static zzbul zzb(byte[] arrby, int n, int n2) {
        return new zzbul(arrby, n, n2);
    }

    public static int zzqi(int n) {
        return - (n & 1) ^ n >>> 1;
    }

    public int getPosition() {
        return this.zzcrQ - this.zzcrN;
    }

    public byte[] readBytes() throws IOException {
        int n = this.zzacD();
        if (n < 0) {
            throw zzbus.zzacS();
        }
        if (n == 0) {
            return zzbuw.zzcsp;
        }
        if (n > this.zzcrO - this.zzcrQ) {
            throw zzbus.zzacR();
        }
        byte[] arrby = new byte[n];
        System.arraycopy(this.buffer, this.zzcrQ, arrby, 0, n);
        this.zzcrQ += n;
        return arrby;
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.zzacG());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.zzacF());
    }

    public String readString() throws IOException {
        int n = this.zzacD();
        if (n < 0) {
            throw zzbus.zzacS();
        }
        if (n > this.zzcrO - this.zzcrQ) {
            throw zzbus.zzacR();
        }
        String string = new String(this.buffer, this.zzcrQ, n, zzbur.UTF_8);
        this.zzcrQ += n;
        return string;
    }

    public byte[] zzE(int n, int n2) {
        if (n2 == 0) {
            return zzbuw.zzcsp;
        }
        byte[] arrby = new byte[n2];
        int n3 = this.zzcrN;
        System.arraycopy(this.buffer, n3 + n, arrby, 0, n2);
        return arrby;
    }

    public void zza(zzbut zzbut2) throws IOException {
        int n = this.zzacD();
        if (this.zzcrT >= this.zzcrU) {
            throw zzbus.zzacX();
        }
        n = this.zzqj(n);
        ++this.zzcrT;
        zzbut2.zzb(this);
        this.zzqg(0);
        --this.zzcrT;
        this.zzqk(n);
    }

    public void zza(zzbut zzbut2, int n) throws IOException {
        if (this.zzcrT >= this.zzcrU) {
            throw zzbus.zzacX();
        }
        ++this.zzcrT;
        zzbut2.zzb(this);
        this.zzqg(zzbuw.zzK(n, 4));
        --this.zzcrT;
    }

    public boolean zzacA() throws IOException {
        if (this.zzacD() != 0) {
            return true;
        }
        return false;
    }

    public int zzacB() throws IOException {
        return zzbul.zzqi(this.zzacD());
    }

    public long zzacC() throws IOException {
        return zzbul.zzaV(this.zzacE());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int zzacD() throws IOException {
        int n = this.zzacK();
        if (n >= 0) {
            return n;
        }
        n &= 127;
        int n2 = this.zzacK();
        if (n2 >= 0) {
            n2 <<= 7;
            do {
                return n | n2;
                break;
            } while (true);
        }
        n |= (n2 & 127) << 7;
        n2 = this.zzacK();
        if (n2 >= 0) {
            n2 <<= 14;
            return n | n2;
        }
        n |= (n2 & 127) << 14;
        n2 = this.zzacK();
        if (n2 >= 0) {
            n2 <<= 21;
            return n | n2;
        }
        byte by = this.zzacK();
        n2 = n | (n2 & 127) << 21 | by << 28;
        if (by >= 0) return n2;
        for (n = 0; n < 5; ++n) {
            if (this.zzacK() < 0) continue;
            return n2;
        }
        throw zzbus.zzacT();
    }

    public long zzacE() throws IOException {
        long l = 0L;
        for (int i = 0; i < 64; i += 7) {
            byte by = this.zzacK();
            l |= (long)(by & 127) << i;
            if ((by & 128) != 0) continue;
            return l;
        }
        throw zzbus.zzacT();
    }

    public int zzacF() throws IOException {
        return this.zzacK() & 255 | (this.zzacK() & 255) << 8 | (this.zzacK() & 255) << 16 | (this.zzacK() & 255) << 24;
    }

    public long zzacG() throws IOException {
        byte by = this.zzacK();
        byte by2 = this.zzacK();
        byte by3 = this.zzacK();
        byte by4 = this.zzacK();
        byte by5 = this.zzacK();
        byte by6 = this.zzacK();
        byte by7 = this.zzacK();
        byte by8 = this.zzacK();
        return (long)by & 255L | ((long)by2 & 255L) << 8 | ((long)by3 & 255L) << 16 | ((long)by4 & 255L) << 24 | ((long)by5 & 255L) << 32 | ((long)by6 & 255L) << 40 | ((long)by7 & 255L) << 48 | ((long)by8 & 255L) << 56;
    }

    public int zzacI() {
        if (this.zzcrS == Integer.MAX_VALUE) {
            return -1;
        }
        int n = this.zzcrQ;
        return this.zzcrS - n;
    }

    public boolean zzacJ() {
        if (this.zzcrQ == this.zzcrO) {
            return true;
        }
        return false;
    }

    public byte zzacK() throws IOException {
        if (this.zzcrQ == this.zzcrO) {
            throw zzbus.zzacR();
        }
        byte[] arrby = this.buffer;
        int n = this.zzcrQ;
        this.zzcrQ = n + 1;
        return arrby[n];
    }

    public int zzacu() throws IOException {
        if (this.zzacJ()) {
            this.zzcrR = 0;
            return 0;
        }
        this.zzcrR = this.zzacD();
        if (this.zzcrR == 0) {
            throw zzbus.zzacU();
        }
        return this.zzcrR;
    }

    public void zzacv() throws IOException {
        int n;
        while ((n = this.zzacu()) != 0 && this.zzqh(n)) {
        }
    }

    public long zzacw() throws IOException {
        return this.zzacE();
    }

    public long zzacx() throws IOException {
        return this.zzacE();
    }

    public int zzacy() throws IOException {
        return this.zzacD();
    }

    public long zzacz() throws IOException {
        return this.zzacG();
    }

    public void zzqg(int n) throws zzbus {
        if (this.zzcrR != n) {
            throw zzbus.zzacV();
        }
    }

    public boolean zzqh(int n) throws IOException {
        switch (zzbuw.zzqA(n)) {
            default: {
                throw zzbus.zzacW();
            }
            case 5: {
                this.zzacF();
                return true;
            }
            case 4: {
                return false;
            }
            case 3: {
                this.zzacv();
                this.zzqg(zzbuw.zzK(zzbuw.zzqB(n), 4));
                return true;
            }
            case 2: {
                this.zzqm(this.zzacD());
                return true;
            }
            case 1: {
                this.zzacG();
                return true;
            }
            case 0: 
        }
        this.zzacy();
        return true;
    }

    public int zzqj(int n) throws zzbus {
        if (n < 0) {
            throw zzbus.zzacS();
        }
        int n2 = this.zzcrS;
        if ((n += this.zzcrQ) > n2) {
            throw zzbus.zzacR();
        }
        this.zzcrS = n;
        this.zzacH();
        return n2;
    }

    public void zzqk(int n) {
        this.zzcrS = n;
        this.zzacH();
    }

    public void zzql(int n) {
        if (n > this.zzcrQ - this.zzcrN) {
            int n2 = this.zzcrQ;
            int n3 = this.zzcrN;
            StringBuilder stringBuilder = new StringBuilder(50);
            stringBuilder.append("Position ");
            stringBuilder.append(n);
            stringBuilder.append(" is beyond current ");
            stringBuilder.append(n2 - n3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder(24);
            stringBuilder.append("Bad position ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.zzcrQ = this.zzcrN + n;
    }

    public void zzqm(int n) throws IOException {
        if (n < 0) {
            throw zzbus.zzacS();
        }
        if (this.zzcrQ + n > this.zzcrS) {
            this.zzqm(this.zzcrS - this.zzcrQ);
            throw zzbus.zzacR();
        }
        if (n <= this.zzcrO - this.zzcrQ) {
            this.zzcrQ += n;
            return;
        }
        throw zzbus.zzacR();
    }
}
