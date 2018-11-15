/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

abstract class DecodedObject {
    private final int newPosition;

    DecodedObject(int n) {
        this.newPosition = n;
    }

    final int getNewPosition() {
        return this.newPosition;
    }
}
