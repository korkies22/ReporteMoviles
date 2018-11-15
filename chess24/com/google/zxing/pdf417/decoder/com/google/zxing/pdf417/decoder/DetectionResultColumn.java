/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import java.util.Formatter;

class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[boundingBox.getMaxY() - boundingBox.getMinY() + 1];
    }

    final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    final Codeword getCodeword(int n) {
        return this.codewords[this.imageRowToCodewordIndex(n)];
    }

    final Codeword getCodewordNearby(int n) {
        Codeword codeword = this.getCodeword(n);
        if (codeword != null) {
            return codeword;
        }
        for (int i = 1; i < 5; ++i) {
            int n2 = this.imageRowToCodewordIndex(n) - i;
            if (n2 >= 0 && (codeword = this.codewords[n2]) != null) {
                return codeword;
            }
            n2 = this.imageRowToCodewordIndex(n) + i;
            if (n2 >= this.codewords.length || (codeword = this.codewords[n2]) == null) continue;
            return codeword;
        }
        return null;
    }

    final Codeword[] getCodewords() {
        return this.codewords;
    }

    final int imageRowToCodewordIndex(int n) {
        return n - this.boundingBox.getMinY();
    }

    final void setCodeword(int n, Codeword codeword) {
        this.codewords[this.imageRowToCodewordIndex((int)n)] = codeword;
    }

    public String toString() {
        int n;
        Formatter formatter = new Formatter();
        Object object = this.codewords;
        int n2 = ((Codeword[])object).length;
        int n3 = n = 0;
        while (n < n2) {
            Codeword codeword = object[n];
            if (codeword == null) {
                formatter.format("%3d:    |   %n", n3);
                ++n3;
            } else {
                formatter.format("%3d: %3d|%3d%n", n3, codeword.getRowNumber(), codeword.getValue());
                ++n3;
            }
            ++n;
        }
        object = formatter.toString();
        formatter.close();
        return object;
    }
}
