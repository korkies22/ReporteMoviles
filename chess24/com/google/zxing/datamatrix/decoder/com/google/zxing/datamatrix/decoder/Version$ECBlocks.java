/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

static final class Version.ECBlocks {
    private final Version.ECB[] ecBlocks;
    private final int ecCodewords;

    private Version.ECBlocks(int n, Version.ECB eCB) {
        this.ecCodewords = n;
        this.ecBlocks = new Version.ECB[]{eCB};
    }

    private Version.ECBlocks(int n, Version.ECB eCB, Version.ECB eCB2) {
        this.ecCodewords = n;
        this.ecBlocks = new Version.ECB[]{eCB, eCB2};
    }

    Version.ECB[] getECBlocks() {
        return this.ecBlocks;
    }

    int getECCodewords() {
        return this.ecCodewords;
    }
}
