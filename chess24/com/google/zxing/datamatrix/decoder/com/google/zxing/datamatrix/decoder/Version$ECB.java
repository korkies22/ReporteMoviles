/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

static final class Version.ECB {
    private final int count;
    private final int dataCodewords;

    private Version.ECB(int n, int n2) {
        this.count = n;
        this.dataCodewords = n2;
    }

    int getCount() {
        return this.count;
    }

    int getDataCodewords() {
        return this.dataCodewords;
    }
}
