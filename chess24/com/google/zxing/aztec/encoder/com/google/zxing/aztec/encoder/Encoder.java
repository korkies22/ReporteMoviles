/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    private static int[] bitsToWords(BitArray bitArray, int n, int n2) {
        int[] arrn = new int[n2];
        int n3 = bitArray.getSize() / n;
        for (n2 = 0; n2 < n3; ++n2) {
            int n4;
            int n5 = n4 = 0;
            while (n4 < n) {
                int n6 = bitArray.get(n2 * n + n4) ? 1 << n - n4 - 1 : 0;
                n5 |= n6;
                ++n4;
            }
            arrn[n2] = n5;
        }
        return arrn;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int n, int n2) {
        int n3;
        int n4;
        for (n3 = 0; n3 < n2; n3 += 2) {
            int n5;
            int n6;
            for (n4 = n5 = n - n3; n4 <= (n6 = n + n3); ++n4) {
                bitMatrix.set(n4, n5);
                bitMatrix.set(n4, n6);
                bitMatrix.set(n5, n4);
                bitMatrix.set(n6, n4);
            }
        }
        n3 = n - n2;
        bitMatrix.set(n3, n3);
        n4 = n3 + 1;
        bitMatrix.set(n4, n3);
        bitMatrix.set(n3, n4);
        bitMatrix.set(n += n2, n3);
        bitMatrix.set(n, n4);
        bitMatrix.set(n, n - 1);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean bl, int n, BitArray bitArray) {
        int n2 = n / 2;
        int n3 = 0;
        if (bl) {
            for (n = n3; n < 7; ++n) {
                n3 = n2 - 3 + n;
                if (bitArray.get(n)) {
                    bitMatrix.set(n3, n2 - 5);
                }
                if (bitArray.get(n + 7)) {
                    bitMatrix.set(n2 + 5, n3);
                }
                if (bitArray.get(20 - n)) {
                    bitMatrix.set(n3, n2 + 5);
                }
                if (!bitArray.get(27 - n)) continue;
                bitMatrix.set(n2 - 5, n3);
            }
            return;
        }
        for (n = 0; n < 10; ++n) {
            n3 = n2 - 5 + n + n / 5;
            if (bitArray.get(n)) {
                bitMatrix.set(n3, n2 - 7);
            }
            if (bitArray.get(n + 10)) {
                bitMatrix.set(n2 + 7, n3);
            }
            if (bitArray.get(29 - n)) {
                bitMatrix.set(n3, n2 + 7);
            }
            if (!bitArray.get(39 - n)) continue;
            bitMatrix.set(n2 - 7, n3);
        }
    }

    public static AztecCode encode(byte[] arrby) {
        return Encoder.encode(arrby, 33, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static AztecCode encode(byte[] var0, int var1_1, int var2_2) {
        block24 : {
            block25 : {
                var14_3 = new HighLevelEncoder((byte[])var0).encode();
                var1_1 = var14_3.getSize() * var1_1 / 100;
                var6_4 = 11;
                var8_5 = var1_1 + 11;
                var9_6 = var14_3.getSize();
                var1_1 = 32;
                if (var2_2 == 0) break block25;
                var12_7 = var2_2 < 0;
                var4_8 = Math.abs(var2_2);
                if (var12_7) {
                    var1_1 = 4;
                }
                if (var4_8 > var1_1) {
                    throw new IllegalArgumentException(String.format("Illegal value %s for layers", new Object[]{var2_2}));
                }
                var1_1 = Encoder.totalBitsInLayer(var4_8, var12_7);
                var2_2 = Encoder.WORD_SIZE[var4_8];
                var0 = Encoder.stuffBits(var14_3, var2_2);
                if (var0.getSize() + var8_5 > var1_1 - var1_1 % var2_2) {
                    throw new IllegalArgumentException("Data to large for user specified layer");
                }
                if (var12_7 && var0.getSize() > var2_2 << 6) {
                    throw new IllegalArgumentException("Data to large for user specified layer");
                }
                var3_9 = var1_1;
                ** GOTO lbl50
            }
            var0 = null;
            var3_9 = var2_2 = 0;
            do {
                block26 : {
                    block27 : {
                        if (var2_2 > 32) {
                            throw new IllegalArgumentException("Data too large for an Aztec code");
                        }
                        var12_7 = var2_2 <= 3;
                        var4_8 = var12_7 != false ? var2_2 + 1 : var2_2;
                        var7_11 = Encoder.totalBitsInLayer(var4_8, var12_7);
                        var5_10 = var3_9;
                        var13_12 = var0;
                        if (var9_6 + var8_5 > var7_11) break block26;
                        var1_1 = var3_9;
                        if (var3_9 != Encoder.WORD_SIZE[var4_8]) {
                            var1_1 = Encoder.WORD_SIZE[var4_8];
                            var0 = Encoder.stuffBits(var14_3, var1_1);
                        }
                        if (!var12_7) break block27;
                        var5_10 = var1_1;
                        var13_12 = var0;
                        if (var0.getSize() > var1_1 << 6) break block26;
                    }
                    if (var0.getSize() + var8_5 > var7_11 - var7_11 % var1_1) {
                        var5_10 = var1_1;
                        var13_12 = var0;
                    } else {
                        var3_9 = var7_11;
                        var2_2 = var1_1;
lbl50: // 2 sources:
                        var13_12 = Encoder.generateCheckWords((BitArray)var0, var3_9, var2_2);
                        var8_5 = var0.getSize() / var2_2;
                        var14_3 = Encoder.generateModeMessage(var12_7, var4_8, var8_5);
                        var1_1 = var12_7 != false ? var6_4 : 14;
                        var7_11 = var1_1 + (var4_8 << 2);
                        var15_13 = new int[var7_11];
                        if (var12_7) {
                            var1_1 = 0;
                            while (var1_1 < var15_13.length) {
                                var15_13[var1_1] = var1_1++;
                            }
                            var1_1 = var7_11;
                            break;
                        }
                        var5_10 = var7_11 / 2;
                        var3_9 = var7_11 + 1 + (var5_10 - 1) / 15 * 2;
                        var6_4 = var3_9 / 2;
                        var2_2 = 0;
                        do {
                            var1_1 = var3_9;
                            if (var2_2 >= var5_10) break;
                            var1_1 = var2_2 / 15 + var2_2;
                            var15_13[var5_10 - var2_2 - 1] = var6_4 - var1_1 - 1;
                            var15_13[var5_10 + var2_2] = var1_1 + var6_4 + 1;
                            ++var2_2;
                        } while (true);
                        break;
                    }
                }
                ++var2_2;
                var3_9 = var5_10;
                var0 = var13_12;
            } while (true);
            var0 = new BitMatrix(var1_1);
            var3_9 = var2_2 = 0;
            do {
                if (var2_2 >= var4_8) {
                    Encoder.drawModeMessage((BitMatrix)var0, var12_7, var1_1, var14_3);
                    if (var12_7) {
                        Encoder.drawBullsEye((BitMatrix)var0, var1_1 / 2, 5);
                        break block24;
                    }
                    var6_4 = var1_1 / 2;
                    Encoder.drawBullsEye((BitMatrix)var0, var6_4, 7);
                    var2_2 = 0;
                    break;
                }
                var5_10 = var12_7 != false ? 9 : 12;
                var9_6 = (var4_8 - var2_2 << 2) + var5_10;
                var5_10 = 0;
                do {
                    if (var5_10 >= var9_6) break;
                    var10_14 = var5_10 << 1;
                    for (var6_4 = 0; var6_4 < 2; ++var6_4) {
                        if (var13_12.get(var3_9 + var10_14 + var6_4)) {
                            var11_15 = var2_2 << 1;
                            var0.set(var15_13[var11_15 + var6_4], var15_13[var11_15 + var5_10]);
                        }
                        if (var13_12.get((var9_6 << 1) + var3_9 + var10_14 + var6_4)) {
                            var11_15 = var2_2 << 1;
                            var0.set(var15_13[var11_15 + var5_10], var15_13[var7_11 - 1 - var11_15 - var6_4]);
                        }
                        if (var13_12.get((var9_6 << 2) + var3_9 + var10_14 + var6_4)) {
                            var11_15 = var7_11 - 1 - (var2_2 << 1);
                            var0.set(var15_13[var11_15 - var6_4], var15_13[var11_15 - var5_10]);
                        }
                        if (!var13_12.get(var9_6 * 6 + var3_9 + var10_14 + var6_4)) continue;
                        var11_15 = var2_2 << 1;
                        var0.set(var15_13[var7_11 - 1 - var11_15 - var5_10], var15_13[var11_15 + var6_4]);
                    }
                    ++var5_10;
                } while (true);
                var3_9 += var9_6 << 3;
                ++var2_2;
            } while (true);
            for (var3_9 = 0; var3_9 < var7_11 / 2 - 1; var3_9 += 15, var2_2 += 16) {
                for (var5_10 = var6_4 & 1; var5_10 < var1_1; var5_10 += 2) {
                    var9_6 = var6_4 - var2_2;
                    var0.set(var9_6, var5_10);
                    var10_14 = var6_4 + var2_2;
                    var0.set(var10_14, var5_10);
                    var0.set(var5_10, var9_6);
                    var0.set(var5_10, var10_14);
                }
            }
        }
        var13_12 = new AztecCode();
        var13_12.setCompact(var12_7);
        var13_12.setSize(var1_1);
        var13_12.setLayers(var4_8);
        var13_12.setCodeWords(var8_5);
        var13_12.setMatrix((BitMatrix)var0);
        return var13_12;
    }

    private static BitArray generateCheckWords(BitArray arrn, int n, int n2) {
        int n3 = arrn.getSize() / n2;
        Object object = new ReedSolomonEncoder(Encoder.getGF(n2));
        int n4 = n / n2;
        arrn = Encoder.bitsToWords((BitArray)arrn, n2, n4);
        object.encode(arrn, n4 - n3);
        object = new BitArray();
        n3 = 0;
        object.appendBits(0, n % n2);
        n4 = arrn.length;
        for (n = n3; n < n4; ++n) {
            object.appendBits(arrn[n], n2);
        }
        return object;
    }

    static BitArray generateModeMessage(boolean bl, int n, int n2) {
        BitArray bitArray = new BitArray();
        if (bl) {
            bitArray.appendBits(n - 1, 2);
            bitArray.appendBits(n2 - 1, 6);
            return Encoder.generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(n - 1, 5);
        bitArray.appendBits(n2 - 1, 11);
        return Encoder.generateCheckWords(bitArray, 40, 4);
    }

    private static GenericGF getGF(int n) {
        if (n != 4) {
            if (n != 6) {
                if (n != 8) {
                    if (n != 10) {
                        if (n != 12) {
                            StringBuilder stringBuilder = new StringBuilder("Unsupported word size ");
                            stringBuilder.append(n);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        return GenericGF.AZTEC_DATA_12;
                    }
                    return GenericGF.AZTEC_DATA_10;
                }
                return GenericGF.AZTEC_DATA_8;
            }
            return GenericGF.AZTEC_DATA_6;
        }
        return GenericGF.AZTEC_PARAM;
    }

    static BitArray stuffBits(BitArray bitArray, int n) {
        BitArray bitArray2 = new BitArray();
        int n2 = bitArray.getSize();
        int n3 = (1 << n) - 2;
        for (int i = 0; i < n2; i += n) {
            int n4;
            int n5 = n4 = 0;
            while (n4 < n) {
                int n6;
                block8 : {
                    block7 : {
                        int n7 = i + n4;
                        if (n7 >= n2) break block7;
                        n6 = n5;
                        if (!bitArray.get(n7)) break block8;
                    }
                    n6 = n5 | 1 << n - 1 - n4;
                }
                ++n4;
                n5 = n6;
            }
            n4 = n5 & n3;
            if (n4 == n3) {
                bitArray2.appendBits(n4, n);
                --i;
                continue;
            }
            if (n4 == 0) {
                bitArray2.appendBits(n5 | 1, n);
                --i;
                continue;
            }
            bitArray2.appendBits(n5, n);
        }
        return bitArray2;
    }

    private static int totalBitsInLayer(int n, boolean bl) {
        int n2 = bl ? 88 : 112;
        return (n2 + (n << 4)) * n;
    }
}
