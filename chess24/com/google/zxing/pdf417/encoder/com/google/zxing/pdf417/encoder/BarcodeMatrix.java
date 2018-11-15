/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.encoder;

import com.google.zxing.pdf417.encoder.BarcodeRow;
import java.lang.reflect.Array;

public final class BarcodeMatrix {
    private int currentRow;
    private final int height;
    private final BarcodeRow[] matrix;
    private final int width;

    BarcodeMatrix(int n, int n2) {
        BarcodeRow[] arrbarcodeRow = this.matrix = new BarcodeRow[n];
        int n3 = arrbarcodeRow.length;
        for (int i = 0; i < n3; ++i) {
            this.matrix[i] = new BarcodeRow((n2 + 4) * 17 + 1);
        }
        this.width = n2 * 17;
        this.height = n;
        this.currentRow = -1;
    }

    BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public byte[][] getMatrix() {
        return this.getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int n, int n2) {
        byte[][] arrby = (byte[][])Array.newInstance(Byte.TYPE, this.height * n2, this.width * n);
        int n3 = this.height * n2;
        for (int i = 0; i < n3; ++i) {
            arrby[n3 - i - 1] = this.matrix[i / n2].getScaledRow(n);
        }
        return arrby;
    }

    void set(int n, int n2, byte by) {
        this.matrix[n2].set(n, by);
    }

    void startRow() {
        ++this.currentRow;
    }
}
