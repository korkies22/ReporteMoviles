/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class RGBLuminanceSource
extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final byte[] luminances;
    private final int top;

    public RGBLuminanceSource(int n, int n2, int[] arrn) {
        super(n, n2);
        this.dataWidth = n;
        this.dataHeight = n2;
        int n3 = 0;
        this.left = 0;
        this.top = 0;
        n2 = n * n2;
        this.luminances = new byte[n2];
        for (n = n3; n < n2; ++n) {
            n3 = arrn[n];
            this.luminances[n] = (byte)(((n3 >> 16 & 255) + (n3 >> 7 & 510) + (n3 & 255)) / 4);
        }
    }

    private RGBLuminanceSource(byte[] arrby, int n, int n2, int n3, int n4, int n5, int n6) {
        super(n5, n6);
        if (n5 + n3 <= n && n6 + n4 <= n2) {
            this.luminances = arrby;
            this.dataWidth = n;
            this.dataHeight = n2;
            this.left = n3;
            this.top = n4;
            return;
        }
        throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }

    @Override
    public LuminanceSource crop(int n, int n2, int n3, int n4) {
        return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + n, this.top + n2, n3, n4);
    }

    @Override
    public byte[] getMatrix() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (n == this.dataWidth && n2 == this.dataHeight) {
            return this.luminances;
        }
        int n3 = n * n2;
        byte[] arrby = new byte[n3];
        int n4 = this.top * this.dataWidth + this.left;
        int n5 = this.dataWidth;
        int n6 = n4;
        if (n == n5) {
            System.arraycopy(this.luminances, n4, arrby, 0, n3);
            return arrby;
        }
        for (int i = 0; i < n2; ++i) {
            System.arraycopy(this.luminances, n6, arrby, i * n, n);
            n6 += this.dataWidth;
        }
        return arrby;
    }

    @Override
    public byte[] getRow(int n, byte[] object) {
        block2 : {
            int n2;
            byte[] arrby;
            block4 : {
                block3 : {
                    if (n < 0 || n >= this.getHeight()) break block2;
                    n2 = this.getWidth();
                    if (object == null) break block3;
                    arrby = object;
                    if (((Object)object).length >= n2) break block4;
                }
                arrby = new byte[n2];
            }
            int n3 = this.top;
            int n4 = this.dataWidth;
            int n5 = this.left;
            System.arraycopy(this.luminances, (n + n3) * n4 + n5, arrby, 0, n2);
            return arrby;
        }
        object = new StringBuilder("Requested row is outside the image: ");
        object.append(n);
        throw new IllegalArgumentException(object.toString());
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }
}
