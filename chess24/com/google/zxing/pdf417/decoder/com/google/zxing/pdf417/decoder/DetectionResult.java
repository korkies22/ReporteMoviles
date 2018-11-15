/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;
import com.google.zxing.pdf417.decoder.DetectionResultRowIndicatorColumn;
import java.util.Formatter;

final class DetectionResult {
    private static final int ADJUST_ROW_NUMBER_SKIP = 2;
    private final int barcodeColumnCount;
    private final BarcodeMetadata barcodeMetadata;
    private BoundingBox boundingBox;
    private final DetectionResultColumn[] detectionResultColumns;

    DetectionResult(BarcodeMetadata barcodeMetadata, BoundingBox boundingBox) {
        this.barcodeMetadata = barcodeMetadata;
        this.barcodeColumnCount = barcodeMetadata.getColumnCount();
        this.boundingBox = boundingBox;
        this.detectionResultColumns = new DetectionResultColumn[this.barcodeColumnCount + 2];
    }

    private void adjustIndicatorColumnRowNumbers(DetectionResultColumn detectionResultColumn) {
        if (detectionResultColumn != null) {
            ((DetectionResultRowIndicatorColumn)detectionResultColumn).adjustCompleteIndicatorColumnRowNumbers(this.barcodeMetadata);
        }
    }

    private static boolean adjustRowNumber(Codeword codeword, Codeword codeword2) {
        if (codeword2 == null) {
            return false;
        }
        if (codeword2.hasValidRowNumber() && codeword2.getBucket() == codeword.getBucket()) {
            codeword.setRowNumber(codeword2.getRowNumber());
            return true;
        }
        return false;
    }

    private static int adjustRowNumberIfValid(int n, int n2, Codeword codeword) {
        if (codeword == null) {
            return n2;
        }
        int n3 = n2;
        if (!codeword.hasValidRowNumber()) {
            if (codeword.isValidRowNumber(n)) {
                codeword.setRowNumber(n);
                return 0;
            }
            n3 = n2 + 1;
        }
        return n3;
    }

    private int adjustRowNumbers() {
        int n = this.adjustRowNumbersByRow();
        if (n == 0) {
            return 0;
        }
        for (int i = 1; i < this.barcodeColumnCount + 1; ++i) {
            Codeword[] arrcodeword = this.detectionResultColumns[i].getCodewords();
            for (int j = 0; j < arrcodeword.length; ++j) {
                if (arrcodeword[j] == null || arrcodeword[j].hasValidRowNumber()) continue;
                this.adjustRowNumbers(i, j, arrcodeword);
            }
        }
        return n;
    }

    private void adjustRowNumbers(int n, int n2, Codeword[] arrcodeword) {
        Codeword codeword = arrcodeword[n2];
        Codeword[] arrcodeword2 = this.detectionResultColumns[n - 1].getCodewords();
        Object[] arrobject = this.detectionResultColumns;
        arrobject = arrobject[++n] != null ? this.detectionResultColumns[n].getCodewords() : arrcodeword2;
        Codeword[] arrcodeword3 = new Codeword[14];
        arrcodeword3[2] = arrcodeword2[n2];
        arrcodeword3[3] = arrobject[n2];
        int n3 = 0;
        if (n2 > 0) {
            n = n2 - 1;
            arrcodeword3[0] = arrcodeword[n];
            arrcodeword3[4] = arrcodeword2[n];
            arrcodeword3[5] = arrobject[n];
        }
        if (n2 > 1) {
            n = n2 - 2;
            arrcodeword3[8] = arrcodeword[n];
            arrcodeword3[10] = arrcodeword2[n];
            arrcodeword3[11] = arrobject[n];
        }
        if (n2 < arrcodeword.length - 1) {
            n = n2 + 1;
            arrcodeword3[1] = arrcodeword[n];
            arrcodeword3[6] = arrcodeword2[n];
            arrcodeword3[7] = arrobject[n];
        }
        n = n3;
        if (n2 < arrcodeword.length - 2) {
            n = n2 + 2;
            arrcodeword3[9] = arrcodeword[n];
            arrcodeword3[12] = arrcodeword2[n];
            arrcodeword3[13] = arrobject[n];
            n = n3;
        }
        while (n < 14) {
            if (DetectionResult.adjustRowNumber(codeword, arrcodeword3[n])) {
                return;
            }
            ++n;
        }
    }

    private int adjustRowNumbersByRow() {
        this.adjustRowNumbersFromBothRI();
        return this.adjustRowNumbersFromLRI() + this.adjustRowNumbersFromRRI();
    }

