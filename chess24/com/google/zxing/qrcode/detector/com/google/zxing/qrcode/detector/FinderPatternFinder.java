/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList<FinderPattern>();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] arrn, int n) {
        return (float)(n - arrn[4] - arrn[3]) - (float)arrn[2] / 2.0f;
    }

    private boolean crossCheckDiagonal(int n, int n2, int n3, int n4) {
        int n5;
        int[] arrn = this.getCrossCheckStateCount();
        for (n5 = 0; n >= n5 && n2 >= n5 && this.image.get(n2 - n5, n - n5); ++n5) {
            arrn[2] = arrn[2] + 1;
        }
        if (n >= n5) {
            int n6;
            if (n2 < n5) {
                return false;
            }
            for (n6 = n5; n >= n6 && n2 >= n6 && !this.image.get(n2 - n6, n - n6) && arrn[1] <= n3; ++n6) {
                arrn[1] = arrn[1] + 1;
            }
            if (n >= n6 && n2 >= n6) {
                int n7;
                if (arrn[1] > n3) {
                    return false;
                }
                while (n >= n6 && n2 >= n6 && this.image.get(n2 - n6, n - n6) && arrn[0] <= n3) {
                    arrn[0] = arrn[0] + 1;
                    ++n6;
                }
                if (arrn[0] > n3) {
                    return false;
                }
                int n8 = this.image.getHeight();
                int n9 = this.image.getWidth();
                n5 = 1;
                while ((n6 = n + n5) < n8 && (n7 = n2 + n5) < n9 && this.image.get(n7, n6)) {
                    arrn[2] = arrn[2] + 1;
                    ++n5;
                }
                if (n6 < n8) {
                    n6 = n5;
                    if (n2 + n5 >= n9) {
                        return false;
                    }
                    while ((n5 = n + n6) < n8 && (n7 = n2 + n6) < n9 && !this.image.get(n7, n5) && arrn[3] < n3) {
                        arrn[3] = arrn[3] + 1;
                        ++n6;
                    }
                    if (n5 < n8 && n2 + n6 < n9) {
                        if (arrn[3] >= n3) {
                            return false;
                        }
                        while ((n5 = n + n6) < n8 && (n7 = n2 + n6) < n9 && this.image.get(n7, n5) && arrn[4] < n3) {
                            arrn[4] = arrn[4] + 1;
                            ++n6;
                        }
                        if (arrn[4] >= n3) {
                            return false;
                        }
                        if (Math.abs(arrn[0] + arrn[1] + arrn[2] + arrn[3] + arrn[4] - n4) < 2 * n4 && FinderPatternFinder.foundPatternCross(arrn)) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private float crossCheckHorizontal(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        BitMatrix bitMatrix = this.image;
        int n7 = bitMatrix.getWidth();
        int[] arrn = this.getCrossCheckStateCount();
        for (n6 = n; n6 >= 0 && bitMatrix.get(n6, n2); --n6) {
            arrn[2] = arrn[2] + 1;
        }
        if (n6 < 0) {
            return Float.NaN;
        }
        for (n5 = n6; n5 >= 0 && !bitMatrix.get(n5, n2) && arrn[1] <= n3; --n5) {
            arrn[1] = arrn[1] + 1;
        }
        if (n5 >= 0) {
            if (arrn[1] > n3) {
                return Float.NaN;
            }
            while (n5 >= 0 && bitMatrix.get(n5, n2) && arrn[0] <= n3) {
                arrn[0] = arrn[0] + 1;
                --n5;
            }
            if (arrn[0] > n3) {
                return Float.NaN;
            }
            ++n;
            while (n < n7 && bitMatrix.get(n, n2)) {
                arrn[2] = arrn[2] + 1;
                ++n;
            }
            if (n == n7) {
                return Float.NaN;
            }
            for (n6 = n; n6 < n7 && !bitMatrix.get(n6, n2) && arrn[3] < n3; ++n6) {
                arrn[3] = arrn[3] + 1;
            }
            if (n6 != n7) {
                if (arrn[3] >= n3) {
                    return Float.NaN;
                }
                while (n6 < n7 && bitMatrix.get(n6, n2) && arrn[4] < n3) {
                    arrn[4] = arrn[4] + 1;
                    ++n6;
                }
                if (arrn[4] >= n3) {
                    return Float.NaN;
                }
                if (5 * Math.abs(arrn[0] + arrn[1] + arrn[2] + arrn[3] + arrn[4] - n4) >= n4) {
                    return Float.NaN;
                }
                if (FinderPatternFinder.foundPatternCross(arrn)) {
                    return FinderPatternFinder.centerFromEnd(arrn, n6);
                }
                return Float.NaN;
            }
            return Float.NaN;
        }
        return Float.NaN;
    }

    private float crossCheckVertical(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        BitMatrix bitMatrix = this.image;
        int n7 = bitMatrix.getHeight();
        int[] arrn = this.getCrossCheckStateCount();
        for (n6 = n; n6 >= 0 && bitMatrix.get(n2, n6); --n6) {
            arrn[2] = arrn[2] + 1;
        }
        if (n6 < 0) {
            return Float.NaN;
        }
        for (n5 = n6; n5 >= 0 && !bitMatrix.get(n2, n5) && arrn[1] <= n3; --n5) {
            arrn[1] = arrn[1] + 1;
        }
        if (n5 >= 0) {
            if (arrn[1] > n3) {
                return Float.NaN;
            }
            while (n5 >= 0 && bitMatrix.get(n2, n5) && arrn[0] <= n3) {
                arrn[0] = arrn[0] + 1;
                --n5;
            }
            if (arrn[0] > n3) {
                return Float.NaN;
            }
            ++n;
            while (n < n7 && bitMatrix.get(n2, n)) {
                arrn[2] = arrn[2] + 1;
                ++n;
            }
            if (n == n7) {
                return Float.NaN;
            }
            for (n6 = n; n6 < n7 && !bitMatrix.get(n2, n6) && arrn[3] < n3; ++n6) {
                arrn[3] = arrn[3] + 1;
            }
            if (n6 != n7) {
                if (arrn[3] >= n3) {
                    return Float.NaN;
                }
                while (n6 < n7 && bitMatrix.get(n2, n6) && arrn[4] < n3) {
                    arrn[4] = arrn[4] + 1;
                    ++n6;
                }
                if (arrn[4] >= n3) {
                    return Float.NaN;
                }
                if (5 * Math.abs(arrn[0] + arrn[1] + arrn[2] + arrn[3] + arrn[4] - n4) >= 2 * n4) {
                    return Float.NaN;
                }
                if (FinderPatternFinder.foundPatternCross(arrn)) {
                    return FinderPatternFinder.centerFromEnd(arrn, n6);
                }
                return Float.NaN;
            }
            return Float.NaN;
        }
        return Float.NaN;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        FinderPattern finderPattern = null;
        for (FinderPattern finderPattern2 : this.possibleCenters) {
            if (finderPattern2.getCount() < 2) continue;
            if (finderPattern == null) {
                finderPattern = finderPattern2;
                continue;
            }
            this.hasSkipped = true;
            return (int)(Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY())) / 2;
        }
        return 0;
    }

    protected static boolean foundPatternCross(int[] arrn) {
        int n;
        int n2 = n = 0;
        while (n < 5) {
            int n3 = arrn[n];
            if (n3 == 0) {
                return false;
            }
            n2 += n3;
            ++n;
        }
        if (n2 < 7) {
            return false;
        }
        float f = (float)n2 / 7.0f;
        float f2 = f / 2.0f;
        if (Math.abs(f - (float)arrn[0]) < f2 && Math.abs(f - (float)arrn[1]) < f2 && Math.abs(3.0f * f - (float)arrn[2]) < 3.0f * f2 && Math.abs(f - (float)arrn[3]) < f2 && Math.abs(f - (float)arrn[4]) < f2) {
            return true;
        }
        return false;
    }

    private int[] getCrossCheckStateCount() {
        this.crossCheckStateCount[0] = 0;
        this.crossCheckStateCount[1] = 0;
        this.crossCheckStateCount[2] = 0;
        this.crossCheckStateCount[3] = 0;
        this.crossCheckStateCount[4] = 0;
        return this.crossCheckStateCount;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int n = this.possibleCenters.size();
        Iterator<FinderPattern> iterator = this.possibleCenters.iterator();
        float f = 0.0f;
        float f2 = 0.0f;
        int n2 = 0;
        while (iterator.hasNext()) {
            FinderPattern finderPattern = iterator.next();
            if (finderPattern.getCount() < 2) continue;
            ++n2;
            f2 += finderPattern.getEstimatedModuleSize();
        }
        if (n2 < 3) {
            return false;
        }
        float f3 = f2 / (float)n;
        iterator = this.possibleCenters.iterator();
        while (iterator.hasNext()) {
            f += Math.abs(iterator.next().getEstimatedModuleSize() - f3);
        }
        if (f <= 0.05f * f2) {
            return true;
        }
        return false;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        Iterator<FinderPattern> iterator;
        float f;
        int n = this.possibleCenters.size();
        if (n < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        float f2 = 0.0f;
        if (n > 3) {
            float f3;
            float f4;
            iterator = this.possibleCenters.iterator();
            f = f4 = 0.0f;
            while (iterator.hasNext()) {
                f3 = iterator.next().getEstimatedModuleSize();
                f4 += f3;
                f += f3 * f3;
            }
            f3 = n;
            f = (float)Math.sqrt(f / f3 - f4 * (f4 /= f3));
            Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(f4));
            f = Math.max(0.2f * f4, f);
            n = 0;
            while (n < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                int n2 = n;
                if (Math.abs(this.possibleCenters.get(n).getEstimatedModuleSize() - f4) > f) {
                    this.possibleCenters.remove(n);
                    n2 = n - 1;
                }
                n = n2 + 1;
            }
        }
        if (this.possibleCenters.size() > 3) {
            iterator = this.possibleCenters.iterator();
            f = f2;
            while (iterator.hasNext()) {
                f += iterator.next().getEstimatedModuleSize();
            }
            Collections.sort(this.possibleCenters, new CenterComparator(f /= (float)this.possibleCenters.size()));
            this.possibleCenters.subList(3, this.possibleCenters.size()).clear();
        }
        return new FinderPattern[]{this.possibleCenters.get(0), this.possibleCenters.get(1), this.possibleCenters.get(2)};
    }

    final FinderPatternInfo find(Map<DecodeHintType, ?> arrobject) throws NotFoundException {
        int n = arrobject != null && arrobject.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        boolean bl = arrobject != null && arrobject.containsKey((Object)DecodeHintType.PURE_BARCODE);
        int n2 = this.image.getHeight();
        int n3 = this.image.getWidth();
        int n4 = 3 * n2 / 228;
        if (n4 < 3 || n != 0) {
            n4 = 3;
        }
        arrobject = new int[5];
        boolean bl2 = false;
        for (int i = n4 - 1; i < n2 && !bl2; i += n4) {
            arrobject[0] = 0;
            arrobject[1] = 0;
            arrobject[2] = 0;
            arrobject[3] = 0;
            arrobject[4] = 0;
            int n5 = n4;
            n4 = n = 0;
            while (n < n3) {
                block9 : {
                    block10 : {
                        block11 : {
                            block14 : {
                                block12 : {
                                    block13 : {
                                        boolean bl3;
                                        int n6;
                                        block8 : {
                                            if (!this.image.get(n, i)) break block8;
                                            n6 = n4;
                                            if ((n4 & 1) == 1) {
                                                n6 = n4 + 1;
                                            }
                                            arrobject[n6] = arrobject[n6] + 1;
                                            n4 = n6;
                                            break block9;
                                        }
                                        if ((n4 & 1) != 0) break block10;
                                        if (n4 != 4) break block11;
                                        if (!FinderPatternFinder.foundPatternCross(arrobject)) break block12;
                                        if (!this.handlePossibleCenter((int[])arrobject, i, n, bl)) break block13;
                                        if (this.hasSkipped) {
                                            bl3 = this.haveMultiplyConfirmedCenters();
                                            n4 = i;
                                        } else {
                                            n5 = this.findRowSkip();
                                            n4 = i;
                                            bl3 = bl2;
                                            if (n5 > arrobject[2]) {
                                                n4 = i + (n5 - arrobject[2] - 2);
                                                n = n3 - 1;
                                                bl3 = bl2;
                                            }
                                        }
                                        arrobject[0] = 0;
                                        arrobject[1] = 0;
                                        arrobject[2] = 0;
                                        arrobject[3] = 0;
                                        arrobject[4] = 0;
                                        n6 = 0;
                                        n5 = 2;
                                        i = n4;
                                        n4 = n6;
                                        bl2 = bl3;
                                        break block9;
                                    }
                                    arrobject[0] = arrobject[2];
                                    arrobject[1] = arrobject[3];
                                    arrobject[2] = arrobject[4];
                                    arrobject[3] = 1;
                                    arrobject[4] = 0;
                                    break block14;
                                }
                                arrobject[0] = arrobject[2];
                                arrobject[1] = arrobject[3];
                                arrobject[2] = arrobject[4];
                                arrobject[3] = 1;
                                arrobject[4] = 0;
                            }
                            n4 = 3;
                            break block9;
                        }
                        arrobject[++n4] = arrobject[n4] + 1;
                        break block9;
                    }
                    arrobject[n4] = arrobject[n4] + 1;
                }
                ++n;
            }
            if (FinderPatternFinder.foundPatternCross(arrobject) && this.handlePossibleCenter((int[])arrobject, i, n3, bl)) {
                n4 = arrobject[0];
                if (!this.hasSkipped) continue;
                bl2 = this.haveMultiplyConfirmedCenters();
                continue;
            }
            n4 = n5;
        }
        arrobject = this.selectBestPatterns();
        ResultPoint.orderBestPatterns((ResultPoint[])arrobject);
        return new FinderPatternInfo((FinderPattern[])arrobject);
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    protected final boolean handlePossibleCenter(int[] object, int n, int n2, boolean bl) {
        float f;
        int n3 = 0;
        int n4 = object[0] + object[1] + object[2] + object[3] + object[4];
        float f2 = this.crossCheckVertical(n, n2 = (int)FinderPatternFinder.centerFromEnd((int[])object, n2), object[2], n4);
        if (!(Float.isNaN(f2) || Float.isNaN(f = this.crossCheckHorizontal(n2, n = (int)f2, object[2], n4)) || bl && !this.crossCheckDiagonal(n, (int)f, object[2], n4))) {
            float f3 = (float)n4 / 7.0f;
            n = 0;
            do {
                n2 = n3;
                if (n >= this.possibleCenters.size()) break;
                object = this.possibleCenters.get(n);
                if (object.aboutEquals(f3, f2, f)) {
                    this.possibleCenters.set(n, object.combineEstimate(f2, f, f3));
                    n2 = 1;
                    break;
                }
                ++n;
            } while (true);
            if (n2 == 0) {
                object = new FinderPattern(f, f2, f3);
                this.possibleCenters.add((FinderPattern)object);
                if (this.resultPointCallback != null) {
                    this.resultPointCallback.foundPossibleResultPoint((ResultPoint)object);
                }
            }
            return true;
        }
        return false;
    }

    private static final class CenterComparator
    implements Serializable,
    Comparator<FinderPattern> {
        private final float average;

        private CenterComparator(float f) {
            this.average = f;
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            if (finderPattern2.getCount() == finderPattern.getCount()) {
                float f;
                float f2 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
                if (f2 < (f = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
                    return 1;
                }
                if (f2 == f) {
                    return 0;
                }
                return -1;
            }
            return finderPattern2.getCount() - finderPattern.getCount();
        }
    }

    private static final class FurthestFromAverageComparator
    implements Serializable,
    Comparator<FinderPattern> {
        private final float average;

        private FurthestFromAverageComparator(float f) {
            this.average = f;
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float f;
            float f2 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
            if (f2 < (f = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
                return -1;
            }
            if (f2 == f) {
                return 0;
            }
            return 1;
        }
    }

}
