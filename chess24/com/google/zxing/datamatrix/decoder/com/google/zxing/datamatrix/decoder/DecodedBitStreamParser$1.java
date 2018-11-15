/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.DecodedBitStreamParser;

static class DecodedBitStreamParser {
    static final /* synthetic */ int[] $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode = new int[DecodedBitStreamParser.Mode.values().length];
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.C40_ENCODE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.TEXT_ENCODE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.ANSIX12_ENCODE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.EDIFACT_ENCODE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DecodedBitStreamParser.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[DecodedBitStreamParser.Mode.BASE256_ENCODE.ordinal()] = 5;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
