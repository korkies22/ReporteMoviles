/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

final class BoundingBox {
    private ResultPoint bottomLeft;
    private ResultPoint bottomRight;
    private BitMatrix image;
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private ResultPoint topLeft;
    private ResultPoint topRight;

    BoundingBox(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        if (resultPoint == null && resultPoint3 == null || resultPoint2 == null && resultPoint4 == null || resultPoint != null && resultPoint2 == null || resultPoint3 != null && resultPoint4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.init(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
    }

    BoundingBox(BoundingBox boundingBox) {
        this.init(boundingBox.image, boundingBox.topLeft, boundingBox.bottomLeft, boundingBox.topRight, boundingBox.bottomRight);
    }

    private void calculateMinMaxValues() {
        if (this.topLeft == null) {
            this.topLeft = new ResultPoint(0.0f, this.topRight.getY());
            this.bottomLeft = new ResultPoint(0.0f, this.bottomRight.getY());
        } else if (this.topRight == null) {
            this.topRight = new ResultPoint(this.image.getWidth() - 1, this.topLeft.getY());
            this.bottomRight = new ResultPoint(this.image.getWidth() - 1, this.bottomLeft.getY());
        }
        this.minX = (int)Math.min(this.topLeft.getX(), this.bottomLeft.getX());
        this.maxX = (int)Math.max(this.topRight.getX(), this.bottomRight.getX());
        this.minY = (int)Math.min(this.topLeft.getY(), this.topRight.getY());
        this.maxY = (int)Math.max(this.bottomLeft.getY(), this.bottomRight.getY());
    }

    private void init(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        this.image = bitMatrix;
        this.topLeft = resultPoint;
        this.bottomLeft = resultPoint2;
        this.topRight = resultPoint3;
        this.bottomRight = resultPoint4;
        this.calculateMinMaxValues();
    }

    static BoundingBox merge(BoundingBox boundingBox, BoundingBox boundingBox2) throws NotFoundException {
        if (boundingBox == null) {
            return boundingBox2;
        }
        if (boundingBox2 == null) {
            return boundingBox;
        }
        return new BoundingBox(boundingBox.image, boundingBox.topLeft, boundingBox.bottomLeft, boundingBox2.topRight, boundingBox2.bottomRight);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    BoundingBox addMissingRows(int var1_1, int var2_2, boolean var3_3) throws NotFoundException {
        block6 : {
            block5 : {
                block4 : {
                    var5_4 = this.topLeft;
                    var6_5 = this.bottomLeft;
                    var10_6 = this.topRight;
                    var9_7 = this.bottomRight;
                    if (var1_1 <= 0) ** GOTO lbl15
                    var7_8 = var3_3 != false ? this.topLeft : this.topRight;
                    var1_1 = var4_9 = (int)var7_8.getY() - var1_1;
                    if (var4_9 < 0) {
                        var1_1 = 0;
                    }
                    var8_10 = new ResultPoint(var7_8.getX(), var1_1);
                    if (!var3_3) {
                        var7_8 = var5_4;
                    } else {
                        var5_4 = var8_10;
lbl15: // 2 sources:
                        var8_10 = var10_6;
                        var7_8 = var5_4;
                    }
                    if (var2_2 <= 0) break block4;
                    var5_4 = var3_3 != false ? this.bottomLeft : this.bottomRight;
                    var1_1 = var2_2 = (int)var5_4.getY() + var2_2;
                    if (var2_2 >= this.image.getHeight()) {
                        var1_1 = this.image.getHeight() - 1;
                    }
                    var5_4 = new ResultPoint(var5_4.getX(), var1_1);
                    if (var3_3) break block5;
                    var9_7 = var5_4;
                    break block6;
                }
                var5_4 = var6_5;
            }
            var6_5 = var5_4;
        }
        this.calculateMinMaxValues();
        return new BoundingBox(this.image, var7_8, var6_5, var8_10, var9_7);
    }

    ResultPoint getBottomLeft() {
        return this.bottomLeft;
    }

    ResultPoint getBottomRight() {
        return this.bottomRight;
    }

    int getMaxX() {
        return this.maxX;
    }

    int getMaxY() {
        return this.maxY;
    }

    int getMinX() {
        return this.minX;
    }

    int getMinY() {
        return this.minY;
    }

    ResultPoint getTopLeft() {
        return this.topLeft;
    }

    ResultPoint getTopRight() {
        return this.topRight;
    }
}
