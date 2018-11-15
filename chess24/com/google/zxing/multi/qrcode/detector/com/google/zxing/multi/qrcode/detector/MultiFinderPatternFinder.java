/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

final class MultiFinderPatternFinder
extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;

    MultiFinderPatternFinder(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    MultiFinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        super(bitMatrix, resultPointCallback);
    }

    private FinderPattern[][] selectMutipleBestPatterns() throws NotFoundException {
        List<FinderPattern> list = this.getPossibleCenters();
        int n = list.size();
        if (n < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n2 = 2;
        if (n == 3) {
            return new FinderPattern[][]{{list.get(0), list.get(1), list.get(2)}};
        }
        Collections.sort(list, new ModuleSizeComparator());
        ArrayList<ResultPoint[]> arrayList = new ArrayList<ResultPoint[]>();
        for (int i = 0; i < n - 2; ++i) {
            FinderPattern finderPattern = list.get(i);
            int n3 = n2;
            if (finderPattern != null) {
                int n4 = i + 1;
                do {
                    block15 : {
                        n3 = n2;
                        if (n4 >= n - 1) break;
                        FinderPattern finderPattern2 = list.get(n4);
                        n3 = n2;
                        if (finderPattern2 != null) {
                            float f = (finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) / Math.min(finderPattern.getEstimatedModuleSize(), finderPattern2.getEstimatedModuleSize());
                            if (Math.abs(finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) > 0.5f) {
                                n3 = n2;
                                if (f >= 0.05f) break;
                            }
                            int n5 = n4 + 1;
                            do {
                                n3 = n2;
                                if (n5 >= n) break;
                                Object object = list.get(n5);
                                if (object != null) {
                                    f = (finderPattern2.getEstimatedModuleSize() - object.getEstimatedModuleSize()) / Math.min(finderPattern2.getEstimatedModuleSize(), object.getEstimatedModuleSize());
                                    if (Math.abs(finderPattern2.getEstimatedModuleSize() - object.getEstimatedModuleSize()) > 0.5f && f >= 0.05f) {
                                        n2 = 2;
                                        break block15;
                                    }
                                    ResultPoint[] arrresultPoint = new FinderPattern[3];
                                    arrresultPoint[0] = finderPattern;
                                    arrresultPoint[1] = finderPattern2;
                                    n3 = 2;
                                    arrresultPoint[2] = object;
                                    ResultPoint.orderBestPatterns(arrresultPoint);
                                    object = new FinderPatternInfo((FinderPattern[])arrresultPoint);
                                    float f2 = ResultPoint.distance(object.getTopLeft(), object.getBottomLeft());
                                    f = ResultPoint.distance(object.getTopRight(), object.getBottomLeft());
                                    float f3 = ResultPoint.distance(object.getTopLeft(), object.getTopRight());
                                    float f4 = (f2 + f3) / (finderPattern.getEstimatedModuleSize() * 2.0f);
                                    n2 = n3;
                                    if (f4 <= 180.0f) {
                                        n2 = n3;
                                        if (f4 >= 9.0f) {
                                            n2 = n3;
                                            if (Math.abs((f2 - f3) / Math.min(f2, f3)) < 0.1f) {
                                                f2 = (float)Math.sqrt(f2 * f2 + f3 * f3);
                                                n2 = n3;
                                                if (Math.abs((f - f2) / Math.min(f, f2)) < 0.1f) {
                                                    arrayList.add(arrresultPoint);
                                                    n2 = n3;
                                                }
                                            }
                                        }
                                    }
                                }
                                ++n5;
                            } while (true);
                        }
                        n2 = n3;
                    }
                    ++n4;
                } while (true);
            }
            n2 = n3;
        }
        if (!arrayList.isEmpty()) {
            return (FinderPattern[][])arrayList.toArray((T[])new FinderPattern[arrayList.size()][]);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> arrfinderPattern) throws NotFoundException {
        int n;
        int n2 = 0;
        int n3 = arrfinderPattern != null && arrfinderPattern.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        boolean bl = arrfinderPattern != null && arrfinderPattern.containsKey((Object)DecodeHintType.PURE_BARCODE);
        arrfinderPattern = this.getImage();
        int n4 = arrfinderPattern.getHeight();
        int n5 = arrfinderPattern.getWidth();
        int n6 = (int)((float)n4 / 228.0f * 3.0f);
        if (n6 < 3 || n3 != 0) {
            n6 = 3;
        }
        Object object = new int[5];
        for (int i = n6 - 1; i < n4; i += n6) {
            int n7;
            object[0] = 0;
            object[1] = 0;
            object[2] = 0;
            object[3] = 0;
            object[4] = false;
            n3 = n7 = 0;
            while (n7 < n5) {
                if (arrfinderPattern.get(n7, i)) {
                    n = n3;
                    if ((n3 & 1) == 1) {
                        n = n3 + 1;
                    }
                    object[n] = object[n] + true;
                    n3 = n;
                } else if ((n3 & 1) == 0) {
                    if (n3 == 4) {
                        if (MultiFinderPatternFinder.foundPatternCross((int[])object) && this.handlePossibleCenter((int[])object, i, n7, bl)) {
                            object[0] = false;
                            object[1] = false;
                            object[2] = false;
                            object[3] = false;
                            object[4] = false;
                            n3 = 0;
                        } else {
                            object[0] = object[2];
                            object[1] = object[3];
                            object[2] = object[4];
                            object[3] = true;
                            object[4] = false;
                            n3 = 3;
                        }
                    } else {
                        object[++n3] = object[n3] + true;
                    }
                } else {
                    object[n3] = object[n3] + true;
                }
                ++n7;
            }
            if (!MultiFinderPatternFinder.foundPatternCross((int[])object)) continue;
            this.handlePossibleCenter((int[])object, i, n5, bl);
        }
        arrfinderPattern = this.selectMutipleBestPatterns();
        object = new ArrayList();
        n = arrfinderPattern.length;
        for (n3 = n2; n3 < n; ++n3) {
            ResultPoint[] arrresultPoint = arrfinderPattern[n3];
            ResultPoint.orderBestPatterns(arrresultPoint);
            object.add(new FinderPatternInfo((FinderPattern[])arrresultPoint));
        }
        if (object.isEmpty()) {
            return EMPTY_RESULT_ARRAY;
        }
        return object.toArray(new FinderPatternInfo[object.size()]);
    }

    private static final class ModuleSizeComparator
    implements Serializable,
    Comparator<FinderPattern> {
        private ModuleSizeComparator() {
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            double d = finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize();
            if (d < 0.0) {
                return -1;
            }
            if (d > 0.0) {
                return 1;
            }
            return 0;
        }
    }

}
