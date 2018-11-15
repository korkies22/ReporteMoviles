/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BarcodeValue;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DecodedBitStreamParser;
import com.google.zxing.pdf417.decoder.DetectionResult;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;
import com.google.zxing.pdf417.decoder.DetectionResultRowIndicatorColumn;
import com.google.zxing.pdf417.decoder.PDF417CodewordDecoder;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;

public final class PDF417ScanningDecoder {
    private static final int CODEWORD_SKEW_SIZE = 2;
    private static final int MAX_EC_CODEWORDS = 512;
    private static final int MAX_ERRORS = 3;
    private static final ErrorCorrection errorCorrection = new ErrorCorrection();

    private PDF417ScanningDecoder() {
    }

    private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) throws NotFoundException {
        int n;
        int n2;
        if (detectionResultRowIndicatorColumn == null) {
            return null;
        }
        int[] arrn = detectionResultRowIndicatorColumn.getRowHeights();
        if (arrn == null) {
            return null;
        }
        int n3 = PDF417ScanningDecoder.getMax(arrn);
        int n4 = 0;
        int n5 = arrn.length;
        int n6 = n = 0;
        do {
            n2 = n6;
            if (n >= n5) break;
            int n7 = arrn[n];
            n2 = n6 += n3 - n7;
            if (n7 > 0) break;
            ++n;
        } while (true);
        Codeword[] arrcodeword = detectionResultRowIndicatorColumn.getCodewords();
        n6 = 0;
        n = n2;
        n2 = n6;
        while (n > 0 && arrcodeword[n2] == null) {
            --n;
            ++n2;
        }
        n5 = arrn.length - 1;
        n6 = n4;
        do {
            n2 = n6;
            if (n5 < 0) break;
            n2 = n6 += n3 - arrn[n5];
            if (arrn[n5] > 0) break;
            --n5;
        } while (true);
        n5 = arrcodeword.length - 1;
        n6 = n2;
        n2 = n5;
        while (n6 > 0 && arrcodeword[n2] == null) {
            --n6;
            --n2;
        }
        return detectionResultRowIndicatorColumn.getBoundingBox().addMissingRows(n, n6, detectionResultRowIndicatorColumn.isLeft());
    }

    private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] arrbarcodeValue) throws NotFoundException {
        int[] arrn = arrbarcodeValue[0][1].getValue();
        int n = detectionResult.getBarcodeColumnCount() * detectionResult.getBarcodeRowCount() - PDF417ScanningDecoder.getNumberOfECCodeWords(detectionResult.getBarcodeECLevel());
        if (arrn.length == 0) {
            if (n > 0 && n <= 928) {
                arrbarcodeValue[0][1].setValue(n);
                return;
            }
            throw NotFoundException.getNotFoundInstance();
        }
        if (arrn[0] != n) {
            arrbarcodeValue[0][1].setValue(n);
        }
    }

    private static int adjustCodewordStartColumn(BitMatrix bitMatrix, int n, int n2, boolean bl, int n3, int n4) {
        int n5 = bl ? -1 : 1;
        int n6 = n5;
        int n7 = n3;
        for (n5 = 0; n5 < 2; ++n5) {
            while ((bl ? n7 >= n : n7 < n2) && bl == bitMatrix.get(n7, n4)) {
                if (Math.abs(n3 - n7) > 2) {
                    return n3;
                }
                n7 += n6;
            }
            n6 = - n6;
            bl = !bl;
        }
        return n7;
    }

    private static boolean checkCodewordSkew(int n, int n2, int n3) {
        if (n2 - 2 <= n && n <= n3 + 2) {
            return true;
        }
        return false;
    }

    private static int correctErrors(int[] arrn, int[] arrn2, int n) throws ChecksumException {
        if ((arrn2 == null || arrn2.length <= n / 2 + 3) && n >= 0 && n <= 512) {
            return errorCorrection.decode(arrn, n, arrn2);
        }
        throw ChecksumException.getChecksumInstance();
    }

    private static BarcodeValue[][] createBarcodeMatrix(DetectionResult arrdetectionResultColumn) {
        int n;
        int n2;
        BarcodeValue[][] arrbarcodeValue = (BarcodeValue[][])Array.newInstance(BarcodeValue.class, arrdetectionResultColumn.getBarcodeRowCount(), arrdetectionResultColumn.getBarcodeColumnCount() + 2);
        for (n2 = 0; n2 < arrbarcodeValue.length; ++n2) {
            for (n = 0; n < arrbarcodeValue[n2].length; ++n) {
                arrbarcodeValue[n2][n] = new BarcodeValue();
            }
        }
        arrdetectionResultColumn = arrdetectionResultColumn.getDetectionResultColumns();
        int n3 = arrdetectionResultColumn.length;
        n = n2 = 0;
        while (n2 < n3) {
            Codeword[] arrcodeword = arrdetectionResultColumn[n2];
            if (arrcodeword != null) {
                for (Codeword codeword : arrcodeword.getCodewords()) {
                    int n4;
                    if (codeword == null || (n4 = codeword.getRowNumber()) < 0 || n4 >= arrbarcodeValue.length) continue;
                    arrbarcodeValue[n4][n].setValue(codeword.getValue());
                }
            }
            ++n;
            ++n2;
        }
        return arrbarcodeValue;
    }

    private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws FormatException, ChecksumException, NotFoundException {
        int n;
        BarcodeValue[][] arrbarcodeValue = PDF417ScanningDecoder.createBarcodeMatrix(detectionResult);
        PDF417ScanningDecoder.adjustCodewordCount(detectionResult, arrbarcodeValue);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int[] arrn = new int[detectionResult.getBarcodeRowCount() * detectionResult.getBarcodeColumnCount()];
        ArrayList<BarcodeValue[]> arrayList2 = new ArrayList<BarcodeValue[]>();
        ArrayList<Integer> arrayList3 = new ArrayList<Integer>();
        int n2 = 0;
        for (n = 0; n < detectionResult.getBarcodeRowCount(); ++n) {
            int n3 = 0;
            while (n3 < detectionResult.getBarcodeColumnCount()) {
                BarcodeValue[] arrbarcodeValue2 = arrbarcodeValue[n];
                int n4 = n3 + 1;
                arrbarcodeValue2 = arrbarcodeValue2[n4].getValue();
                n3 = detectionResult.getBarcodeColumnCount() * n + n3;
                if (arrbarcodeValue2.length == 0) {
                    arrayList.add(n3);
                } else if (arrbarcodeValue2.length == 1) {
                    arrn[n3] = (int)arrbarcodeValue2[0];
                } else {
                    arrayList3.add(n3);
                    arrayList2.add(arrbarcodeValue2);
                }
                n3 = n4;
            }
        }
        arrbarcodeValue = new int[arrayList2.size()][];
        for (n = n2; n < arrbarcodeValue.length; ++n) {
            arrbarcodeValue[n] = (int[])arrayList2.get(n);
        }
        return PDF417ScanningDecoder.createDecoderResultFromAmbiguousValues(detectionResult.getBarcodeECLevel(), arrn, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), (int[][])arrbarcodeValue);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static DecoderResult createDecoderResultFromAmbiguousValues(int n, int[] arrn, int[] arrn2, int[] arrn3, int[][] arrn4) throws FormatException, ChecksumException {
        int n2 = 100;
        int[] arrn5 = new int[arrn3.length];
        do {
            int n3;
            if (n2 <= 0) {
                throw ChecksumException.getChecksumInstance();
            }
            for (n3 = 0; n3 < arrn5.length; ++n3) {
                arrn[arrn3[n3]] = arrn4[n3][arrn5[n3]];
            }
            try {
                return PDF417ScanningDecoder.decodeCodewords(arrn, n, arrn2);
            }
            catch (ChecksumException checksumException) {}
            if (arrn5.length == 0) {
                throw ChecksumException.getChecksumInstance();
            }
            for (n3 = 0; n3 < arrn5.length; ++n3) {
                if (arrn5[n3] < arrn4[n3].length - 1) {
                    arrn5[n3] = arrn5[n3] + 1;
                    break;
                }
                arrn5[n3] = 0;
                if (n3 != arrn5.length - 1) continue;
                throw ChecksumException.getChecksumInstance();
            }
            --n2;
        } while (true);
    }

    public static DecoderResult decode(BitMatrix bitMatrix, ResultPoint object, ResultPoint object2, ResultPoint resultPoint, ResultPoint object3, int n, int n2) throws NotFoundException, FormatException, ChecksumException {
        Object object4;
        int n3;
        BoundingBox boundingBox = new BoundingBox(bitMatrix, (ResultPoint)object, (ResultPoint)object2, resultPoint, (ResultPoint)object3);
        object3 = object4 = (object2 = null);
        for (n3 = 0; n3 < 2; ++n3) {
            if (object != null) {
                object2 = PDF417ScanningDecoder.getRowIndicatorColumn(bitMatrix, boundingBox, (ResultPoint)object, true, n, n2);
            }
            if (resultPoint != null) {
                object3 = PDF417ScanningDecoder.getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint, false, n, n2);
            }
            if ((object4 = PDF417ScanningDecoder.merge((DetectionResultRowIndicatorColumn)object2, (DetectionResultRowIndicatorColumn)object3)) == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (n3 == 0 && object4.getBoundingBox() != null && (object4.getBoundingBox().getMinY() < boundingBox.getMinY() || object4.getBoundingBox().getMaxY() > boundingBox.getMaxY())) {
                boundingBox = object4.getBoundingBox();
                continue;
            }
            object4.setBoundingBox(boundingBox);
            break;
        }
        int n4 = object4.getBarcodeColumnCount() + 1;
        object4.setDetectionResultColumn(0, (DetectionResultColumn)object2);
        object4.setDetectionResultColumn(n4, (DetectionResultColumn)object3);
        boolean bl = object2 != null;
        int n5 = 1;
        n3 = n2;
        for (n2 = n5; n2 <= n4; ++n2) {
            int n6 = bl ? n2 : n4 - n2;
            int n7 = n;
            n5 = n3;
            if (object4.getDetectionResultColumn(n6) == null) {
                if (n6 != 0 && n6 != n4) {
                    object = new DetectionResultColumn(boundingBox);
                } else {
                    boolean bl2 = n6 == 0;
                    object = new DetectionResultRowIndicatorColumn(boundingBox, bl2);
                }
                object4.setDetectionResultColumn(n6, (DetectionResultColumn)object);
                n5 = n;
                n7 = -1;
                n = n3;
                n3 = n7;
                for (int i = boundingBox.getMinY(); i <= boundingBox.getMaxY(); ++i) {
                    n7 = PDF417ScanningDecoder.getStartColumn((DetectionResult)object4, n6, i, bl);
                    if (n7 < 0 || n7 > boundingBox.getMaxX()) {
                        if (n3 == -1) continue;
                        n7 = n3;
                    }
                    if ((object2 = PDF417ScanningDecoder.detectCodeword(bitMatrix, boundingBox.getMinX(), boundingBox.getMaxX(), bl, n7, i, n5, n)) == null) continue;
                    object.setCodeword(i, (Codeword)object2);
                    n5 = Math.min(n5, object2.getWidth());
                    n = Math.max(n, object2.getWidth());
                    n3 = n7;
                }
                n7 = n5;
                n5 = n;
            }
            n = n7;
            n3 = n5;
        }
        return PDF417ScanningDecoder.createDecoderResult((DetectionResult)object4);
    }

    private static DecoderResult decodeCodewords(int[] object, int n, int[] arrn) throws FormatException, ChecksumException {
        if (((int[])object).length == 0) {
            throw FormatException.getFormatInstance();
        }
        int n2 = 1 << n + 1;
        int n3 = PDF417ScanningDecoder.correctErrors((int[])object, arrn, n2);
        PDF417ScanningDecoder.verifyCodewordCount((int[])object, n2);
        object = DecodedBitStreamParser.decode((int[])object, String.valueOf(n));
        object.setErrorsCorrected(n3);
        object.setErasures(arrn.length);
        return object;
    }

    private static Codeword detectCodeword(BitMatrix arrn, int n, int n2, boolean bl, int n3, int n4, int n5, int n6) {
        n3 = PDF417ScanningDecoder.adjustCodewordStartColumn((BitMatrix)arrn, n, n2, bl, n3, n4);
        if ((arrn = PDF417ScanningDecoder.getModuleBitCount((BitMatrix)arrn, n, n2, bl, n3, n4)) == null) {
            return null;
        }
        n4 = MathUtils.sum(arrn);
        if (bl) {
            n2 = n3 + n4;
            n = n3;
            n3 = n2;
        } else {
            for (n = 0; n < arrn.length / 2; ++n) {
                n2 = arrn[n];
                arrn[n] = arrn[arrn.length - 1 - n];
                arrn[arrn.length - 1 - n] = n2;
            }
            n = n3 - n4;
        }
        if (!PDF417ScanningDecoder.checkCodewordSkew(n4, n5, n6)) {
            return null;
        }
        n2 = PDF417CodewordDecoder.getDecodedValue(arrn);
        n4 = PDF417Common.getCodeword(n2);
        if (n4 == -1) {
            return null;
        }
        return new Codeword(n, n3, PDF417ScanningDecoder.getCodewordBucketNumber(n2), n4);
    }

    private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn object, DetectionResultRowIndicatorColumn object2) {
        if (object != null && (object = object.getBarcodeMetadata()) != null) {
            if (object2 != null) {
                if ((object2 = object2.getBarcodeMetadata()) == null) {
                    return object;
                }
                if (object.getColumnCount() != object2.getColumnCount() && object.getErrorCorrectionLevel() != object2.getErrorCorrectionLevel() && object.getRowCount() != object2.getRowCount()) {
                    return null;
                }
                return object;
            }
            return object;
        }
        if (object2 == null) {
            return null;
        }
        return object2.getBarcodeMetadata();
    }

    private static int[] getBitCountForCodeword(int n) {
        int[] arrn = new int[8];
        int n2 = 0;
        int n3 = 7;
        do {
            int n4 = n & 1;
            int n5 = n2;
            int n6 = n3;
            if (n4 != n2) {
                n6 = n3 - 1;
                if (n6 >= 0) {
                    n5 = n4;
                } else {
                    return arrn;
                }
            }
            arrn[n6] = arrn[n6] + 1;
            n >>= 1;
            n2 = n5;
            n3 = n6;
        } while (true);
    }

    private static int getCodewordBucketNumber(int n) {
        return PDF417ScanningDecoder.getCodewordBucketNumber(PDF417ScanningDecoder.getBitCountForCodeword(n));
    }

    private static int getCodewordBucketNumber(int[] arrn) {
        return (arrn[0] - arrn[2] + arrn[4] - arrn[6] + 9) % 9;
    }

    private static int getMax(int[] arrn) {
        int n = -1;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            n = Math.max(n, arrn[i]);
        }
        return n;
    }

    private static int[] getModuleBitCount(BitMatrix bitMatrix, int n, int n2, boolean bl, int n3, int n4) {
        int[] arrn = new int[8];
        int n5 = bl ? 1 : -1;
        boolean bl2 = bl;
        int n6 = 0;
        while ((bl ? n3 < n2 : n3 >= n) && n6 < 8) {
            if (bitMatrix.get(n3, n4) == bl2) {
                arrn[n6] = arrn[n6] + 1;
                n3 += n5;
                continue;
            }
            ++n6;
            if (!bl2) {
                bl2 = true;
                continue;
            }
            bl2 = false;
        }
        if (n6 != 8) {
            if (bl) {
                n = n2;
            }
            if (n3 == n && n6 == 7) {
                return arrn;
            }
            return null;
        }
        return arrn;
    }

    private static int getNumberOfECCodeWords(int n) {
        return 2 << n;
    }

    private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix bitMatrix, BoundingBox boundingBox, ResultPoint resultPoint, boolean bl, int n, int n2) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, bl);
        for (int i = 0; i < 2; ++i) {
            int n3 = i == 0 ? 1 : -1;
            int n4 = (int)resultPoint.getX();
            for (int j = (int)resultPoint.getY(); j <= boundingBox.getMaxY() && j >= boundingBox.getMinY(); j += n3) {
                Codeword codeword = PDF417ScanningDecoder.detectCodeword(bitMatrix, 0, bitMatrix.getWidth(), bl, n4, j, n, n2);
                if (codeword == null) continue;
                detectionResultRowIndicatorColumn.setCodeword(j, codeword);
                n4 = bl ? codeword.getStartX() : codeword.getEndX();
            }
        }
        return detectionResultRowIndicatorColumn;
    }

    private static int getStartColumn(DetectionResult detectionResult, int n, int n2, boolean bl) {
        int n3 = bl ? 1 : -1;
        Object object = null;
        int n4 = n - n3;
        if (PDF417ScanningDecoder.isValidBarcodeColumn(detectionResult, n4)) {
            object = detectionResult.getDetectionResultColumn(n4).getCodeword(n2);
        }
        if (object != null) {
            if (bl) {
                return object.getEndX();
            }
            return object.getStartX();
        }
        object = detectionResult.getDetectionResultColumn(n).getCodewordNearby(n2);
        if (object != null) {
            if (bl) {
                return object.getStartX();
            }
            return object.getEndX();
        }
        if (PDF417ScanningDecoder.isValidBarcodeColumn(detectionResult, n4)) {
            object = detectionResult.getDetectionResultColumn(n4).getCodewordNearby(n2);
        }
        if (object != null) {
            if (bl) {
                return object.getEndX();
            }
            return object.getStartX();
        }
        n4 = 0;
        n2 = n;
        n = n4;
        while (PDF417ScanningDecoder.isValidBarcodeColumn(detectionResult, n4 = n2 - n3)) {
            object = detectionResult.getDetectionResultColumn(n4).getCodewords();
            int n5 = ((Codeword[])object).length;
            for (n2 = 0; n2 < n5; ++n2) {
                Codeword codeword = object[n2];
                if (codeword == null) continue;
                n2 = bl ? codeword.getEndX() : codeword.getStartX();
                return n2 + n3 * n * (codeword.getEndX() - codeword.getStartX());
            }
            ++n;
            n2 = n4;
        }
        if (bl) {
            return detectionResult.getBoundingBox().getMinX();
        }
        return detectionResult.getBoundingBox().getMaxX();
    }

    private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int n) {
        if (n >= 0 && n <= detectionResult.getBarcodeColumnCount() + 1) {
            return true;
        }
        return false;
    }

    private static DetectionResult merge(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) throws NotFoundException {
        if (detectionResultRowIndicatorColumn == null && detectionResultRowIndicatorColumn2 == null) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = PDF417ScanningDecoder.getBarcodeMetadata(detectionResultRowIndicatorColumn, detectionResultRowIndicatorColumn2);
        if (barcodeMetadata == null) {
            return null;
        }
        return new DetectionResult(barcodeMetadata, BoundingBox.merge(PDF417ScanningDecoder.adjustBoundingBox(detectionResultRowIndicatorColumn), PDF417ScanningDecoder.adjustBoundingBox(detectionResultRowIndicatorColumn2)));
    }

    public static String toString(BarcodeValue[][] object) {
        Formatter formatter = new Formatter();
        for (int i = 0; i < ((BarcodeValue[][])object).length; ++i) {
            formatter.format("Row %2d: ", i);
            for (int j = 0; j < object[i].length; ++j) {
                BarcodeValue barcodeValue = object[i][j];
                if (barcodeValue.getValue().length == 0) {
                    formatter.format("        ", null);
                    continue;
                }
                formatter.format("%4d(%2d)", barcodeValue.getValue()[0], barcodeValue.getConfidence(barcodeValue.getValue()[0]));
            }
            formatter.format("%n", new Object[0]);
        }
        object = formatter.toString();
        formatter.close();
        return object;
    }

    private static void verifyCodewordCount(int[] arrn, int n) throws FormatException {
        if (arrn.length < 4) {
            throw FormatException.getFormatInstance();
        }
        int n2 = arrn[0];
        if (n2 > arrn.length) {
            throw FormatException.getFormatInstance();
        }
        if (n2 == 0) {
            if (n < arrn.length) {
                arrn[0] = arrn.length - n;
                return;
            }
            throw FormatException.getFormatInstance();
        }
    }
}
