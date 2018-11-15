/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.qrcode.encoder.ByteMatrix;

final class MaskUtil {
    private static final int N1 = 3;
    private static final int N2 = 3;
    private static final int N3 = 40;
    private static final int N4 = 10;

    private MaskUtil() {
    }

    static int applyMaskPenaltyRule1(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, true) + MaskUtil.applyMaskPenaltyRule1Internal(byteMatrix, false);
    }

    private static int applyMaskPenaltyRule1Internal(ByteMatrix arrby, boolean bl) {
        int n;
        int n2 = bl ? arrby.getHeight() : arrby.getWidth();
        int n3 = bl ? arrby.getWidth() : arrby.getHeight();
        arrby = arrby.getArray();
        int n4 = n = 0;
        while (n < n2) {
            int n5;
            int n6;
            int n7 = -1;
            int n8 = n6 = 0;
            while (n6 < n3) {
                int n9;
                n5 = bl ? arrby[n][n6] : arrby[n6][n];
                if (n5 == n7) {
                    n5 = n8 + 1;
                    n9 = n7;
                } else {
                    n7 = n4;
                    if (n8 >= 5) {
                        n7 = n4 + (3 + (n8 - 5));
                    }
                    n8 = 1;
                    n9 = n5;
                    n4 = n7;
                    n5 = n8;
                }
                ++n6;
                n8 = n5;
                n7 = n9;
            }
            n5 = n4;
            if (n8 >= 5) {
                n5 = n4 + (3 + (n8 - 5));
            }
            n4 = n5;
            ++n;
        }
        return n4;
    }

    static int applyMaskPenaltyRule2(ByteMatrix arrby) {
        int n;
        byte[][] arrby2 = arrby.getArray();
        int n2 = arrby.getWidth();
        int n3 = arrby.getHeight();
        int n4 = n = 0;
        while (n < n3 - 1) {
            int n5 = 0;
            while (n5 < n2 - 1) {
                byte by = arrby2[n][n5];
                arrby = arrby2[n];
                int n6 = n5 + 1;
                int n7 = n4;
                if (by == arrby[n6]) {
                    int n8 = n + 1;
                    n7 = n4;
                    if (by == arrby2[n8][n5]) {
                        n7 = n4;
                        if (by == arrby2[n8][n6]) {
                            n7 = n4 + 1;
                        }
                    }
                }
                n5 = n6;
                n4 = n7;
            }
            ++n;
        }
        return 3 * n4;
    }

    static int applyMaskPenaltyRule3(ByteMatrix arrby) {
        int n;
        byte[][] arrby2 = arrby.getArray();
        int n2 = arrby.getWidth();
        int n3 = arrby.getHeight();
        int n4 = n = 0;
        while (n < n3) {
            for (int i = 0; i < n2; ++i) {
                int n5;
                int n6;
                block6 : {
                    block7 : {
                        arrby = arrby2[n];
                        n5 = i + 6;
                        n6 = n4;
                        if (n5 >= n2) break block6;
                        n6 = n4;
                        if (arrby[i] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 1] != 0) break block6;
                        n6 = n4;
                        if (arrby[i + 2] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 3] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 4] != 1) break block6;
                        n6 = n4;
                        if (arrby[i + 5] != 0) break block6;
                        n6 = n4;
                        if (arrby[n5] != 1) break block6;
                        if (MaskUtil.isWhiteHorizontal(arrby, i - 4, i)) break block7;
                        n6 = n4;
                        if (!MaskUtil.isWhiteHorizontal(arrby, i + 7, i + 11)) break block6;
                    }
                    n6 = n4 + 1;
                }
                n5 = n + 6;
                n4 = n6;
                if (n5 >= n3) continue;
                n4 = n6;
                if (arrby2[n][i] != 1) continue;
                n4 = n6;
                if (arrby2[n + 1][i] != 0) continue;
                n4 = n6;
                if (arrby2[n + 2][i] != 1) continue;
                n4 = n6;
                if (arrby2[n + 3][i] != 1) continue;
                n4 = n6;
                if (arrby2[n + 4][i] != 1) continue;
                n4 = n6;
                if (arrby2[n + 5][i] != 0) continue;
                n4 = n6;
                if (arrby2[n5][i] != 1) continue;
                if (!MaskUtil.isWhiteVertical(arrby2, i, n - 4, n)) {
                    n4 = n6;
                    if (!MaskUtil.isWhiteVertical(arrby2, i, n + 7, n + 11)) continue;
                }
                n4 = n6 + 1;
            }
            ++n;
        }
        return n4 * 40;
    }

    static int applyMaskPenaltyRule4(ByteMatrix byteMatrix) {
        int n;
        byte[][] arrby = byteMatrix.getArray();
        int n2 = byteMatrix.getWidth();
        int n3 = byteMatrix.getHeight();
        int n4 = n = 0;
        while (n < n3) {
            byte[] arrby2 = arrby[n];
            for (int i = 0; i < n2; ++i) {
                int n5 = n4;
                if (arrby2[i] == 1) {
                    n5 = n4 + 1;
                }
                n4 = n5;
            }
            ++n;
        }
        n = byteMatrix.getHeight() * byteMatrix.getWidth();
        return Math.abs((n4 << 1) - n) * 10 / n * 10;
    }

    static boolean getDataMaskBit(int n, int n2, int n3) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder("Invalid mask pattern: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 7: {
                n = n3 * n2 % 3 + (n3 + n2 & 1) & 1;
                break;
            }
            case 6: {
                n = n3 * n2;
                n = (n & 1) + n % 3 & 1;
                break;
            }
            case 5: {
                n = n3 * n2;
                n = (n & 1) + n % 3;
                break;
            }
            case 4: {
                n = n3 / 2 + n2 / 3 & 1;
                break;
            }
            case 3: {
                n = (n3 + n2) % 3;
                break;
            }
            case 2: {
                n = n2 % 3;
                break;
            }
            case 1: {
                n = n3 & 1;
                break;
            }
            case 0: {
                n = n3 + n2 & 1;
            }
        }
        if (n == 0) {
            return true;
        }
        return false;
    }

    private static boolean isWhiteHorizontal(byte[] arrby, int n, int n2) {
        n2 = Math.min(n2, arrby.length);
        for (n = Math.max((int)n, (int)0); n < n2; ++n) {
            if (arrby[n] != 1) continue;
            return false;
        }
        return true;
    }

    private static boolean isWhiteVertical(byte[][] arrby, int n, int n2, int n3) {
        n3 = Math.min(n3, arrby.length);
        for (n2 = Math.max((int)n2, (int)0); n2 < n3; ++n2) {
            if (arrby[n2][n] != 1) continue;
            return false;
        }
        return true;
    }
}
