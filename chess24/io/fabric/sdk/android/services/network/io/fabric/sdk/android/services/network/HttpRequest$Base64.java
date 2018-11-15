/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public static class HttpRequest.Base64 {
    private static final byte EQUALS_SIGN = 61;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    private static final byte[] _STANDARD_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    private HttpRequest.Base64() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String encode(String arrby) {
        try {
            byte[] arrby2;
            arrby = arrby2 = arrby.getBytes(PREFERRED_ENCODING);
            return HttpRequest.Base64.encodeBytes(arrby);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {}
        arrby = arrby.getBytes();
        return HttpRequest.Base64.encodeBytes(arrby);
    }

    private static byte[] encode3to4(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        byte[] arrby3 = _STANDARD_ALPHABET;
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

    public static String encodeBytes(byte[] arrby) {
        return HttpRequest.Base64.encodeBytes(arrby, 0, arrby.length);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String encodeBytes(byte[] arrby, int n, int n2) {
        arrby = HttpRequest.Base64.encodeBytesToBytes(arrby, n, n2);
        try {
            return new String(arrby, PREFERRED_ENCODING);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(arrby);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] object, int n, int n2) {
        if (object == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        }
        if (n < 0) {
            object = new StringBuilder();
            object.append("Cannot have negative offset: ");
            object.append(n);
            throw new IllegalArgumentException(object.toString());
        }
        if (n2 < 0) {
            object = new StringBuilder();
            object.append("Cannot have length offset: ");
            object.append(n2);
            throw new IllegalArgumentException(object.toString());
        }
        if (n + n2 > ((byte[])object).length) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Cannot have offset of %d and length of %d with array of length %d", n, n2, ((Object)object).length));
        }
        int n3 = n2 / 3;
        int n4 = 4;
        if (n2 % 3 <= 0) {
            n4 = 0;
        }
        byte[] arrby = new byte[n3 * 4 + n4];
        n4 = n3 = 0;
        while (n3 < n2 - 2) {
            HttpRequest.Base64.encode3to4((byte[])object, n3 + n, 3, arrby, n4);
            n3 += 3;
            n4 += 4;
        }
        int n5 = n4;
        if (n3 < n2) {
            HttpRequest.Base64.encode3to4((byte[])object, n + n3, n2 - n3, arrby, n4);
            n5 = n4 + 4;
        }
        if (n5 <= arrby.length - 1) {
            object = new byte[n5];
            System.arraycopy(arrby, 0, object, 0, n5);
            return object;
        }
        return arrby;
    }
}
