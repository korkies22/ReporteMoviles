/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.oned.rss.expanded.decoders.CurrentParsingState;

private static enum CurrentParsingState.State {
    NUMERIC,
    ALPHA,
    ISO_IEC_646;
    

    private CurrentParsingState.State() {
    }
}
