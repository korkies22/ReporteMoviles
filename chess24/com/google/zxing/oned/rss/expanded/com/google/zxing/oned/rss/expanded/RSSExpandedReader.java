/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.BitArrayBuilder;
import com.google.zxing.oned.rss.expanded.ExpandedPair;
import com.google.zxing.oned.rss.expanded.ExpandedRow;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSSExpandedReader
extends AbstractRSSReader {
    private static final int[] EVEN_TOTAL_SUBSET;
    private static final int[][] FINDER_PATTERNS;
    private static final int[][] FINDER_PATTERN_SEQUENCES;
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int[] GSUM;
    private static final int MAX_PAIRS = 11;
    private static final int[] SYMBOL_WIDEST;
    private static final int[][] WEIGHTS;
    private final List<ExpandedPair> pairs = new ArrayList<ExpandedPair>(11);
    private final List<ExpandedRow> rows = new ArrayList<ExpandedRow>();
    private final int[] startEnd = new int[2];
    private boolean startFromEven;

    static {
        SYMBOL_WIDEST = new int[]{7, 5, 4, 3, 1};
        EVEN_TOTAL_SUBSET = new int[]{4, 20, 52, 104, 204};
        GSUM = new int[]{0, 348, 1388, 2948, 3988};
        FINDER_PATTERNS = new int[][]{{1, 8, 4, 1}, {3, 6, 4, 1}, {3, 4, 6, 1}, {3, 2, 8, 1}, {2, 6, 5, 1}, {2, 2, 9, 1}};
        int[] arrn = new int[]{20, 60, 180, 118, 143, 7, 21, 63};
        int[] arrn2 = new int[]{189, 145, 13, 39, 117, 140, 209, 205};
        int[] arrn3 = new int[]{193, 157, 49, 147, 19, 57, 171, 91};
        int[] arrn4 = new int[]{62, 186, 136, 197, 169, 85, 44, 132};
        int[] arrn5 = new int[]{113, 128, 173, 97, 80, 29, 87, 50};
        int[] arrn6 = new int[]{76, 17, 51, 153, 37, 111, 122, 155};
        int[] arrn7 = new int[]{43, 129, 176, 106, 107, 110, 119, 146};
        int[] arrn8 = new int[]{109, 116, 137, 200, 178, 112, 125, 164};
        int[] arrn9 = new int[]{120, 149, 25, 75, 14, 42, 126, 167};
        int[] arrn10 = new int[]{79, 26, 78, 23, 69, 207, 199, 175};
        int[] arrn11 = new int[]{103, 98, 83, 38, 114, 131, 182, 124};
        int[] arrn12 = new int[]{55, 165, 73, 8, 24, 72, 5, 15};
        WEIGHTS = new int[][]{{1, 3, 9, 27, 81, 32, 96, 77}, arrn, arrn2, arrn3, arrn4, {185, 133, 188, 142, 4, 12, 36, 108}, arrn5, {150, 28, 84, 41, 123, 158, 52, 156}, {46, 138, 203, 187, 139, 206, 196, 166}, arrn6, arrn7, {16, 48, 144, 10, 30, 90, 59, 177}, arrn8, {70, 210, 208, 202, 184, 130, 179, 115}, {134, 191, 151, 31, 93, 68, 204, 190}, {148, 22, 66, 198, 172, 94, 71, 2}, {6, 18, 54, 162, 64, 192, 154, 40}, arrn9, arrn10, arrn11, {161, 61, 183, 127, 170, 88, 53, 159}, arrn12, {45, 135, 194, 160, 58, 174, 100, 89}};
        arrn = new int[]{0, 0};
        arrn2 = new int[]{0, 1, 1};
        arrn3 = new int[]{0, 2, 1, 3};
        arrn4 = new int[]{0, 4, 1, 3, 2};
        arrn5 = new int[]{0, 4, 1, 3, 3, 5};
        arrn6 = new int[]{0, 4, 1, 3, 4, 5, 5};
        arrn7 = new int[]{0, 0, 1, 1, 2, 2, 3, 3};
        arrn8 = new int[]{0, 0, 1, 1, 2, 2, 3, 4, 4};
        arrn9 = new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5};
        FINDER_PATTERN_SEQUENCES = new int[][]{arrn, arrn2, arrn3, arrn4, arrn5, arrn6, arrn7, arrn8, {0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, arrn9};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void adjustOddEvenCounts(int n) throws NotFoundException {
        boolean bl;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        int n2 = MathUtils.sum(this.getOddCounts());
        int n3 = MathUtils.sum(this.getEvenCounts());
        int n4 = 0;
        if (n2 > 13) {
            bl3 = false;
            bl2 = true;
        } else if (n2 < 4) {
            bl2 = false;
            bl3 = true;
        } else {
            bl3 = bl2 = false;
        }
        if (n3 > 13) {
            bl4 = false;
            bl = true;
        } else if (n3 < 4) {
            bl = false;
            bl4 = true;
        } else {
            bl = bl4 = false;
        }
        int n5 = n2 + n3 - n;
        boolean bl5 = (n2 & 1) == 1;
        n = n4;
        if ((n3 & 1) == 0) {
            n = 1;
        }
        if (n5 == 1) {
            if (bl5) {
                if (n != 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl2 = true;
            } else {
                if (n == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl = true;
            }
        } else if (n5 == -1) {
            if (bl5) {
                if (n != 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl3 = true;
            } else {
                if (n == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl4 = true;
            }
        } else {
            if (n5 != 0) throw NotFoundException.getNotFoundInstance();
            if (bl5) {
                if (n == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (n2 < n3) {
                    bl3 = bl = true;
                } else {
                    bl2 = bl4 = true;
                }
            } else if (n != 0) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (bl3) {
            if (bl2) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSSExpandedReader.increment(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (bl2) {
            RSSExpandedReader.decrement(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (bl4) {
            if (bl) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSSExpandedReader.increment(this.getEvenCounts(), this.getOddRoundingErrors());
        }
        if (!bl) return;
        RSSExpandedReader.decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
    }

    private boolean checkChecksum() {
        Object object = this.pairs.get(0);
        DataCharacter dataCharacter = object.getLeftChar();
        if ((object = object.getRightChar()) == null) {
            return false;
        }
        int n = object.getChecksumPortion();
        int n2 = 2;
        for (int i = 1; i < this.pairs.size(); ++i) {
            object = this.pairs.get(i);
            int n3 = n + object.getLeftChar().getChecksumPortion();
            int n4 = n2 + 1;
            object = object.getRightChar();
            n = n3;
            n2 = n4;
            if (object == null) continue;
            n = n3 + object.getChecksumPortion();
            n2 = n4 + 1;
        }
        if (211 * (n2 - 4) + n % 211 == dataCharacter.getValue()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<ExpandedPair> checkRows(List<ExpandedRow> list, int n) throws NotFoundException {
        do {
            if (n >= this.rows.size()) {
                throw NotFoundException.getNotFoundInstance();
            }
            ExpandedRow expandedRow = this.rows.get(n);
            this.pairs.clear();
            for (ExpandedRow expandedRow2 : list) {
                this.pairs.addAll(expandedRow2.getPairs());
            }
            this.pairs.addAll(expandedRow.getPairs());
            if (RSSExpandedReader.isValidSequence(this.pairs)) {
                if (this.checkChecksum()) {
                    return this.pairs;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(list);
                arrayList.add(expandedRow);
                try {
                    return this.checkRows(arrayList, n + 1);
                }
                catch (NotFoundException notFoundException) {}
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<ExpandedPair> checkRows(boolean bl) {
        List<ExpandedPair> list;
        block5 : {
            if (this.rows.size() > 25) {
                this.rows.clear();
                return null;
            }
            this.pairs.clear();
            if (bl) {
                Collections.reverse(this.rows);
            }
            try {
                list = this.checkRows(new ArrayList<ExpandedRow>(), 0);
                break block5;
            }
            catch (NotFoundException notFoundException) {}
            list = null;
        }
        if (bl) {
            Collections.reverse(this.rows);
        }
        return list;
    }

    static Result constructResult(List<ExpandedPair> object) throws NotFoundException, FormatException {
        String string = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(object)).parseInformation();
        Object object2 = object.get(0).getFinderPattern().getResultPoints();
        Object object3 = object.get(object.size() - 1).getFinderPattern().getResultPoints();
        object = object2[0];
        object2 = object2[1];
        ResultPoint resultPoint = object3[0];
        object3 = object3[1];
        BarcodeFormat barcodeFormat = BarcodeFormat.RSS_EXPANDED;
        return new Result(string, null, new ResultPoint[]{object, object2, resultPoint, object3}, barcodeFormat);
    }

    private void findNextPair(BitArray bitArray, List<ExpandedPair> list, int n) throws NotFoundException {
        int n2;
        int[] arrn = this.getDecodeFinderCounters();
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        arrn[3] = 0;
        int n3 = bitArray.getSize();
        if (n < 0) {
            n = list.isEmpty() ? 0 : list.get(list.size() - 1).getFinderPattern().getStartEnd()[1];
        }
        int n4 = list.size() % 2 != 0 ? 1 : 0;
        int n5 = n4;
        if (this.startFromEven) {
            n5 = n4 ^ true;
        }
        n4 = 0;
        while (n < n3) {
            n4 = n2 = bitArray.get(n) ^ true;
            if (n2 == 0) break;
            ++n;
            n4 = n2;
        }
        n2 = n;
        int n6 = 0;
        int n7 = n;
        n = n2;
        n2 = n4;
        while (n7 < n3) {
            if ((bitArray.get(n7) ^ n2) != 0) {
                arrn[n6] = arrn[n6] + 1;
                n4 = n;
            } else {
                if (n6 == 3) {
                    if (n5 != 0) {
                        RSSExpandedReader.reverseCounters(arrn);
                    }
                    if (RSSExpandedReader.isFinderPattern(arrn)) {
                        this.startEnd[0] = n;
                        this.startEnd[1] = n7;
                        return;
                    }
                    if (n5 != 0) {
                        RSSExpandedReader.reverseCounters(arrn);
                    }
                    n4 = n + (arrn[0] + arrn[1]);
                    arrn[0] = arrn[2];
                    arrn[1] = arrn[3];
                    arrn[2] = 0;
                    arrn[3] = 0;
                    n = n6 - 1;
                } else {
                    n4 = n;
                    n = ++n6;
                }
                arrn[n] = 1;
                if (n2 == 0) {
                    n2 = 1;
                    n6 = n;
                } else {
                    n2 = 0;
                    n6 = n;
                }
            }
            ++n7;
            n = n4;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int getNextSecondBar(BitArray bitArray, int n) {
        if (bitArray.get(n)) {
            return bitArray.getNextSet(bitArray.getNextUnset(n));
        }
        return bitArray.getNextUnset(bitArray.getNextSet(n));
    }

    private static boolean isNotA1left(FinderPattern finderPattern, boolean bl, boolean bl2) {
        if (finderPattern.getValue() == 0 && bl && bl2) {
            return false;
        }
        return true;
    }

    private static boolean isPartialRow(Iterable<ExpandedPair> iterable, Iterable<ExpandedRow> object) {
        block3 : {
            boolean bl;
            object = object.iterator();
            block0 : do {
                boolean bl2 = object.hasNext();
                boolean bl3 = false;
                if (!bl2) break block3;
                ExpandedRow expandedRow = (ExpandedRow)object.next();
                for (ExpandedPair expandedPair : iterable) {
                    block4 : {
                        Iterator<ExpandedPair> iterator = expandedRow.getPairs().iterator();
                        while (iterator.hasNext()) {
                            if (!expandedPair.equals(iterator.next())) continue;
                            bl = true;
                            break block4;
                        }
                        bl = false;
                    }
                    if (bl) continue;
                    bl = bl3;
                    continue block0;
                }
                bl = true;
            } while (!bl);
            return true;
        }
        return false;
    }

    private static boolean isValidSequence(List<ExpandedPair> list) {
        for (int[] arrn : FINDER_PATTERN_SEQUENCES) {
            int n;
            block2 : {
                if (list.size() > arrn.length) continue;
                for (n = 0; n < list.size(); ++n) {
                    if (list.get(n).getFinderPattern().getValue() == arrn[n]) continue;
                    n = 0;
                    break block2;
                }
                n = 1;
            }
            if (n == 0) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private FinderPattern parseFoundFinderPattern(BitArray arrn, int n, boolean bl) {
        int n2;
        int n3;
        int n4;
        if (bl) {
            for (n2 = this.startEnd[0] - 1; n2 >= 0 && !arrn.get(n2); --n2) {
            }
            n4 = n2 + 1;
            n3 = this.startEnd[0] - n4;
            n2 = this.startEnd[1];
        } else {
            n4 = this.startEnd[0];
            n2 = arrn.getNextUnset(this.startEnd[1] + 1);
            n3 = n2 - this.startEnd[1];
        }
        arrn = this.getDecodeFinderCounters();
        System.arraycopy(arrn, 0, arrn, 1, arrn.length - 1);
        arrn[0] = n3;
        try {
            n3 = RSSExpandedReader.parseFinderValue(arrn, FINDER_PATTERNS);
        }
        catch (NotFoundException notFoundException) {
            return null;
        }
        return new FinderPattern(n3, new int[]{n4, n2}, n4, n2, n);
    }

    private static void removePartialRows(List<ExpandedPair> list, List<ExpandedRow> object) {
        object = object.iterator();
        while (object.hasNext()) {
            boolean bl;
            block4 : {
                block3 : {
                    boolean bl2;
                    Object object2 = (ExpandedRow)object.next();
                    if (object2.getPairs().size() == list.size()) continue;
                    object2 = object2.getPairs().iterator();
                    block1 : do {
                        boolean bl3 = object2.hasNext();
                        bl2 = false;
                        bl = true;
                        if (!bl3) break block3;
                        ExpandedPair expandedPair = (ExpandedPair)object2.next();
                        Iterator<ExpandedPair> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            if (!expandedPair.equals(iterator.next())) continue;
                            continue block1;
                        }
                        bl = false;
                    } while (bl);
                    bl = bl2;
                    break block4;
                }
                bl = true;
            }
            if (!bl) continue;
            object.remove();
        }
    }

    private static void reverseCounters(int[] arrn) {
        int n = arrn.length;
        for (int i = 0; i < n / 2; ++i) {
            int n2 = arrn[i];
            int n3 = n - i - 1;
            arrn[i] = arrn[n3];
            arrn[n3] = n2;
        }
    }

    private void storeRow(int n, boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    DataCharacter decodeDataCharacter(BitArray arrn, FinderPattern finderPattern, boolean bl, boolean bl2) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int[] arrn2 = this.getDataCharacterCounters();
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        arrn2[4] = 0;
        arrn2[5] = 0;
        arrn2[6] = 0;
        arrn2[7] = 0;
        if (bl2) {
            RSSExpandedReader.recordPatternInReverse((BitArray)arrn, finderPattern.getStartEnd()[0], arrn2);
        } else {
            RSSExpandedReader.recordPattern((BitArray)arrn, finderPattern.getStartEnd()[1], arrn2);
            n2 = arrn2.length - 1;
            for (n4 = 0; n4 < n2; ++n4, --n2) {
                n3 = arrn2[n4];
                arrn2[n4] = arrn2[n2];
                arrn2[n2] = n3;
            }
        }
        float f = (float)MathUtils.sum(arrn2) / 17.0f;
        float f2 = (float)(finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0]) / 15.0f;
        if (Math.abs(f - f2) / f2 > 0.3f) {
            throw NotFoundException.getNotFoundInstance();
        }
        arrn = this.getOddCounts();
        int[] arrn3 = this.getEvenCounts();
        float[] arrf = this.getOddRoundingErrors();
        float[] arrf2 = this.getEvenRoundingErrors();
        for (n4 = 0; n4 < arrn2.length; ++n4) {
            f2 = 1.0f * (float)arrn2[n4] / f;
            n3 = (int)(0.5f + f2);
            if (n3 <= 0) {
                if (f2 < 0.3f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n2 = 1;
            } else {
                n2 = n3;
                if (n3 > 8) {
                    if (f2 > 8.7f) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    n2 = 8;
                }
            }
            n3 = n4 / 2;
            if ((n4 & 1) == 0) {
                arrn[n3] = n2;
                arrf[n3] = f2 - (float)n2;
                continue;
            }
            arrn3[n3] = n2;
            arrf2[n3] = f2 - (float)n2;
        }
        this.adjustOddEvenCounts(17);
        n4 = finderPattern.getValue();
        n2 = bl ? 0 : 2;
        int n6 = n4 * 4 + n2 + (bl2 ^ true) - 1;
        n4 = n2 = 0;
        for (n3 = arrn.length - 1; n3 >= 0; --n3) {
            n5 = n2;
            if (RSSExpandedReader.isNotA1left(finderPattern, bl, bl2)) {
                n5 = WEIGHTS[n6][2 * n3];
                n5 = n2 + arrn[n3] * n5;
            }
            n4 += arrn[n3];
            n2 = n5;
        }
        n3 = 0;
        for (n5 = arrn3.length - 1; n5 >= 0; --n5) {
            n = n3;
            if (RSSExpandedReader.isNotA1left(finderPattern, bl, bl2)) {
                n = WEIGHTS[n6][2 * n5 + 1];
                n = n3 + arrn3[n5] * n;
            }
            n3 = n;
        }
        if ((n4 & 1) == 0 && n4 <= 13 && n4 >= 4) {
            n4 = (13 - n4) / 2;
            n = SYMBOL_WIDEST[n4];
            n5 = RSSUtils.getRSSvalue(arrn, n, true);
            n = RSSUtils.getRSSvalue(arrn3, 9 - n, false);
            return new DataCharacter(n5 * EVEN_TOTAL_SUBSET[n4] + n + GSUM[n4], n2 + n3);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Result decodeRow(int n, BitArray bitArray, Map<DecodeHintType, ?> object) throws NotFoundException, FormatException {
        this.pairs.clear();
        this.startFromEven = false;
        try {
            return RSSExpandedReader.constructResult(this.decodeRow2pairs(n, bitArray));
        }
        catch (NotFoundException notFoundException) {}
        this.pairs.clear();
        this.startFromEven = true;
        return RSSExpandedReader.constructResult(this.decodeRow2pairs(n, bitArray));
    }

    List<ExpandedPair> decodeRow2pairs(int n, BitArray bitArray) throws NotFoundException {
        try {
            do {
                ExpandedPair expandedPair = this.retrieveNextPair(bitArray, this.pairs, n);
                this.pairs.add(expandedPair);
            } while (true);
        }
        catch (NotFoundException notFoundException) {
            if (this.pairs.isEmpty()) {
                throw notFoundException;
            }
            if (this.checkChecksum()) {
                return this.pairs;
            }
            boolean bl = this.rows.isEmpty();
            this.storeRow(n, false);
            if (bl ^ true) {
                List<ExpandedPair> list = this.checkRows(false);
                if (list != null) {
                    return list;
                }
                list = this.checkRows(true);
                if (list != null) {
                    return list;
                }
            }
            throw NotFoundException.getNotFoundInstance();
        }
    }

    List<ExpandedRow> getRows() {
        return this.rows;
    }

    @Override
    public void reset() {
        this.pairs.clear();
        this.rows.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ExpandedPair retrieveNextPair(BitArray object, List<ExpandedPair> list, int n) throws NotFoundException {
        boolean bl;
        FinderPattern finderPattern;
        boolean bl2 = list.size() % 2 == 0;
        boolean bl3 = bl2;
        if (this.startFromEven) {
            bl3 = bl2 ^ true;
        }
        int n2 = -1;
        boolean bl4 = true;
        do {
            this.findNextPair((BitArray)object, list, n2);
            finderPattern = this.parseFoundFinderPattern((BitArray)object, n, bl3);
            if (finderPattern == null) {
                n2 = RSSExpandedReader.getNextSecondBar((BitArray)object, this.startEnd[0]);
                bl = bl4;
            } else {
                bl = false;
            }
            bl4 = bl;
        } while (bl);
        DataCharacter dataCharacter = this.decodeDataCharacter((BitArray)object, finderPattern, bl3, true);
        if (!list.isEmpty() && list.get(list.size() - 1).mustBeLast()) {
            throw NotFoundException.getNotFoundInstance();
        }
        try {
            object = this.decodeDataCharacter((BitArray)object, finderPattern, bl3, false);
            return new ExpandedPair(dataCharacter, (DataCharacter)object, finderPattern, true);
        }
        catch (NotFoundException notFoundException) {}
        object = null;
        return new ExpandedPair(dataCharacter, (DataCharacter)object, finderPattern, true);
    }
}
