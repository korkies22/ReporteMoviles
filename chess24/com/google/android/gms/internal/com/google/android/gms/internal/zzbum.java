/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class zzbum {
    private final ByteBuffer zzcrW;

    private zzbum(ByteBuffer byteBuffer) {
        this.zzcrW = byteBuffer;
        this.zzcrW.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzbum(byte[] arrby, int n, int n2) {
        this(ByteBuffer.wrap(arrby, n, n2));
    }

    public static int zzH(int n, int n2) {
        return zzbum.zzqs(n) + zzbum.zzqp(n2);
    }

    public static int zzI(int n, int n2) {
        return zzbum.zzqs(n) + zzbum.zzqq(n2);
    }

    private static int zza(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        while (n < n2) {
            int n4;
            char c = charSequence.charAt(n);
            if (c < '\u0800') {
                n3 += 127 - c >>> 31;
                n4 = n;
            } else {
                int n5;
                n3 = n5 = n3 + 2;
                n4 = n;
                if ('\ud800' <= c) {
                    n3 = n5;
                    n4 = n;
                    if (c <= '\udfff') {
                        if (Character.codePointAt(charSequence, n) < 65536) {
                            charSequence = new StringBuilder(39);
                            charSequence.append("Unpaired surrogate at index ");
                            charSequence.append(n);
                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        }
                        n4 = n + 1;
                        n3 = n5;
                    }
                }
            }
            n = n4 + 1;
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int zza(CharSequence charSequence, byte[] arrby, int n, int n2) {
        int n3;
        int n4;
        int n5 = charSequence.length();
        int n6 = n2 + n;
        for (n2 = 0; n2 < n5 && (n4 = n2 + n) < n6 && (n3 = charSequence.charAt(n2)) < 128; ++n2) {
            arrby[n4] = (byte)n3;
        }
        if (n2 == n5) {
            return n + n5;
        }
        n4 = n + n2;
        n = n2;
        do {
            if (n >= n5) {
                return n4;
            }
            char c = charSequence.charAt(n);
            if (c < '?' && n4 < n6) {
                n2 = n4 + 1;
                arrby[n4] = (byte)c;
            } else if (c < '\u0800' && n4 <= n6 - 2) {
                n3 = n4 + 1;
                arrby[n4] = (byte)(960 | c >>> 6);
                n2 = n3 + 1;
                arrby[n3] = (byte)(c & 63 | 128);
            } else if ((c < '\ud800' || '\udfff' < c) && n4 <= n6 - 3) {
                n2 = n4 + 1;
                arrby[n4] = (byte)(480 | c >>> 12);
                n4 = n2 + 1;
                arrby[n2] = (byte)(c >>> 6 & 63 | 128);
                n2 = n4 + 1;
                arrby[n4] = (byte)(c & 63 | 128);
            } else {
                if (n4 > n6 - 4) {
                    charSequence = new StringBuilder(37);
                    charSequence.append("Failed writing ");
                    charSequence.append(c);
                    charSequence.append(" at index ");
                    charSequence.append(n4);
                    throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
                }
                n2 = n + 1;
                if (n2 == charSequence.length()) break;
                char c2 = charSequence.charAt(n2);
                if (!Character.isSurrogatePair(c, c2)) {
                    n = n2;
                    break;
                }
                n = Character.toCodePoint(c, c2);
                n3 = n4 + 1;
                arrby[n4] = (byte)(240 | n >>> 18);
                n4 = n3 + 1;
                arrby[n3] = (byte)(n >>> 12 & 63 | 128);
                n3 = n4 + 1;
                arrby[n4] = (byte)(n >>> 6 & 63 | 128);
                n4 = n3 + 1;
                arrby[n3] = (byte)(n & 63 | 128);
                n = n2;
                n2 = n4;
            }
            ++n;
            n4 = n2;
        } while (true);
        charSequence = new StringBuilder(39);
        charSequence.append("Unpaired surrogate at index ");
        charSequence.append(n - 1);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static void zza(CharSequence charSequence, ByteBuffer object) {
        if (object.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (object.hasArray()) {
            try {
                object.position(zzbum.zza(charSequence, object.array(), object.arrayOffset() + object.position(), object.remaining()) - object.arrayOffset());
                return;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                object = new BufferOverflowException();
                object.initCause(arrayIndexOutOfBoundsException);
                throw object;
            }
        }
        zzbum.zzb(charSequence, (ByteBuffer)object);
    }

    public static zzbum zzae(byte[] arrby) {
        return zzbum.zzc(arrby, 0, arrby.length);
    }

    public static int zzag(byte[] arrby) {
        return zzbum.zzqu(arrby.length) + arrby.length;
    }

    public static int zzb(int n, double d) {
        return zzbum.zzqs(n) + 8;
    }

    public static int zzb(int n, zzbut zzbut2) {
        return zzbum.zzqs(n) * 2 + zzbum.zzd(zzbut2);
    }

    private static int zzb(CharSequence charSequence) {
        int n;
        int n2;
        block3 : {
            int n3;
            n = charSequence.length();
            for (n3 = 0; n3 < n && charSequence.charAt(n3) < '?'; ++n3) {
            }
            int n4 = n;
            do {
                n2 = n4;
                if (n3 >= n) break block3;
                n2 = charSequence.charAt(n3);
                if (n2 >= 2048) break;
                n4 += 127 - n2 >>> 31;
                ++n3;
            } while (true);
            n2 = n4 + zzbum.zza(charSequence, n3);
        }
        if (n2 < n) {
            long l = n2;
            charSequence = new StringBuilder(54);
            charSequence.append("UTF-8 length does not fit in int: ");
            charSequence.append(l + 0x100000000L);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void zzb(CharSequence var0, ByteBuffer var1_1) {
        var6_2 = var0.length();
        var4_3 = 0;
        while (var4_3 < var6_2) {
            block4 : {
                block3 : {
                    var2_4 = var0.charAt(var4_3);
                    if (var2_4 >= 128) break block3;
                    var5_6 = var2_4;
                    ** GOTO lbl34
                }
                if (var2_4 >= 2048) break block4;
                var5_6 = 960 | var2_4 >>> 6;
                ** GOTO lbl32
            }
            if (var2_4 < 55296 || 57343 < var2_4) ** GOTO lbl30
            var5_6 = var4_3 + 1;
            if (var5_6 == var0.length()) ** GOTO lbl26
            var3_5 = var0.charAt(var5_6);
            if (Character.isSurrogatePair((char)var2_4, var3_5)) {
                var4_3 = Character.toCodePoint((char)var2_4, var3_5);
                var1_1.put((byte)(240 | var4_3 >>> 18));
                var1_1.put((byte)(var4_3 >>> 12 & 63 | 128));
                var1_1.put((byte)(var4_3 >>> 6 & 63 | 128));
                var1_1.put((byte)(var4_3 & 63 | 128));
                var4_3 = var5_6;
            } else {
                var4_3 = var5_6;
lbl26: // 2 sources:
                var0 = new StringBuilder(39);
                var0.append("Unpaired surrogate at index ");
                var0.append(var4_3 - 1);
                throw new IllegalArgumentException(var0.toString());
lbl30: // 1 sources:
                var1_1.put((byte)(480 | var2_4 >>> 12));
                var5_6 = var2_4 >>> 6 & 63 | 128;
lbl32: // 2 sources:
                var1_1.put((byte)var5_6);
                var5_6 = var2_4 & 63 | 128;
lbl34: // 2 sources:
                var1_1.put((byte)var5_6);
            }
            ++var4_3;
        }
    }

    public static int zzba(long l) {
        return zzbum.zzbe(l);
    }

    public static int zzbb(long l) {
        return zzbum.zzbe(l);
    }

    public static int zzbc(long l) {
        return zzbum.zzbe(zzbum.zzbg(l));
    }

    public static int zzbe(long l) {
        if ((l & -128L) == 0L) {
            return 1;
        }
        if ((l & -16384L) == 0L) {
            return 2;
        }
        if ((l & -2097152L) == 0L) {
            return 3;
        }
        if ((l & -268435456L) == 0L) {
            return 4;
        }
        if ((l & -34359738368L) == 0L) {
            return 5;
        }
        if ((l & -4398046511104L) == 0L) {
            return 6;
        }
        if ((l & -562949953421312L) == 0L) {
            return 7;
        }
        if ((l & -72057594037927936L) == 0L) {
            return 8;
        }
        if ((l & Long.MIN_VALUE) == 0L) {
            return 9;
        }
        return 10;
    }

    public static long zzbg(long l) {
        return l << 1 ^ l >> 63;
    }

    public static int zzc(int n, zzbut zzbut2) {
        return zzbum.zzqs(n) + zzbum.zze(zzbut2);
    }

    public static int zzc(int n, byte[] arrby) {
        return zzbum.zzqs(n) + zzbum.zzag(arrby);
    }

    public static zzbum zzc(byte[] arrby, int n, int n2) {
        return new zzbum(arrby, n, n2);
    }

    public static int zzd(int n, float f) {
        return zzbum.zzqs(n) + 4;
    }

    public static int zzd(zzbut zzbut2) {
        return zzbut2.zzacZ();
    }

    public static int zze(int n, long l) {
        return zzbum.zzqs(n) + zzbum.zzba(l);
    }

    public static int zze(zzbut zzbut2) {
        int n = zzbut2.zzacZ();
        return zzbum.zzqu(n) + n;
    }

    public static int zzf(int n, long l) {
        return zzbum.zzqs(n) + zzbum.zzbb(l);
    }

    public static int zzg(int n, long l) {
        return zzbum.zzqs(n) + 8;
    }

    public static int zzh(int n, long l) {
        return zzbum.zzqs(n) + zzbum.zzbc(l);
    }

    public static int zzh(int n, boolean bl) {
        return zzbum.zzqs(n) + 1;
    }

    public static int zzkc(String string) {
        int n = zzbum.zzb(string);
        return zzbum.zzqu(n) + n;
    }

    public static int zzqp(int n) {
        if (n >= 0) {
            return zzbum.zzqu(n);
        }
        return 10;
    }

    public static int zzqq(int n) {
        return zzbum.zzqu(zzbum.zzqw(n));
    }

    public static int zzqs(int n) {
        return zzbum.zzqu(zzbuw.zzK(n, 0));
    }

    public static int zzqu(int n) {
        if ((n & -128) == 0) {
            return 1;
        }
        if ((n & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & n) == 0) {
            return 3;
        }
        if ((n & -268435456) == 0) {
            return 4;
        }
        return 5;
    }

    public static int zzqw(int n) {
        return n >> 31 ^ n << 1;
    }

    public static int zzr(int n, String string) {
        return zzbum.zzqs(n) + zzbum.zzkc(string);
    }

    public void zzF(int n, int n2) throws IOException {
        this.zzJ(n, 0);
        this.zzqn(n2);
    }

    public void zzG(int n, int n2) throws IOException {
        this.zzJ(n, 0);
        this.zzqo(n2);
    }

    public void zzJ(int n, int n2) throws IOException {
        this.zzqt(zzbuw.zzK(n, n2));
    }

    public void zza(int n, double d) throws IOException {
        this.zzJ(n, 1);
        this.zzn(d);
    }

    public void zza(int n, long l) throws IOException {
        this.zzJ(n, 0);
        this.zzaW(l);
    }

    public void zza(int n, zzbut zzbut2) throws IOException {
        this.zzJ(n, 2);
        this.zzc(zzbut2);
    }

    public void zzaW(long l) throws IOException {
        this.zzbd(l);
    }

    public void zzaX(long l) throws IOException {
        this.zzbd(l);
    }

    public void zzaY(long l) throws IOException {
        this.zzbf(l);
    }

    public void zzaZ(long l) throws IOException {
        this.zzbd(zzbum.zzbg(l));
    }

    public int zzacL() {
        return this.zzcrW.remaining();
    }

    public void zzacM() {
        if (this.zzacL() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void zzaf(byte[] arrby) throws IOException {
        this.zzqt(arrby.length);
        this.zzah(arrby);
    }

    public void zzah(byte[] arrby) throws IOException {
        this.zzd(arrby, 0, arrby.length);
    }

    public void zzb(int n, long l) throws IOException {
        this.zzJ(n, 0);
        this.zzaX(l);
    }

    public void zzb(int n, byte[] arrby) throws IOException {
        this.zzJ(n, 2);
        this.zzaf(arrby);
    }

    public void zzb(zzbut zzbut2) throws IOException {
        zzbut2.zza(this);
    }

    public void zzbd(long l) throws IOException {
        do {
            if ((l & -128L) == 0L) {
                this.zzqr((int)l);
                return;
            }
            this.zzqr((int)l & 127 | 128);
            l >>>= 7;
        } while (true);
    }

    public void zzbf(long l) throws IOException {
        if (this.zzcrW.remaining() < 8) {
            throw new zza(this.zzcrW.position(), this.zzcrW.limit());
        }
        this.zzcrW.putLong(l);
    }

    public void zzbl(boolean bl) throws IOException {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public void zzc(byte by) throws IOException {
        if (!this.zzcrW.hasRemaining()) {
            throw new zza(this.zzcrW.position(), this.zzcrW.limit());
        }
        this.zzcrW.put(by);
    }

    public void zzc(int n, float f) throws IOException {
        this.zzJ(n, 5);
        this.zzk(f);
    }

    public void zzc(int n, long l) throws IOException {
        this.zzJ(n, 1);
        this.zzaY(l);
    }

    public void zzc(zzbut zzbut2) throws IOException {
        this.zzqt(zzbut2.zzacY());
        zzbut2.zza(this);
    }

    public void zzd(int n, long l) throws IOException {
        this.zzJ(n, 0);
        this.zzaZ(l);
    }

    public void zzd(byte[] arrby, int n, int n2) throws IOException {
        if (this.zzcrW.remaining() >= n2) {
            this.zzcrW.put(arrby, n, n2);
            return;
        }
        throw new zza(this.zzcrW.position(), this.zzcrW.limit());
    }

    public void zzg(int n, boolean bl) throws IOException {
        this.zzJ(n, 0);
        this.zzbl(bl);
    }

    public void zzk(float f) throws IOException {
        this.zzqv(Float.floatToIntBits(f));
    }

    public void zzkb(String string) throws IOException {
        try {
            int n = zzbum.zzqu(string.length());
            if (n == zzbum.zzqu(string.length() * 3)) {
                int n2 = this.zzcrW.position();
                if (this.zzcrW.remaining() < n) {
                    throw new zza(n2 + n, this.zzcrW.limit());
                }
                this.zzcrW.position(n2 + n);
                zzbum.zza((CharSequence)string, this.zzcrW);
                int n3 = this.zzcrW.position();
                this.zzcrW.position(n2);
                this.zzqt(n3 - n2 - n);
                this.zzcrW.position(n3);
                return;
            }
            this.zzqt(zzbum.zzb(string));
            zzbum.zza((CharSequence)string, this.zzcrW);
            return;
        }
        catch (BufferOverflowException bufferOverflowException) {
            zza zza2 = new zza(this.zzcrW.position(), this.zzcrW.limit());
            zza2.initCause(bufferOverflowException);
            throw zza2;
        }
    }

    public void zzn(double d) throws IOException {
        this.zzbf(Double.doubleToLongBits(d));
    }

    public void zzq(int n, String string) throws IOException {
        this.zzJ(n, 2);
        this.zzkb(string);
    }

    public void zzqn(int n) throws IOException {
        if (n >= 0) {
            this.zzqt(n);
            return;
        }
        this.zzbd(n);
    }

    public void zzqo(int n) throws IOException {
        this.zzqt(zzbum.zzqw(n));
    }

    public void zzqr(int n) throws IOException {
        this.zzc((byte)n);
    }

    public void zzqt(int n) throws IOException {
        do {
            if ((n & -128) == 0) {
                this.zzqr(n);
                return;
            }
            this.zzqr(n & 127 | 128);
            n >>>= 7;
        } while (true);
    }

    public void zzqv(int n) throws IOException {
        if (this.zzcrW.remaining() < 4) {
            throw new zza(this.zzcrW.position(), this.zzcrW.limit());
        }
        this.zzcrW.putInt(n);
    }

    public static class zza
    extends IOException {
        zza(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder(108);
            stringBuilder.append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ");
            stringBuilder.append(n);
            stringBuilder.append(" limit ");
            stringBuilder.append(n2);
            stringBuilder.append(").");
            super(stringBuilder.toString());
        }
    }

}
