/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.encoder;

final class BarcodeRow {
    private int currentLocation;
    private final byte[] row;

    BarcodeRow(int n) {
        this.row = new byte[n];
        this.currentLocation = 0;
    }

    private void set(int n, boolean bl) {
        this.row[n] = (byte)(bl ? 1 : 0);
    }

    void addBar(boolean bl, int n) {
        for (int i = 0; i < n; ++i) {
            int n2 = this.currentLocation;
            this.currentLocation = n2 + 1;
            this.set(n2, bl);
        }
    }

    byte[] getScaledRow(int n) {
        byte[] arrby = this.row;
        arrby = new byte[arrby.length * n];
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = this.row[i / n];
        }
        return arrby;
    }

    void set(int n, byte by) {
        this.row[n] = by;
    }
}
