/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version;

public static final class Version.ECB {
    private final int count;
    private final int dataCodewords;

    Version.ECB(int n, int n2) {
        this.count = n;
        this.dataCodewords = n2;
    }

    public int getCount() {
        return this.count;
    }

    public int getDataCodewords() {
        return this.dataCodewords;
    }
}
