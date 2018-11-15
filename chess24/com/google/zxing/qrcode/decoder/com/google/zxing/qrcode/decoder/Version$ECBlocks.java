/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version;

public static final class Version.ECBlocks {
    private final Version.ECB[] ecBlocks;
    private final int ecCodewordsPerBlock;

    /* varargs */ Version.ECBlocks(int n, Version.ECB ... arreCB) {
        this.ecCodewordsPerBlock = n;
        this.ecBlocks = arreCB;
    }

    public Version.ECB[] getECBlocks() {
        return this.ecBlocks;
    }

    public int getECCodewordsPerBlock() {
        return this.ecCodewordsPerBlock;
    }

    public int getNumBlocks() {
        Version.ECB[] arreCB = this.ecBlocks;
        int n = arreCB.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 += arreCB[i].getCount();
        }
        return n2;
    }

    public int getTotalECCodewords() {
        return this.ecCodewordsPerBlock * this.getNumBlocks();
    }
}
