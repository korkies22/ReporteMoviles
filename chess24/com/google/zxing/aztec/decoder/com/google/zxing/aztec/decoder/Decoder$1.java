/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.decoder;

import com.google.zxing.aztec.decoder.Decoder;

static class Decoder {
    static final /* synthetic */ int[] $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table = new int[Decoder.Table.values().length];
        try {
            Decoder.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Decoder.Table.UPPER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Decoder.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Decoder.Table.LOWER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Decoder.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Decoder.Table.MIXED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Decoder.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Decoder.Table.PUNCT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Decoder.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[Decoder.Table.DIGIT.ordinal()] = 5;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