    private void adjustRowNumbersFromBothRI() {
        Object[] arrobject = this.detectionResultColumns;
        if (arrobject[0] != null) {
            if (this.detectionResultColumns[this.barcodeColumnCount + 1] == null) {
                return;
            }
            arrobject = this.detectionResultColumns[0].getCodewords();
            Codeword[] arrcodeword = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords();
            for (int i = 0; i < arrobject.length; ++i) {
                if (arrobject[i] == null || arrcodeword[i] == null || arrobject[i].getRowNumber() != arrcodeword[i].getRowNumber()) continue;
                for (int j = 1; j <= this.barcodeColumnCount; ++j) {
                    Codeword codeword = this.detectionResultColumns[j].getCodewords()[i];
                    if (codeword == null) continue;
                    codeword.setRowNumber(arrobject[i].getRowNumber());
                    if (codeword.hasValidRowNumber()) continue;
                    this.detectionResultColumns[j].getCodewords()[i] = null;
                }
            }
            return;
        }
    }

    private int adjustRowNumbersFromLRI() {
        int n;
        if (this.detectionResultColumns[0] == null) {
            return 0;
        }
        Codeword[] arrcodeword = this.detectionResultColumns[0].getCodewords();
        int n2 = n = 0;
        while (n < arrcodeword.length) {
            int n3 = n2;
            if (arrcodeword[n] != null) {
                int n4 = arrcodeword[n].getRowNumber();
                int n5 = 0;
                for (n3 = 1; n3 < this.barcodeColumnCount + 1 && n5 < 2; ++n3) {
                    Codeword codeword = this.detectionResultColumns[n3].getCodewords()[n];
                    int n6 = n5;
                    int n7 = n2;
                    if (codeword != null) {
                        n6 = n5 = DetectionResult.adjustRowNumberIfValid(n4, n5, codeword);
                        n7 = n2;
                        if (!codeword.hasValidRowNumber()) {
                            n7 = n2 + 1;
                            n6 = n5;
                        }
                    }
                    n5 = n6;
                    n2 = n7;
                }
                n3 = n2;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    private int adjustRowNumbersFromRRI() {
        int n;
        if (this.detectionResultColumns[this.barcodeColumnCount + 1] == null) {
            return 0;
        }
        Codeword[] arrcodeword = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords();
        int n2 = n = 0;
        while (n < arrcodeword.length) {
            int n3 = n2;
            if (arrcodeword[n] != null) {
                int n4 = arrcodeword[n].getRowNumber();
                int n5 = 0;
                for (n3 = this.barcodeColumnCount + 1; n3 > 0 && n5 < 2; --n3) {
                    Codeword codeword = this.detectionResultColumns[n3].getCodewords()[n];
                    int n6 = n5;
                    int n7 = n2;
                    if (codeword != null) {
                        n6 = n5 = DetectionResult.adjustRowNumberIfValid(n4, n5, codeword);
                        n7 = n2;
                        if (!codeword.hasValidRowNumber()) {
                            n7 = n2 + 1;
                            n6 = n5;
                        }
                    }
                    n5 = n6;
                    n2 = n7;
                }
                n3 = n2;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    int getBarcodeColumnCount() {
        return this.barcodeColumnCount;
    }

    int getBarcodeECLevel() {
        return this.barcodeMetadata.getErrorCorrectionLevel();
    }

    int getBarcodeRowCount() {
        return this.barcodeMetadata.getRowCount();
    }

    BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    DetectionResultColumn getDetectionResultColumn(int n) {
        return this.detectionResultColumns[n];
    }

    DetectionResultColumn[] getDetectionResultColumns() {
        int n;
        this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[0]);
        this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[this.barcodeColumnCount + 1]);
        int n2 = 928;
        while ((n = this.adjustRowNumbers()) > 0 && n < n2) {
            n2 = n;
        }
        return this.detectionResultColumns;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    void setDetectionResultColumn(int n, DetectionResultColumn detectionResultColumn) {
        this.detectionResultColumns[n] = detectionResultColumn;
    }

    public String toString() {
        Object object;
        Object object2 = object = this.detectionResultColumns[0];
        if (object == null) {
            object2 = this.detectionResultColumns[this.barcodeColumnCount + 1];
        }
        object = new Formatter();
        for (int i = 0; i < object2.getCodewords().length; ++i) {
            object.format("CW %3d:", i);
            for (int j = 0; j < this.barcodeColumnCount + 2; ++j) {
                if (this.detectionResultColumns[j] == null) {
                    object.format("    |   ", new Object[0]);
                    continue;
                }
                Codeword codeword = this.detectionResultColumns[j].getCodewords()[i];
                if (codeword == null) {
                    object.format("    |   ", new Object[0]);
                    continue;
                }
                object.format(" %3d|%3d", codeword.getRowNumber(), codeword.getValue());
            }
            object.format("%n", new Object[0]);
        }
        object2 = object.toString();
        object.close();
        return object2;
    }
}
