/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.DecodedBitStreamParser;

static class DecodedBitStreamParser {
    static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = new int[DecodedBitStreamParser.Mode.values().length];
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.ALPHA.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.LOWER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.MIXED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.PUNCT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.ALPHA_SHIFT.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.PUNCT_SHIFT.ordinal()] = 6;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
