/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.Pair;
import com.google.zxing.oned.rss.RSSUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSS14Reader
extends AbstractRSSReader {
    private static final int[][] FINDER_PATTERNS;
    private static final int[] INSIDE_GSUM;
    private static final int[] INSIDE_ODD_TOTAL_SUBSET;
    private static final int[] INSIDE_ODD_WIDEST;
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET;
    private static final int[] OUTSIDE_GSUM;
    private static final int[] OUTSIDE_ODD_WIDEST;
    private final List<Pair> possibleLeftPairs = new ArrayList<Pair>();
    private final List<Pair> possibleRightPairs = new ArrayList<Pair>();

    static {
        OUTSIDE_EVEN_TOTAL_SUBSET = new int[]{1, 10, 34, 70, 126};
        INSIDE_ODD_TOTAL_SUBSET = new int[]{4, 20, 48, 81};
        OUTSIDE_GSUM = new int[]{0, 161, 961, 2015, 2715};
        INSIDE_GSUM = new int[]{0, 336, 1036, 1516};
        OUTSIDE_ODD_WIDEST = new int[]{8, 6, 4, 3, 1};
        INSIDE_ODD_WIDEST = new int[]{2, 4, 6, 8};
        int[] arrn = new int[]{2, 5, 6, 1};
        FINDER_PATTERNS = new int[][]{{3, 8, 2, 1}, {3, 5, 5, 1}, {3, 3, 7, 1}, {3, 1, 9, 1}, {2, 7, 4, 1}, arrn, {2, 3, 8, 1}, {1, 5, 7, 1}, {1, 3, 9, 1}};
    }

    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        boolean bl;
        block3 : {
            Pair pair2;
            if (pair == null) {
                return;
            }
            boolean bl2 = false;
            Iterator<Pair> iterator = collection.iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break block3;
            } while ((pair2 = iterator.next()).getValue() != pair.getValue());
            pair2.incrementCount();
            bl = true;
        }
        if (!bl) {
            collection.add(pair);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void adjustOddEvenCounts(boolean var1_1, int var2_2) throws NotFoundException {
        block37 : {
            block36 : {
                var9_3 = MathUtils.sum(this.getOddCounts());
                var10_4 = MathUtils.sum(this.getEvenCounts());
                var8_5 = 0;
                if (var1_1 == 0) break block36;
                if (var9_3 > 12) {
                    var4_6 = false;
                    var3_7 = true;
                } else if (var9_3 < 4) {
                    var3_7 = false;
                    var4_6 = true;
                } else {
                    var3_7 = var4_6 = false;
                }
                if (var10_4 > 12) ** GOTO lbl-1000
                if (var10_4 >= 4) ** GOTO lbl-1000
                var5_8 = true;
                var6_9 = false;
                break block37;
            }
            if (var9_3 > 11) {
                var4_6 = false;
                var3_7 = true;
            } else if (var9_3 < 5) {
                var3_7 = false;
                var4_6 = true;
            } else {
                var3_7 = var4_6 = false;
            }
            if (var10_4 > 10) lbl-1000: // 2 sources:
            {
                var5_8 = false;
                var6_9 = true;
            } else if (var10_4 < 4) {
                var6_9 = false;
                var5_8 = true;
            } else lbl-1000: // 2 sources:
            {
                var5_8 = false;
                var6_9 = false;
            }
        }
        var11_10 = var9_3 + var10_4 - var2_2;
        var7_11 = (var9_3 & 1) == var1_1;
        var2_2 = var8_5;
        if ((var10_4 & 1) == 1) {
            var2_2 = 1;
        }
        if (var11_10 == 1) {
            if (var7_11) {
                if (var2_2 != 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                var3_7 = true;
            } else {
                if (var2_2 == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                var6_9 = true;
            }
        } else if (var11_10 == -1) {
            if (var7_11) {
                if (var2_2 != 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                var4_6 = true;
            } else {
                if (var2_2 == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                var5_8 = true;
            }
        } else {
            if (var11_10 != 0) throw NotFoundException.getNotFoundInstance();
            if (var7_11) {
                if (var2_2 == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (var9_3 < var10_4) {
                    var6_9 = var4_6 = true;
                } else {
                    var3_7 = var5_8 = true;
                }
            } else if (var2_2 != 0) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (var4_6) {
            if (var3_7) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSS14Reader.increment(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (var3_7) {
            RSS14Reader.decrement(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (var5_8) {
            if (var6_9) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSS14Reader.increment(this.getEvenCounts(), this.getOddRoundingErrors());
        }
        if (var6_9 == false) return;
        RSS14Reader.decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int n;
        int n2 = pair.getChecksumPortion();
        int n3 = pair2.getChecksumPortion();
        int n4 = n = 9 * pair.getFinderPattern().getValue() + pair2.getFinderPattern().getValue();
        if (n > 72) {
            n4 = n - 1;
        }
        n = n4;
        if (n4 > 8) {
            n = n4 - 1;
        }
        if ((n2 + 16 * n3) % 79 == n) {
            return true;
        }
        return false;
    }

    private static Result constructResult(Pair object, Pair object2) {
        int n;
        Object object3 = String.valueOf(4537077L * (long)object.getValue() + (long)object2.getValue());
        Object object4 = new StringBuilder(14);
        for (n = 13 - object3.length(); n > 0; --n) {
            object4.append('0');
        }
        object4.append((String)object3);
        int n2 = n = 0;
        while (n < 13) {
            int n3;
            int n4 = n3 = object4.charAt(n) - 48;
            if ((n & 1) == 0) {
                n4 = n3 * 3;
            }
            n2 += n4;
            ++n;
        }
        n = n2 = 10 - n2 % 10;
        if (n2 == 10) {
            n = 0;
        }
        object4.append(n);
        Object object5 = object.getFinderPattern().getResultPoints();
        object3 = object2.getFinderPattern().getResultPoints();
        object = String.valueOf(object4.toString());
        object2 = object5[0];
        object4 = object5[1];
        object5 = object3[0];
        object3 = object3[1];
        BarcodeFormat barcodeFormat = BarcodeFormat.RSS_14;
        return new Result((String)object, null, new ResultPoint[]{object2, object4, object5, object3}, barcodeFormat);
    }

    private DataCharacter decodeDataCharacter(BitArray arrn, FinderPattern arrn2, boolean bl) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int n4;
        int[] arrn3 = this.getDataCharacterCounters();
        arrn3[0] = 0;
        arrn3[1] = 0;
        arrn3[2] = 0;
        arrn3[3] = 0;
        arrn3[4] = 0;
        arrn3[5] = 0;
        arrn3[6] = 0;
        arrn3[7] = 0;
        if (bl) {
            RSS14Reader.recordPatternInReverse((BitArray)arrn, arrn2.getStartEnd()[0], arrn3);
        } else {
            RSS14Reader.recordPattern((BitArray)arrn, arrn2.getStartEnd()[1] + 1, arrn3);
            n4 = arrn3.length - 1;
            for (n2 = 0; n2 < n4; ++n2, --n4) {
                n = arrn3[n2];
                arrn3[n2] = arrn3[n4];
                arrn3[n4] = n;
            }
        }
        n2 = bl ? 16 : 15;
        float f = (float)MathUtils.sum(arrn3) / (float)n2;
        arrn = this.getOddCounts();
        arrn2 = this.getEvenCounts();
        float[] arrf = this.getOddRoundingErrors();
        float[] arrf2 = this.getEvenRoundingErrors();
        for (n = 0; n < arrn3.length; ++n) {
            float f2 = (float)arrn3[n] / f;
            n3 = (int)(0.5f + f2);
            if (n3 <= 0) {
                n4 = 1;
            } else {
                n4 = n3;
                if (n3 > 8) {
                    n4 = 8;
                }
            }
            n3 = n / 2;
            if ((n & 1) == 0) {
                arrn[n3] = n4;
                arrf[n3] = f2 - (float)n4;
                continue;
            }
            arrn2[n3] = n4;
            arrf2[n3] = f2 - (float)n4;
        }
        this.adjustOddEvenCounts(bl, n2);
        n2 = 0;
        n4 = 0;
        for (n = arrn.length - 1; n >= 0; --n) {
            n2 = n2 * 9 + arrn[n];
            n4 += arrn[n];
        }
        int n5 = 0;
        n = 0;
        for (n3 = arrn2.length - 1; n3 >= 0; --n3) {
            n5 = n5 * 9 + arrn2[n3];
            n += arrn2[n3];
        }
        n2 += 3 * n5;
        if (bl) {
            if ((n4 & 1) == 0 && n4 <= 12 && n4 >= 4) {
                n4 = (12 - n4) / 2;
                n3 = OUTSIDE_ODD_WIDEST[n4];
                n = RSSUtils.getRSSvalue(arrn, n3, false);
                n3 = RSSUtils.getRSSvalue(arrn2, 9 - n3, true);
                return new DataCharacter(n * OUTSIDE_EVEN_TOTAL_SUBSET[n4] + n3 + OUTSIDE_GSUM[n4], n2);
            }
            throw NotFoundException.getNotFoundInstance();
        }
        if ((n & 1) == 0 && n <= 10 && n >= 4) {
            n4 = (10 - n) / 2;
            n = INSIDE_ODD_WIDEST[n4];
            n3 = RSSUtils.getRSSvalue(arrn, n, true);
            return new DataCharacter(RSSUtils.getRSSvalue(arrn2, 9 - n, false) * INSIDE_ODD_TOTAL_SUBSET[n4] + n3 + INSIDE_GSUM[n4], n2);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Pair decodePair(BitArray object, boolean bl, int n, Map<DecodeHintType, ?> object2) {
        try {
            int[] arrn = this.findFinderPattern((BitArray)object, 0, bl);
            FinderPattern finderPattern = this.parseFoundFinderPattern((BitArray)object, n, bl, arrn);
            object2 = object2 == null ? null : (ResultPointCallback)object2.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (object2 != null) {
                float f;
                float f2 = f = (float)(arrn[0] + arrn[1]) / 2.0f;
                if (bl) {
                    f2 = (float)(object.getSize() - 1) - f;
                }
                object2.foundPossibleResultPoint(new ResultPoint(f2, n));
            }
            object2 = this.decodeDataCharacter((BitArray)object, finderPattern, true);
            object = this.decodeDataCharacter((BitArray)object, finderPattern, false);
            return new Pair(1597 * object2.getValue() + object.getValue(), object2.getChecksumPortion() + 4 * object.getChecksumPortion(), finderPattern);
        }
        catch (NotFoundException notFoundException) {
            return null;
        }
    }

    private int[] findFinderPattern(BitArray bitArray, int n, boolean bl) throws NotFoundException {
        int[] arrn = this.getDecodeFinderCounters();
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        arrn[3] = 0;
        int n2 = bitArray.getSize();
        boolean bl2 = false;
        while (n < n2) {
            boolean bl3;
            bl2 = bl3 = bitArray.get(n) ^ true;
            if (bl == bl3) break;
            ++n;
            bl2 = bl3;
        }
        int n3 = n;
        int n4 = 0;
        int n5 = n;
        n = n3;
        bl = bl2;
        while (n5 < n2) {
            if (bitArray.get(n5) ^ bl) {
                arrn[n4] = arrn[n4] + 1;
            } else {
                if (n4 == 3) {
                    if (RSS14Reader.isFinderPattern(arrn)) {
                        return new int[]{n, n5};
                    }
                    n += arrn[0] + arrn[1];
                    arrn[0] = arrn[2];
                    arrn[1] = arrn[3];
                    arrn[2] = 0;
                    arrn[3] = 0;
                    --n4;
                } else {
                    ++n4;
                }
                arrn[n4] = 1;
                bl = !bl;
            }
            ++n5;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int n, boolean bl, int[] arrn) throws NotFoundException {
        int n2;
        boolean bl2 = bitArray.get(arrn[0]);
        for (n2 = arrn[0] - 1; n2 >= 0 && bitArray.get(n2) ^ bl2; --n2) {
        }
        int n3 = n2 + 1;
        n2 = arrn[0];
        int[] arrn2 = this.getDecodeFinderCounters();
        System.arraycopy(arrn2, 0, arrn2, 1, arrn2.length - 1);
        arrn2[0] = n2 - n3;
        int n4 = RSS14Reader.parseFinderValue(arrn2, FINDER_PATTERNS);
        int n5 = arrn[1];
        if (bl) {
            n2 = bitArray.getSize();
            n5 = bitArray.getSize() - 1 - n5;
            n2 = n2 - 1 - n3;
        } else {
            n2 = n3;
        }
        return new FinderPattern(n4, new int[]{n3, arrn[1]}, n2, n5, n);
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object22) throws NotFoundException {
        Pair pair = this.decodePair((BitArray)((Object)object), false, n, (Map<DecodeHintType, ?>)object22);
        RSS14Reader.addOrTally(this.possibleLeftPairs, pair);
        object.reverse();
        Pair pair2 = this.decodePair((BitArray)((Object)object), true, n, (Map<DecodeHintType, ?>)object22);
        RSS14Reader.addOrTally(this.possibleRightPairs, pair2);
        object.reverse();
        for (Pair pair3 : this.possibleLeftPairs) {
            if (pair3.getCount() <= 1) continue;
            for (Pair pair4 : this.possibleRightPairs) {
                if (pair4.getCount() <= 1 || !RSS14Reader.checkChecksum(pair3, pair4)) continue;
                return RSS14Reader.constructResult(pair3, pair4);
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}
