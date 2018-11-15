/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.decoder;

import com.google.zxing.aztec.decoder.Decoder;

private static enum Decoder.Table {
    UPPER,
    LOWER,
    MIXED,
    DIGIT,
    PUNCT,
    BINARY;
    

    private Decoder.Table() {
    }
}
