/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.QRCode;

final class MatrixUtil {
    private static final int[][] POSITION_ADJUSTMENT_PATTERN;
    private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
    private static final int[][] POSITION_DETECTION_PATTERN;
    private static final int[][] TYPE_INFO_COORDINATES;
    private static final int TYPE_INFO_MASK_PATTERN = 21522;
    private static final int TYPE_INFO_POLY = 1335;
    private static final int VERSION_INFO_POLY = 7973;

    static {
        int[] arrn = new int[]{1, 0, 0, 0, 0, 0, 1};
        POSITION_DETECTION_PATTERN = new int[][]{{1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, arrn, {1, 1, 1, 1, 1, 1, 1}};
        arrn = new int[]{1, 0, 0, 0, 1};
        int[] arrn2 = new int[]{1, 0, 0, 0, 1};
        int[] arrn3 = new int[]{1, 1, 1, 1, 1};
        POSITION_ADJUSTMENT_PATTERN = new int[][]{{1, 1, 1, 1, 1}, arrn, {1, 0, 1, 0, 1}, arrn2, arrn3};
        arrn = new int[]{-1, -1, -1, -1, -1, -1, -1};
        arrn2 = new int[]{6, 18, -1, -1, -1, -1, -1};
        arrn3 = new int[]{6, 22, -1, -1, -1, -1, -1};
        int[] arrn4 = new int[]{6, 26, -1, -1, -1, -1, -1};
        int[] arrn5 = new int[]{6, 30, -1, -1, -1, -1, -1};
        int[] arrn6 = new int[]{6, 22, 38, -1, -1, -1, -1};
        int[] arrn7 = new int[]{6, 24, 42, -1, -1, -1, -1};
        int[] arrn8 = new int[]{6, 26, 46, -1, -1, -1, -1};
        int[] arrn9 = new int[]{6, 28, 50, -1, -1, -1, -1};
        int[] arrn10 = new int[]{6, 30, 54, -1, -1, -1, -1};
        int[] arrn11 = new int[]{6, 34, 62, -1, -1, -1, -1};
        int[] arrn12 = new int[]{6, 26, 46, 66, -1, -1, -1};
        int[] arrn13 = new int[]{6, 26, 48, 70, -1, -1, -1};
        int[] arrn14 = new int[]{6, 26, 50, 74, -1, -1, -1};
        int[] arrn15 = new int[]{6, 30, 54, 78, -1, -1, -1};
        int[] arrn16 = new int[]{6, 30, 56, 82, -1, -1, -1};
        int[] arrn17 = new int[]{6, 34, 62, 90, -1, -1, -1};
        int[] arrn18 = new int[]{6, 28, 50, 72, 94, -1, -1};
        int[] arrn19 = new int[]{6, 26, 50, 74, 98, -1, -1};
        int[] arrn20 = new int[]{6, 34, 62, 90, 118, -1, -1};
        int[] arrn21 = new int[]{6, 30, 54, 78, 102, 126, -1};
        int[] arrn22 = new int[]{6, 30, 54, 78, 102, 126, 150};
        int[] arrn23 = new int[]{6, 24, 50, 76, 102, 128, 154};
        int[] arrn24 = new int[]{6, 28, 54, 80, 106, 132, 158};
        int[] arrn25 = new int[]{6, 26, 54, 82, 110, 138, 166};
        int[] arrn26 = new int[]{6, 30, 58, 86, 114, 142, 170};
        POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][]{arrn, arrn2, arrn3, arrn4, arrn5, {6, 34, -1, -1, -1, -1, -1}, arrn6, arrn7, arrn8, arrn9, arrn10, {6, 32, 58, -1, -1, -1, -1}, arrn11, arrn12, arrn13, arrn14, arrn15, arrn16, {6, 30, 58, 86, -1, -1, -1}, arrn17, arrn18, arrn19, {6, 30, 54, 78, 102, -1, -1}, {6, 28, 54, 80, 106, -1, -1}, {6, 32, 58, 84, 110, -1, -1}, {6, 30, 58, 86, 114, -1, -1}, arrn20, {6, 26, 50, 74, 98, 122, -1}, arrn21, {6, 26, 52, 78, 104, 130, -1}, {6, 30, 56, 82, 108, 134, -1}, {6, 34, 60, 86, 112, 138, -1}, {6, 30, 58, 86, 114, 142, -1}, {6, 34, 62, 90, 118, 146, -1}, arrn22, arrn23, arrn24, {6, 32, 58, 84, 110, 136, 162}, arrn25, arrn26};
        arrn = new int[]{8, 1};
        arrn2 = new int[]{8, 4};
        arrn3 = new int[]{8, 5};
        arrn4 = new int[]{8, 8};
        arrn5 = new int[]{5, 8};
        arrn6 = new int[]{4, 8};
        arrn7 = new int[]{3, 8};
        arrn8 = new int[]{2, 8};
        arrn9 = new int[]{0, 8};
        TYPE_INFO_COORDINATES = new int[][]{{8, 0}, arrn, {8, 2}, {8, 3}, arrn2, arrn3, {8, 7}, arrn4, {7, 8}, arrn5, arrn6, arrn7, arrn8, {1, 8}, arrn9};
    }

