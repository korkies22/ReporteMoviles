/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.common.BitMatrix;

enum DataMask {
    DATA_MASK_000{

        @Override
        boolean isMasked(int n, int n2) {
            if ((n + n2 & 1) == 0) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_001{

        @Override
        boolean isMasked(int n, int n2) {
            if ((n & 1) == 0) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_010{

        @Override
        boolean isMasked(int n, int n2) {
            if (n2 % 3 == 0) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_011{

        @Override
        boolean isMasked(int n, int n2) {
            if ((n + n2) % 3 == 0) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_100{

        @Override
        boolean isMasked(int n, int n2) {
            if ((n / 2 + n2 / 3 & 1) == 0) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_101{

        @Override
        boolean isMasked(int n, int n2) {
            if (n * n2 % 6 == 0) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_110{

        @Override
        boolean isMasked(int n, int n2) {
            if (n * n2 % 6 < 3) {
                return true;
            }
            return false;
        }
    }
    ,
    DATA_MASK_111{

        @Override
        boolean isMasked(int n, int n2) {
            if ((n + n2 + n * n2 % 3 & 1) == 0) {
                return true;
            }
            return false;
        }
    };
    

    private DataMask() {
    }

    abstract boolean isMasked(int var1, int var2);

    final void unmaskBitMatrix(BitMatrix bitMatrix, int n) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!this.isMasked(i, j)) continue;
                bitMatrix.flip(j, i);
            }
        }
    }

}
