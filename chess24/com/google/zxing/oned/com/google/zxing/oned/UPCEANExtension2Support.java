/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension2Support {
    private final int[] decodeMiddleCounters = new int[4];
    private final StringBuilder decodeRowStringBuffer = new StringBuilder();

    UPCEANExtension2Support() {
    }

    private int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int n;
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n2 = bitArray.getSize();
        int n3 = arrn[1];
        int n4 = n = 0;
        while (n < 2 && n3 < n2) {
            int n5;
            int n6 = UPCEANReader.decodeDigit(bitArray, arrn2, n3, UPCEANReader.L_AND_G_PATTERNS);
            stringBuilder.append((char)(48 + n6 % 10));
            int n7 = arrn2.length;
            for (n5 = 0; n5 < n7; ++n5) {
                n3 += arrn2[n5];
            }
            n5 = n4;
            if (n6 >= 10) {
                n5 = 1 << 1 - n | n4;
            }
            if (n != 1) {
                n3 = bitArray.getNextUnset(bitArray.getNextSet(n3));
            }
            ++n;
            n4 = n5;
        }
        if (stringBuilder.length() != 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (Integer.parseInt(stringBuilder.toString()) % 4 != n4) {
            throw NotFoundException.getNotFoundInstance();
        }
        return n3;
    }

    private static Map<ResultMetadataType, Object> parseExtensionString(String string) {
        if (string.length() != 2) {
            return null;
        }
        EnumMap<ResultMetadataType, Object> enumMap = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        enumMap.put(ResultMetadataType.ISSUE_NUMBER, Integer.valueOf(string));
        return enumMap;
    }

    Result decodeRow(int n, BitArray object, int[] object2) throws NotFoundException {
        CharSequence charSequence = this.decodeRowStringBuffer;
        charSequence.setLength(0);
        int n2 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)charSequence);
        charSequence = charSequence.toString();
        object = UPCEANExtension2Support.parseExtensionString((String)charSequence);
        float f = (float)(object2[0] + object2[1]) / 2.0f;
        float f2 = n;
        object2 = new ResultPoint(f, f2);
        ResultPoint resultPoint = new ResultPoint(n2, f2);
        BarcodeFormat barcodeFormat = BarcodeFormat.UPC_EAN_EXTENSION;
        object2 = new Result((String)charSequence, null, new ResultPoint[]{object2, resultPoint}, barcodeFormat);
        if (object != null) {
            object2.putAllMetadata((Map<ResultMetadataType, Object>)object);
        }
        return object2;
    }
}
