/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;

final class DecodedInformation
extends DecodedObject {
    private final String newString;
    private final boolean remaining;
    private final int remainingValue;

    DecodedInformation(int n, String string) {
        super(n);
        this.newString = string;
        this.remaining = false;
        this.remainingValue = 0;
    }

    DecodedInformation(int n, String string, int n2) {
        super(n);
        this.remaining = true;
        this.remainingValue = n2;
        this.newString = string;
    }

    String getNewString() {
        return this.newString;
    }

    int getRemainingValue() {
        return this.remainingValue;
    }

    boolean isRemaining() {
        return this.remaining;
    }
}
