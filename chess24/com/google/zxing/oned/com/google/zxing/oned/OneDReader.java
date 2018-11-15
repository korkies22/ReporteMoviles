/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public abstract class OneDReader
implements Reader {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Result doDecode(BinaryBitmap var1_1, Map<DecodeHintType, ?> var2_2) throws NotFoundException {
        var10_8 = var1_1.getWidth();
        var8_9 = var1_1.getHeight();
        var14_10 = new BitArray(var10_8);
        var7_11 = 1;
        var5_12 = var2_2 != null && var2_2.containsKey((Object)DecodeHintType.TRY_HARDER) != false ? 1 : 0;
        var6_13 = var5_12 != 0 ? 8 : 5;
        var13_14 = Math.max(1, var8_9 >> var6_13);
        var9_15 = var5_12 != 0 ? var8_9 : 15;
        var11_16 = 0;
        var5_12 = var7_11;
        var7_11 = var8_9;
        var6_13 = var10_8;
        var10_8 = var11_16;
        block12 : do {
            block18 : {
                if (var10_8 >= var9_15) throw NotFoundException.getNotFoundInstance();
                var12_19 = var10_8 + 1;
                var11_16 = var12_19 / 2;
                var10_8 = (var10_8 & 1) == 0 ? var5_12 : 0;
                var10_8 = var10_8 != 0 ? var11_16 : - var11_16;
                var11_16 = var10_8 * var13_14 + (var8_9 >> 1);
                if (var11_16 < 0) throw NotFoundException.getNotFoundInstance();
                if (var11_16 >= var7_11) throw NotFoundException.getNotFoundInstance();
                try {
                    var15_20 = var1_1.getBlackRow(var11_16, (BitArray)var14_10);
                    var10_8 = 0;
                    break block18;
                }
                catch (NotFoundException var15_21) {}
                var10_8 = var7_11;
                var7_11 = var5_12;
                var5_12 = var10_8;
                ** GOTO lbl66
            }
            do {
                block20 : {
                    block19 : {
                        if (var10_8 < 2) {
                            var14_10 = var2_2;
                            if (var10_8 == var5_12) {
                                var15_20.reverse();
                                var14_10 = var2_2;
                                if (var2_2 != null) {
                                    var14_10 = var2_2;
                                    if (var2_2.containsKey((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                                        var14_10 = new EnumMap<DecodeHintType, ?>(DecodeHintType.class);
                                        var14_10.putAll(var2_2);
                                        var14_10.remove((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                                    }
                                }
                            }
                            var2_2 = this.decodeRow(var11_16, var15_20, var14_10);
                            if (var10_8 != var5_12) return var2_2;
                            var16_22 = ResultMetadataType.ORIENTATION;
                            var2_2.putMetadata((ResultMetadataType)var16_22, (Object)180);
                            var16_22 = var2_2.getResultPoints();
                            if (var16_22 == null) return var2_2;
                            var3_17 = var6_13;
                            var4_18 = var16_22[0].getX();
                            var16_22[0] = new ResultPoint(var3_17 - var4_18 - 1.0f, var16_22[0].getY());
                            var5_12 = 1;
                            var16_22[1] = new ResultPoint(var3_17 - var16_22[1].getX() - 1.0f, var16_22[1].getY());
                            return var2_2;
                        }
                        var10_8 = var7_11;
                        var14_10 = var15_20;
                        var7_11 = var5_12;
                        var5_12 = var10_8;
lbl66: // 2 sources:
                        var11_16 = var7_11;
                        var10_8 = var12_19;
                        var7_11 = var5_12;
                        var5_12 = var11_16;
                        continue block12;
                        catch (ReaderException var2_3) {}
                        break block20;
                        catch (ReaderException var2_4) {}
                        break block19;
                        catch (ReaderException var2_5) {
                            break block19;
                        }
                        catch (ReaderException var2_6) {}
                    }
                    var5_12 = 1;
                    break block20;
                    catch (ReaderException var2_7) {}
                }
                ++var10_8;
                var2_2 = var14_10;
            } while (true);
            break;
        } while (true);
    }

    protected static float patternMatchVariance(int[] arrn, int[] arrn2, float f) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = arrn.length;
        int n5 = n2 = (n = 0);
        while (n < n4) {
            n2 += arrn[n];
            n5 += arrn2[n];
            ++n;
        }
        if (n2 < n5) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = n2;
        float f3 = f2 / (float)n5;
        float f4 = 0.0f;
        for (n2 = n3; n2 < n4; ++n2) {
            n5 = arrn[n2];
            float f5 = n5;
            float f6 = (float)arrn2[n2] * f3;
            f6 = f5 > f6 ? f5 - f6 : (f6 -= f5);
            if (f6 > f * f3) {
                return Float.POSITIVE_INFINITY;
            }
            f4 += f6;
        }
        return f4 / f2;
    }

    protected static void recordPattern(BitArray bitArray, int n, int[] arrn) throws NotFoundException {
        int n2 = arrn.length;
        Arrays.fill(arrn, 0, n2, 0);
        int n3 = bitArray.getSize();
        if (n >= n3) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean bl = bitArray.get(n) ^ true;
        int n4 = 0;
        int n5 = n;
        n = n4;
        do {
            n4 = n++;
            if (n5 >= n3) break;
            if (bitArray.get(n5) ^ bl) {
                arrn[n] = arrn[n] + 1;
            } else {
                n4 = n;
                if (n == n2) break;
                arrn[n] = 1;
                bl = !bl;
            }
            ++n5;
        } while (true);
        if (n4 != n2 && (n4 != n2 - 1 || n5 != n3)) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    protected static void recordPatternInReverse(BitArray bitArray, int n, int[] arrn) throws NotFoundException {
        int n2 = arrn.length;
        boolean bl = bitArray.get(n);
        while (n > 0 && n2 >= 0) {
            int n3;
            n = n3 = n - 1;
            if (bitArray.get(n3) == bl) continue;
            --n2;
            if (!bl) {
                bl = true;
                n = n3;
                continue;
            }
            bl = false;
            n = n3;
        }
        if (n2 >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        OneDReader.recordPattern(bitArray, n + 1, arrn);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> object) throws NotFoundException, FormatException {
        try {
            Result result = this.doDecode(binaryBitmap, (Map<DecodeHintType, ?>)object);
            return result;
        }
        catch (NotFoundException notFoundException) {
            int n = 0;
            int n2 = object != null && object.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
            if (n2 != 0 && binaryBitmap.isRotateSupported()) {
                int n3;
                binaryBitmap = binaryBitmap.rotateCounterClockwise();
                object = this.doDecode(binaryBitmap, (Map<DecodeHintType, ?>)object);
                ResultPoint[] arrresultPoint = object.getResultMetadata();
                n2 = n3 = 270;
                if (arrresultPoint != null) {
                    n2 = n3;
                    if (arrresultPoint.containsKey((Object)ResultMetadataType.ORIENTATION)) {
                        n2 = (270 + (Integer)arrresultPoint.get((Object)ResultMetadataType.ORIENTATION)) % 360;
                    }
                }
                object.putMetadata(ResultMetadataType.ORIENTATION, n2);
                arrresultPoint = object.getResultPoints();
                if (arrresultPoint != null) {
                    n3 = binaryBitmap.getHeight();
                    for (n2 = n; n2 < arrresultPoint.length; ++n2) {
                        arrresultPoint[n2] = new ResultPoint((float)n3 - arrresultPoint[n2].getY() - 1.0f, arrresultPoint[n2].getX());
                    }
                }
                return object;
            }
            throw notFoundException;
        }
    }

    public abstract Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException;

    @Override
    public void reset() {
    }
}
