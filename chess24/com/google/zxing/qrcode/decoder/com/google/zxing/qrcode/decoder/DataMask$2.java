/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

static final class DataMask
extends com.google.zxing.qrcode.decoder.DataMask {
    DataMask(String string2, int n2) {
    }

    @Override
    boolean isMasked(int n, int n2) {
        if ((n & 1) == 0) {
            return true;
        }
        return false;
    }
}
