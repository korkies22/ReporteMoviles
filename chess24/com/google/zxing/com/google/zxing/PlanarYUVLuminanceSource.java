/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class PlanarYUVLuminanceSource
extends LuminanceSource {
    private static final int THUMBNAIL_SCALE_FACTOR = 2;
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final int top;
    private final byte[] yuvData;

    public PlanarYUVLuminanceSource(byte[] arrby, int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        super(n5, n6);
        if (n3 + n5 <= n && n4 + n6 <= n2) {
            this.yuvData = arrby;
            this.dataWidth = n;
            this.dataHeight = n2;
            this.left = n3;
            this.top = n4;
            if (bl) {
                this.reverseHorizontal(n5, n6);
            }
            return;
        }
        throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }

    private void reverseHorizontal(int n, int n2) {
        byte[] arrby = this.yuvData;
        int n3 = this.top * this.dataWidth + this.left;
        int n4 = 0;
        while (n4 < n2) {
            int n5 = n / 2;
            int n6 = n3 + n - 1;
            int n7 = n3;
            while (n7 < n5 + n3) {
                byte by = arrby[n7];
                arrby[n7] = arrby[n6];
                arrby[n6] = by;
                ++n7;
                --n6;
            }
            ++n4;
            n3 += this.dataWidth;
        }
    }

    @Override
    public LuminanceSource crop(int n, int n2, int n3, int n4) {
        return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + n, this.top + n2, n3, n4, false);
    }

    @Override
    public byte[] getMatrix() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (n == this.dataWidth && n2 == this.dataHeight) {
            return this.yuvData;
        }
        int n3 = n * n2;
        byte[] arrby = new byte[n3];
        int n4 = this.top * this.dataWidth + this.left;
        int n5 = this.dataWidth;
        int n6 = n4;
        if (n == n5) {
            System.arraycopy(this.yuvData, n4, arrby, 0, n3);
            return arrby;
        }
        for (int i = 0; i < n2; ++i) {
            System.arraycopy(this.yuvData, n6, arrby, i * n, n);
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
            System.arraycopy(this.yuvData, (n + n3) * n4 + n5, arrby, 0, n2);
            return arrby;
        }
        object = new StringBuilder("Requested row is outside the image: ");
        object.append(n);
        throw new IllegalArgumentException(object.toString());
    }

    public int getThumbnailHeight() {
        return this.getHeight() / 2;
    }

    public int getThumbnailWidth() {
        return this.getWidth() / 2;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

    public int[] renderThumbnail() {
        int n = this.getWidth() / 2;
        int n2 = this.getHeight() / 2;
        int[] arrn = new int[n * n2];
        byte[] arrby = this.yuvData;
        int n3 = this.top * this.dataWidth + this.left;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                arrn[i * n + j] = (arrby[(j << 1) + n3] & 255) * 65793 | -16777216;
            }
            n3 += this.dataWidth << 1;
        }
        return arrn;
    }
}
