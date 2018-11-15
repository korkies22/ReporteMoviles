/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class InvertedLuminanceSource
extends LuminanceSource {
    private final LuminanceSource delegate;

    public InvertedLuminanceSource(LuminanceSource luminanceSource) {
        super(luminanceSource.getWidth(), luminanceSource.getHeight());
        this.delegate = luminanceSource;
    }

    @Override
    public LuminanceSource crop(int n, int n2, int n3, int n4) {
        return new InvertedLuminanceSource(this.delegate.crop(n, n2, n3, n4));
    }

    @Override
    public byte[] getMatrix() {
        byte[] arrby = this.delegate.getMatrix();
        int n = this.getWidth() * this.getHeight();
        byte[] arrby2 = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby2[i] = (byte)(255 - (arrby[i] & 255));
        }
        return arrby2;
    }

    @Override
    public byte[] getRow(int n, byte[] arrby) {
        arrby = this.delegate.getRow(n, arrby);
        int n2 = this.getWidth();
        for (n = 0; n < n2; ++n) {
            arrby[n] = (byte)(255 - (arrby[n] & 255));
        }
        return arrby;
    }

    @Override
    public LuminanceSource invert() {
        return this.delegate;
    }

    @Override
    public boolean isCropSupported() {
        return this.delegate.isCropSupported();
    }

    @Override
    public boolean isRotateSupported() {
        return this.delegate.isRotateSupported();
    }

    @Override
    public LuminanceSource rotateCounterClockwise() {
        return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise());
    }

    @Override
    public LuminanceSource rotateCounterClockwise45() {
        return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise45());
    }
}
