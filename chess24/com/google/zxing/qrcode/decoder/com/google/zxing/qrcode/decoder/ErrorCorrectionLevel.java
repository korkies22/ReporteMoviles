/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

public enum ErrorCorrectionLevel {
    L(1),
    M(0),
    Q(3),
    H(2);
    
    private static final ErrorCorrectionLevel[] FOR_BITS = new ErrorCorrectionLevel[]{M, L, H, Q};
    private final int bits;

    private ErrorCorrectionLevel(int n2) {
        this.bits = n2;
    }

    public static ErrorCorrectionLevel forBits(int n) {
        if (n >= 0 && n < FOR_BITS.length) {
            return FOR_BITS[n];
        }
        throw new IllegalArgumentException();
    }

    public int getBits() {
        return this.bits;
    }
}
