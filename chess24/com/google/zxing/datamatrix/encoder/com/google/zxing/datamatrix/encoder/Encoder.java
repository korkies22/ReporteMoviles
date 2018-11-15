/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.EncoderContext;

interface Encoder {
    public void encode(EncoderContext var1);

    public int getEncodingMode();
}
