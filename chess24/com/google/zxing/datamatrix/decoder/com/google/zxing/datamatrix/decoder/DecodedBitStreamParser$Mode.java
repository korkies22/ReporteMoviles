/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.DecodedBitStreamParser;

private static enum DecodedBitStreamParser.Mode {
    PAD_ENCODE,
    ASCII_ENCODE,
    C40_ENCODE,
    TEXT_ENCODE,
    ANSIX12_ENCODE,
    EDIFACT_ENCODE,
    BASE256_ENCODE;
    

    private DecodedBitStreamParser.Mode() {
    }
}