    private MatrixUtil() {
    }

    static void buildMatrix(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, int n, ByteMatrix byteMatrix) throws WriterException {
        MatrixUtil.clearMatrix(byteMatrix);
        MatrixUtil.embedBasicPatterns(version, byteMatrix);
        MatrixUtil.embedTypeInfo(errorCorrectionLevel, n, byteMatrix);
        MatrixUtil.maybeEmbedVersionInfo(version, byteMatrix);
        MatrixUtil.embedDataBits(bitArray, n, byteMatrix);
    }

    static int calculateBCHCode(int n, int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("0 polynomial");
        }
        int n3 = MatrixUtil.findMSBSet(n2);
        n <<= n3 - 1;
        while (MatrixUtil.findMSBSet(n) >= n3) {
            n ^= n2 << MatrixUtil.findMSBSet(n) - n3;
        }
        return n;
    }

    static void clearMatrix(ByteMatrix byteMatrix) {
        byteMatrix.clear((byte)-1);
    }

    static void embedBasicPatterns(Version version, ByteMatrix byteMatrix) throws WriterException {
        MatrixUtil.embedPositionDetectionPatternsAndSeparators(byteMatrix);
        MatrixUtil.embedDarkDotAtLeftBottomCorner(byteMatrix);
        MatrixUtil.maybeEmbedPositionAdjustmentPatterns(version, byteMatrix);
        MatrixUtil.embedTimingPatterns(byteMatrix);
    }

    private static void embedDarkDotAtLeftBottomCorner(ByteMatrix byteMatrix) throws WriterException {
        if (byteMatrix.get(8, byteMatrix.getHeight() - 8) == 0) {
            throw new WriterException();
        }
        byteMatrix.set(8, byteMatrix.getHeight() - 8, 1);
    }

    static void embedDataBits(BitArray bitArray, int n, ByteMatrix object) throws WriterException {
        int n2 = object.getWidth() - 1;
        int n3 = object.getHeight() - 1;
        int n4 = -1;
        int n5 = 0;
        while (n2 > 0) {
            int n6 = n2;
            int n7 = n5;
            int n8 = n3;
            if (n2 == 6) {
                n6 = n2 - 1;
                n8 = n3;
                n7 = n5;
            }
            while (n8 >= 0 && n8 < object.getHeight()) {
                for (n3 = 0; n3 < 2; ++n3) {
                    n5 = n6 - n3;
                    n2 = n7;
                    if (MatrixUtil.isEmpty(object.get(n5, n8))) {
                        boolean bl;
                        if (n7 < bitArray.getSize()) {
                            bl = bitArray.get(n7);
                            ++n7;
                        } else {
                            bl = false;
                        }
                        boolean bl2 = bl;
                        if (n != -1) {
                            bl2 = bl;
                            if (MaskUtil.getDataMaskBit(n, n5, n8)) {
                                bl2 = !bl;
                            }
                        }
                        object.set(n5, n8, bl2);
                        n2 = n7;
                    }
                    n7 = n2;
                }
                n8 += n4;
            }
            n4 = - n4;
            n3 = n8 + n4;
            n2 = n6 - 2;
            n5 = n7;
        }
        if (n5 != bitArray.getSize()) {
            object = new StringBuilder("Not all bits consumed: ");
            object.append(n5);
            object.append('/');
            object.append(bitArray.getSize());
            throw new WriterException(object.toString());
        }
    }

    private static void embedHorizontalSeparationPattern(int n, int n2, ByteMatrix byteMatrix) throws WriterException {
        for (int i = 0; i < 8; ++i) {
            int n3 = n + i;
            if (!MatrixUtil.isEmpty(byteMatrix.get(n3, n2))) {
                throw new WriterException();
            }
            byteMatrix.set(n3, n2, 0);
        }
    }

    private static void embedPositionAdjustmentPattern(int n, int n2, ByteMatrix byteMatrix) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                byteMatrix.set(n + j, n2 + i, POSITION_ADJUSTMENT_PATTERN[i][j]);
            }
        }
    }

    private static void embedPositionDetectionPattern(int n, int n2, ByteMatrix byteMatrix) {
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                byteMatrix.set(n + j, n2 + i, POSITION_DETECTION_PATTERN[i][j]);
            }
        }
    }

    private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix byteMatrix) throws WriterException {
        int n = POSITION_DETECTION_PATTERN[0].length;
        MatrixUtil.embedPositionDetectionPattern(0, 0, byteMatrix);
        MatrixUtil.embedPositionDetectionPattern(byteMatrix.getWidth() - n, 0, byteMatrix);
        MatrixUtil.embedPositionDetectionPattern(0, byteMatrix.getWidth() - n, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(0, 7, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(byteMatrix.getWidth() - 8, 7, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(0, byteMatrix.getWidth() - 8, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(7, 0, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(byteMatrix.getHeight() - 7 - 1, 0, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(7, byteMatrix.getHeight() - 7, byteMatrix);
    }

    private static void embedTimingPatterns(ByteMatrix byteMatrix) {
        int n = 8;
        while (n < byteMatrix.getWidth() - 8) {
            int n2 = n + 1;
            int n3 = n2 % 2;
            if (MatrixUtil.isEmpty(byteMatrix.get(n, 6))) {
                byteMatrix.set(n, 6, n3);
            }
            if (MatrixUtil.isEmpty(byteMatrix.get(6, n))) {
                byteMatrix.set(6, n, n3);
            }
            n = n2;
        }
    }

    static void embedTypeInfo(ErrorCorrectionLevel errorCorrectionLevel, int n, ByteMatrix byteMatrix) throws WriterException {
        BitArray bitArray = new BitArray();
        MatrixUtil.makeTypeInfoBits(errorCorrectionLevel, n, bitArray);
        for (n = 0; n < bitArray.getSize(); ++n) {
            boolean bl = bitArray.get(bitArray.getSize() - 1 - n);
            byteMatrix.set(TYPE_INFO_COORDINATES[n][0], TYPE_INFO_COORDINATES[n][1], bl);
            if (n < 8) {
                byteMatrix.set(byteMatrix.getWidth() - n - 1, 8, bl);
                continue;
            }
            byteMatrix.set(8, byteMatrix.getHeight() - 7 + (n - 8), bl);
        }
    }

    private static void embedVerticalSeparationPattern(int n, int n2, ByteMatrix byteMatrix) throws WriterException {
        for (int i = 0; i < 7; ++i) {
            int n3 = n2 + i;
            if (!MatrixUtil.isEmpty(byteMatrix.get(n, n3))) {
                throw new WriterException();
            }
            byteMatrix.set(n, n3, 0);
        }
    }

    static int findMSBSet(int n) {
        return 32 - Integer.numberOfLeadingZeros(n);
    }

    private static boolean isEmpty(int n) {
        if (n == -1) {
            return true;
        }
        return false;
    }

    static void makeTypeInfoBits(ErrorCorrectionLevel object, int n, BitArray bitArray) throws WriterException {
        if (!QRCode.isValidMaskPattern(n)) {
            throw new WriterException("Invalid mask pattern");
        }
        n = object.getBits() << 3 | n;
        bitArray.appendBits(n, 5);
        bitArray.appendBits(MatrixUtil.calculateBCHCode(n, 1335), 10);
        object = new BitArray();
        object.appendBits(21522, 15);
        bitArray.xor((BitArray)object);
        if (bitArray.getSize() != 15) {
            object = new StringBuilder("should not happen but we got: ");
            object.append(bitArray.getSize());
            throw new WriterException(object.toString());
        }
    }

    static void makeVersionInfoBits(Version object, BitArray bitArray) throws WriterException {
        bitArray.appendBits(object.getVersionNumber(), 6);
        bitArray.appendBits(MatrixUtil.calculateBCHCode(object.getVersionNumber(), 7973), 12);
        if (bitArray.getSize() != 18) {
            object = new StringBuilder("should not happen but we got: ");
            object.append(bitArray.getSize());
            throw new WriterException(object.toString());
        }
    }

    private static void maybeEmbedPositionAdjustmentPatterns(Version arrn, ByteMatrix byteMatrix) {
        if (arrn.getVersionNumber() < 2) {
            return;
        }
        int n = arrn.getVersionNumber() - 1;
        arrn = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[n];
        int n2 = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[n].length;
        for (n = 0; n < n2; ++n) {
            for (int i = 0; i < n2; ++i) {
                int n3 = arrn[n];
                int n4 = arrn[i];
                if (n4 == -1 || n3 == -1 || !MatrixUtil.isEmpty(byteMatrix.get(n4, n3))) continue;
                MatrixUtil.embedPositionAdjustmentPattern(n4 - 2, n3 - 2, byteMatrix);
            }
        }
    }

    static void maybeEmbedVersionInfo(Version version, ByteMatrix byteMatrix) throws WriterException {
        if (version.getVersionNumber() < 7) {
            return;
        }
        BitArray bitArray = new BitArray();
        MatrixUtil.makeVersionInfoBits(version, bitArray);
        int n = 17;
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 3; ++j) {
                boolean bl = bitArray.get(n);
                --n;
                byteMatrix.set(i, byteMatrix.getHeight() - 11 + j, bl);
                byteMatrix.set(byteMatrix.getHeight() - 11 + j, i, bl);
            }
        }
    }
}
