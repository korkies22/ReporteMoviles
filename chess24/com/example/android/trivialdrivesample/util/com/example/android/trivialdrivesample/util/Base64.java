/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.Base64DecoderException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] DECODABET;
    public static final boolean DECODE = false;
    public static final boolean ENCODE = true;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    private static final byte NEW_LINE = 10;
    private static final byte[] WEBSAFE_ALPHABET;
    private static final byte[] WEBSAFE_DECODABET;
    private static final byte WHITE_SPACE_ENC = -5;

    static {
        WEBSAFE_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
        WEBSAFE_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
    }

    private Base64() {
    }

    public static byte[] decode(String arrby) throws Base64DecoderException {
        arrby = arrby.getBytes();
        return Base64.decode(arrby, 0, arrby.length);
    }

    public static byte[] decode(byte[] arrby) throws Base64DecoderException {
        return Base64.decode(arrby, 0, arrby.length);
    }

    public static byte[] decode(byte[] arrby, int n, int n2) throws Base64DecoderException {
        return Base64.decode(arrby, n, n2, DECODABET);
    }

    public static byte[] decode(byte[] object, int n, int n2, byte[] object2) throws Base64DecoderException {
        byte[] arrby = new byte[n2 * 3 / 4 + 2];
        byte[] arrby2 = new byte[4];
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            int n5 = i + n;
            byte by = (byte)(object[n5] & 127);
            byte by2 = object2[by];
            if (by2 >= -5) {
                if (by2 < -1) continue;
                if (by == 61) {
                    n5 = n2 - i;
                    n = (byte)(object[n2 - 1 + n] & 127);
                    if (n3 != 0 && n3 != 1) {
                        if (n3 == 3 && n5 > 2 || n3 == 4 && n5 > 1) {
                            object = new StringBuilder();
                            object.append("padding byte '=' falsely signals end of encoded value at offset ");
                            object.append(i);
                            throw new Base64DecoderException(object.toString());
                        }
                        if (n == 61 || n == 10) break;
                        throw new Base64DecoderException("encoded value has invalid trailing byte");
                    }
                    object = new StringBuilder();
                    object.append("invalid padding byte '=' at byte offset ");
                    object.append(i);
                    throw new Base64DecoderException(object.toString());
                }
                n5 = n3 + 1;
                arrby2[n3] = by;
                if (n5 == 4) {
                    n4 += Base64.decode4to3(arrby2, 0, arrby, n4, (byte[])object2);
                    n3 = 0;
                    continue;
                }
                n3 = n5;
                continue;
            }
            object2 = new StringBuilder();
            object2.append("Bad Base64 input character at ");
            object2.append(i);
            object2.append(": ");
            object2.append((int)object[n5]);
            object2.append("(decimal)");
            throw new Base64DecoderException(object2.toString());
        }
        if (n3 != 0) {
            if (n3 == 1) {
                object = new StringBuilder();
                object.append("single trailing character at offset ");
                object.append(n2 - 1);
                throw new Base64DecoderException(object.toString());
            }
            arrby2[n3] = 61;
            n4 += Base64.decode4to3(arrby2, 0, arrby, n4, (byte[])object2);
        }
        object = new byte[n4];
        System.arraycopy(arrby, 0, object, 0, n4);
        return object;
    }

    private static int decode4to3(byte[] arrby, int n, byte[] arrby2, int n2, byte[] arrby3) {
        int n3 = n + 2;
        if (arrby[n3] == 61) {
            n3 = arrby3[arrby[n]];
            arrby2[n2] = (byte)((arrby3[arrby[n + 1]] << 24 >>> 12 | n3 << 24 >>> 6) >>> 16);
            return 1;
        }
        int n4 = n + 3;
        if (arrby[n4] == 61) {
            n4 = arrby3[arrby[n]];
            n = arrby3[arrby[n + 1]];
            n = arrby3[arrby[n3]] << 24 >>> 18 | (n << 24 >>> 12 | n4 << 24 >>> 6);
            arrby2[n2] = (byte)(n >>> 16);
            arrby2[n2 + 1] = (byte)(n >>> 8);
            return 2;
        }
        byte by = arrby3[arrby[n]];
        n = arrby3[arrby[n + 1]];
        n3 = arrby3[arrby[n3]];
        n = arrby3[arrby[n4]] << 24 >>> 24 | (n << 24 >>> 12 | by << 24 >>> 6 | n3 << 24 >>> 18);
        arrby2[n2] = (byte)(n >> 16);
        arrby2[n2 + 1] = (byte)(n >> 8);
        arrby2[n2 + 2] = (byte)n;
        return 3;
    }

    public static byte[] decodeWebSafe(String arrby) throws Base64DecoderException {
        arrby = arrby.getBytes();
        return Base64.decodeWebSafe(arrby, 0, arrby.length);
    }

    public static byte[] decodeWebSafe(byte[] arrby) throws Base64DecoderException {
        return Base64.decodeWebSafe(arrby, 0, arrby.length);
    }

    public static byte[] decodeWebSafe(byte[] arrby, int n, int n2) throws Base64DecoderException {
        return Base64.decode(arrby, n, n2, WEBSAFE_DECODABET);
    }

    public static String encode(byte[] arrby) {
        return Base64.encode(arrby, 0, arrby.length, ALPHABET, true);
    }

    public static String encode(byte[] arrby, int n, int n2, byte[] arrby2, boolean bl) {
        arrby = Base64.encode(arrby, n, n2, arrby2, Integer.MAX_VALUE);
        for (n = arrby.length; !bl && n > 0 && arrby[n - 1] == 61; --n) {
        }
        return new String(arrby, 0, n);
    }

    public static byte[] encode(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        int n4;
        int n5 = (n2 + 2) / 3 * 4;
        byte[] arrby3 = new byte[n5 + n5 / n3];
        int n6 = n5 = (n4 = 0);
        while (n4 < n2 - 2) {
            int n7 = arrby[n4 + n] << 24 >>> 8 | arrby[n4 + 1 + n] << 24 >>> 16 | arrby[n4 + 2 + n] << 24 >>> 24;
            arrby3[n5] = arrby2[n7 >>> 18];
            int n8 = n5 + 1;
            arrby3[n8] = arrby2[n7 >>> 12 & 63];
            arrby3[n5 + 2] = arrby2[n7 >>> 6 & 63];
            arrby3[n5 + 3] = arrby2[n7 & 63];
            if ((n6 += 4) == n3) {
                arrby3[n5 + 4] = 10;
                n6 = 0;
                n5 = n8;
            }
            n4 += 3;
            n5 += 4;
        }
        if (n4 < n2) {
            Base64.encode3to4(arrby, n4 + n, n2 - n4, arrby3, n5, arrby2);
            if (n6 + 4 == n3) {
                arrby3[n5 + 4] = 10;
            }
        }
        return arrby3;
    }

    private static byte[] encode3to4(byte[] arrby, int n, int n2, byte[] arrby2, int n3, byte[] arrby3) {
        int n4 = 0;
        int n5 = n2 > 0 ? arrby[n] << 24 >>> 8 : 0;
        int n6 = n2 > 1 ? arrby[n + 1] << 24 >>> 16 : 0;
        if (n2 > 2) {
            n4 = arrby[n + 2] << 24 >>> 24;
        }
        n = n5 | n6 | n4;
        switch (n2) {
            default: {
                return arrby2;
            }
            case 3: {
                arrby2[n3] = arrby3[n >>> 18];
                arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
                arrby2[n3 + 2] = arrby3[n >>> 6 & 63];
                arrby2[n3 + 3] = arrby3[n & 63];
                return arrby2;
            }
            case 2: {
                arrby2[n3] = arrby3[n >>> 18];
                arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
                arrby2[n3 + 2] = arrby3[n >>> 6 & 63];
                arrby2[n3 + 3] = 61;
                return arrby2;
            }
            case 1: 
        }
        arrby2[n3] = arrby3[n >>> 18];
        arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
        arrby2[n3 + 2] = 61;
        arrby2[n3 + 3] = 61;
        return arrby2;
    }

    public static String encodeWebSafe(byte[] arrby, boolean bl) {
        return Base64.encode(arrby, 0, arrby.length, WEBSAFE_ALPHABET, bl);
    }
}
