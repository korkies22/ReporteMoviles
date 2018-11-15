/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.DecodedBitStreamParser;

private static enum DecodedBitStreamParser.Mode {
    ALPHA,
    LOWER,
    MIXED,
    PUNCT,
    ALPHA_SHIFT,
    PUNCT_SHIFT;
    

    private DecodedBitStreamParser.Mode() {
    }
}
