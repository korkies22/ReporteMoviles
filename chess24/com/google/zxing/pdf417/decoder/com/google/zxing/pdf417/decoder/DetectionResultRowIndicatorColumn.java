/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.ResultPoint;
import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BarcodeValue;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;

final class DetectionResultRowIndicatorColumn
extends DetectionResultColumn {
    private final boolean isLeft;

    DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean bl) {
        super(boundingBox);
        this.isLeft = bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata var1_1) {
        var11_2 = this.getBoundingBox();
        if (this.isLeft) {
            var10_3 = var11_2.getTopLeft();
        } else {
            var10_4 = var11_2.getTopRight();
        }
        var11_2 = this.isLeft != false ? var11_2.getBottomLeft() : var11_2.getBottomRight();
        var4_7 = this.imageRowToCodewordIndex((int)var10_5.getY());
        var9_8 = this.imageRowToCodewordIndex((int)var11_2.getY());
        var10_6 = this.getCodewords();
        var6_9 = -1;
        var7_10 = 0;
        var2_11 = 1;
        while (var4_7 < var9_8) {
            block5 : {
                block7 : {
                    block6 : {
                        var5_13 = var6_9;
                        var3_12 = var7_10;
                        var8_14 = var2_11;
                        if (var10_6[var4_7] == null) break block5;
                        var11_2 = var10_6[var4_7];
                        var11_2.setRowNumberAsRowIndicatorColumn();
                        var3_12 = var11_2.getRowNumber() - var6_9;
                        if (var3_12 != 0) break block6;
                        var3_12 = var7_10 + 1;
                        var5_13 = var6_9;
                        var8_14 = var2_11;
                        break block5;
                    }
                    if (var3_12 != 1) break block7;
                    var2_11 = Math.max(var2_11, var7_10);
                    var3_12 = var11_2.getRowNumber();
                    ** GOTO lbl39
                }
                if (var11_2.getRowNumber() >= var1_1.getRowCount()) {
                    var10_6[var4_7] = null;
                    var5_13 = var6_9;
                    var3_12 = var7_10;
                    var8_14 = var2_11;
                } else {
                    var3_12 = var11_2.getRowNumber();
lbl39: // 2 sources:
                    var6_9 = 1;
                    var5_13 = var3_12;
                    var3_12 = var6_9;
                    var8_14 = var2_11;
                }
            }
            ++var4_7;
            var6_9 = var5_13;
            var7_10 = var3_12;
            var2_11 = var8_14;
        }
    }

    private void removeIncorrectCodewords(Codeword[] arrcodeword, BarcodeMetadata barcodeMetadata) {
        block5 : for (int i = 0; i < arrcodeword.length; ++i) {
            Codeword codeword = arrcodeword[i];
            if (arrcodeword[i] == null) continue;
            int n = codeword.getValue() % 30;
            int n2 = codeword.getRowNumber();
            if (n2 > barcodeMetadata.getRowCount()) {
                arrcodeword[i] = null;
                continue;
            }
            int n3 = n2;
            if (!this.isLeft) {
                n3 = n2 + 2;
            }
            switch (n3 % 3) {
                default: {
                    continue block5;
                }
                case 2: {
                    if (n + 1 == barcodeMetadata.getColumnCount()) continue block5;
                    arrcodeword[i] = null;
                    continue block5;
                }
                case 1: {
                    if (n / 3 == barcodeMetadata.getErrorCorrectionLevel() && n % 3 == barcodeMetadata.getRowCountLowerPart()) continue block5;
                    arrcodeword[i] = null;
                    continue block5;
                }
                case 0: {
                    if (n * 3 + 1 == barcodeMetadata.getRowCountUpperPart()) continue block5;
                    arrcodeword[i] = null;
                }
            }
        }
    }

    private void setRowNumbers() {
        for (Codeword codeword : this.getCodewords()) {
            if (codeword == null) continue;
            codeword.setRowNumberAsRowIndicatorColumn();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata var1_1) {
        var12_2 = this.getCodewords();
        this.setRowNumbers();
        this.removeIncorrectCodewords(var12_2, var1_1);
        var11_3 = this.getBoundingBox();
        var10_4 = this.isLeft != false ? var11_3.getTopLeft() : var11_3.getTopRight();
        var11_3 = this.isLeft != false ? var11_3.getBottomLeft() : var11_3.getBottomRight();
        var4_5 = this.imageRowToCodewordIndex((int)var10_4.getY());
        var9_6 = this.imageRowToCodewordIndex((int)var11_3.getY());
        var5_7 = -1;
        var6_8 = 0;
        var2_9 = 1;
        while (var4_5 < var9_6) {
            block6 : {
                block11 : {
                    block9 : {
                        block10 : {
                            block8 : {
                                block7 : {
                                    var7_11 = var5_7;
                                    var3_10 = var6_8;
                                    var8_12 = var2_9;
                                    if (var12_2[var4_5] == null) break block6;
                                    var10_4 = var12_2[var4_5];
                                    var3_10 = var10_4.getRowNumber() - var5_7;
                                    if (var3_10 != 0) break block7;
                                    var3_10 = var6_8 + 1;
                                    var7_11 = var5_7;
                                    var8_12 = var2_9;
                                    break block6;
                                }
                                if (var3_10 != 1) break block8;
                                var2_9 = Math.max(var2_9, var6_8);
                                var3_10 = var10_4.getRowNumber();
                                break block9;
                            }
                            if (var3_10 < 0 || var10_4.getRowNumber() >= var1_1.getRowCount() || var3_10 > var4_5) break block10;
                            var7_11 = var3_10;
                            if (var2_9 > 2) {
                                var7_11 = var3_10 * (var2_9 - 2);
                            }
                            var3_10 = var7_11 >= var4_5 ? 1 : 0;
                            break block11;
                        }
                        var12_2[var4_5] = null;
                        var8_12 = var2_9;
                        var3_10 = var6_8;
                        var7_11 = var5_7;
                        break block6;
                    }
lbl43: // 2 sources:
                    do {
                        var5_7 = 1;
                        var7_11 = var3_10;
                        var3_10 = var5_7;
                        var8_12 = var2_9;
                        break block6;
                        break;
                    } while (true);
                }
                for (var8_12 = 1; var8_12 <= var7_11 && var3_10 == 0; ++var8_12) {
                    var3_10 = var12_2[var4_5 - var8_12] != null ? 1 : 0;
                }
                if (var3_10 != 0) {
                    var12_2[var4_5] = null;
                    var7_11 = var5_7;
                    var3_10 = var6_8;
                    var8_12 = var2_9;
                } else {
                    var3_10 = var10_4.getRowNumber();
                    ** continue;
                }
            }
            ++var4_5;
            var5_7 = var7_11;
            var6_8 = var3_10;
            var2_9 = var8_12;
        }
    }

    BarcodeMetadata getBarcodeMetadata() {
        Codeword[] arrcodeword = this.getCodewords();
        Object object = new BarcodeValue();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        int n = arrcodeword.length;
        block5 : for (int i = 0; i < n; ++i) {
            int n2;
            Codeword codeword = arrcodeword[i];
            if (codeword == null) continue;
            codeword.setRowNumberAsRowIndicatorColumn();
            int n3 = codeword.getValue() % 30;
            int n4 = n2 = codeword.getRowNumber();
            if (!this.isLeft) {
                n4 = n2 + 2;
            }
            switch (n4 % 3) {
                default: {
                    continue block5;
                }
                case 2: {
                    object.setValue(n3 + 1);
                    continue block5;
                }
                case 1: {
                    barcodeValue3.setValue(n3 / 3);
                    barcodeValue2.setValue(n3 % 3);
                    continue block5;
                }
                case 0: {
                    barcodeValue.setValue(n3 * 3 + 1);
                }
            }
        }
        if (object.getValue().length != 0 && barcodeValue.getValue().length != 0 && barcodeValue2.getValue().length != 0 && barcodeValue3.getValue().length != 0 && object.getValue()[0] > 0 && barcodeValue.getValue()[0] + barcodeValue2.getValue()[0] >= 3 && barcodeValue.getValue()[0] + barcodeValue2.getValue()[0] <= 90) {
            object = new BarcodeMetadata(object.getValue()[0], barcodeValue.getValue()[0], barcodeValue2.getValue()[0], barcodeValue3.getValue()[0]);
            this.removeIncorrectCodewords(arrcodeword, (BarcodeMetadata)object);
            return object;
        }
        return null;
    }

    int[] getRowHeights() {
        int[] arrn = this.getBarcodeMetadata();
        if (arrn == null) {
            return null;
        }
        this.adjustIncompleteIndicatorColumnRowNumbers((BarcodeMetadata)arrn);
        arrn = new int[arrn.getRowCount()];
        for (Codeword codeword : this.getCodewords()) {
            int n;
            if (codeword == null || (n = codeword.getRowNumber()) >= arrn.length) continue;
            arrn[n] = arrn[n] + 1;
        }
        return arrn;
    }

    boolean isLeft() {
        return this.isLeft;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IsLeft: ");
        stringBuilder.append(this.isLeft);
        stringBuilder.append('\n');
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}
