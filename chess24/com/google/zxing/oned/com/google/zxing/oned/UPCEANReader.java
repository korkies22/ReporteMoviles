/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.EANManufacturerOrgSupport;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.UPCEANExtensionSupport;
import java.util.Map;

public abstract class UPCEANReader
extends OneDReader {
    static final int[] END_PATTERN;
    static final int[][] L_AND_G_PATTERNS;
    static final int[][] L_PATTERNS;
    private static final float MAX_AVG_VARIANCE = 0.48f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;
    static final int[] MIDDLE_PATTERN;
    static final int[] START_END_PATTERN;
    private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
    private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
    private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();

    static {
        START_END_PATTERN = new int[]{1, 1, 1};
        MIDDLE_PATTERN = new int[]{1, 1, 1, 1, 1};
        END_PATTERN = new int[]{1, 1, 1, 1, 1, 1};
        L_PATTERNS = new int[][]{{3, 2, 1, 1}, {2, 2, 2, 1}, {2, 1, 2, 2}, {1, 4, 1, 1}, {1, 1, 3, 2}, {1, 2, 3, 1}, {1, 1, 1, 4}, {1, 3, 1, 2}, {1, 2, 1, 3}, {3, 1, 1, 2}};
        L_AND_G_PATTERNS = new int[20][];
        System.arraycopy(L_PATTERNS, 0, L_AND_G_PATTERNS, 0, 10);
        for (int i = 10; i < 20; ++i) {
            int[] arrn = L_PATTERNS[i - 10];
            int[] arrn2 = new int[arrn.length];
            for (int j = 0; j < arrn.length; ++j) {
                arrn2[j] = arrn[arrn.length - j - 1];
            }
            UPCEANReader.L_AND_G_PATTERNS[i] = arrn2;
        }
    }

    protected UPCEANReader() {
    }

    static boolean checkStandardUPCEANChecksum(CharSequence charSequence) throws FormatException {
        int n;
        int n2 = charSequence.length();
        if (n2 == 0) {
            return false;
        }
        int n3 = 0;
        for (n = n2 - 2; n >= 0; n -= 2) {
            int n4 = charSequence.charAt(n) - 48;
            if (n4 >= 0 && n4 <= 9) {
                n3 += n4;
                continue;
            }
            throw FormatException.getFormatInstance();
        }
        n3 *= 3;
        for (n = n2 - 1; n >= 0; n -= 2) {
            n2 = charSequence.charAt(n) - 48;
            if (n2 >= 0 && n2 <= 9) {
                n3 += n2;
                continue;
            }
            throw FormatException.getFormatInstance();
        }
        if (n3 % 10 == 0) {
            return true;
        }
        return false;
    }

    static int decodeDigit(BitArray bitArray, int[] arrn, int n, int[][] arrn2) throws NotFoundException {
        UPCEANReader.recordPattern(bitArray, n, arrn);
        float f = 0.48f;
        int n2 = -1;
        int n3 = arrn2.length;
        for (n = 0; n < n3; ++n) {
            float f2 = UPCEANReader.patternMatchVariance(arrn, arrn2[n], 0.7f);
            float f3 = f;
            if (f2 < f) {
                n2 = n;
                f3 = f2;
            }
            f = f3;
        }
        if (n2 >= 0) {
            return n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static int[] findGuardPattern(BitArray bitArray, int n, boolean bl, int[] arrn) throws NotFoundException {
        return UPCEANReader.findGuardPattern(bitArray, n, bl, arrn, new int[arrn.length]);
    }

    private static int[] findGuardPattern(BitArray bitArray, int n, boolean bl, int[] arrn, int[] arrn2) throws NotFoundException {
        int n2 = bitArray.getSize();
        n = bl ? bitArray.getNextUnset(n) : bitArray.getNextSet(n);
        int n3 = arrn.length;
        int n4 = n;
        int n5 = 0;
        int n6 = n;
        n = n4;
        n4 = n5;
        while (n6 < n2) {
            boolean bl2 = bitArray.get(n6);
            boolean bl3 = true;
            if (bl2 ^ bl) {
                arrn2[n4] = arrn2[n4] + 1;
                n5 = n;
            } else {
                int n7 = n3 - 1;
                if (n4 == n7) {
                    if (UPCEANReader.patternMatchVariance(arrn2, arrn, 0.7f) < 0.48f) {
                        return new int[]{n, n6};
                    }
                    n5 = n + (arrn2[0] + arrn2[1]);
                    n = n3 - 2;
                    System.arraycopy(arrn2, 2, arrn2, 0, n);
                    arrn2[n] = 0;
                    arrn2[n7] = 0;
                    n = n4 - 1;
                    n4 = n5;
                } else {
                    n5 = n4 + 1;
                    n4 = n;
                    n = n5;
                }
                arrn2[n] = 1;
                bl = !bl ? bl3 : false;
                n5 = n4;
                n4 = n;
            }
            ++n6;
            n = n5;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static int[] findStartGuardPattern(BitArray bitArray) throws NotFoundException {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:296)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    boolean checkChecksum(String string) throws FormatException {
        return UPCEANReader.checkStandardUPCEANChecksum(string);
    }

    int[] decodeEnd(BitArray bitArray, int n) throws NotFoundException {
        return UPCEANReader.findGuardPattern(bitArray, n, false, START_END_PATTERN);
    }

    protected abstract int decodeMiddle(BitArray var1, int[] var2, StringBuilder var3) throws NotFoundException;

    @Override
    public Result decodeRow(int n, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        return this.decodeRow(n, bitArray, UPCEANReader.findStartGuardPattern(bitArray), map);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Result decodeRow(int n, BitArray object, int[] object2, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        Object var12_6;
        int n2;
        int n3;
        Object object3;
        BarcodeFormat barcodeFormat;
        int n4;
        block12 : {
            var12_6 = null;
            object3 = map == null ? null : (ResultPointCallback)map.get((Object)((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK));
            n2 = 1;
            if (object3 != null) {
                object3.foundPossibleResultPoint(new ResultPoint((float)(object2[0] + object2[1]) / 2.0f, n));
            }
            StringBuilder stringBuilder = this.decodeRowStringBuffer;
            stringBuilder.setLength(0);
            n4 = this.decodeMiddle((BitArray)object, (int[])object2, stringBuilder);
            if (object3 != null) {
                object3.foundPossibleResultPoint(new ResultPoint(n4, n));
            }
            int[] arrn = this.decodeEnd((BitArray)object, n4);
            if (object3 != null) {
                object3.foundPossibleResultPoint(new ResultPoint((float)(arrn[0] + arrn[1]) / 2.0f, n));
            }
            if ((n3 = (n4 = arrn[1]) - arrn[0] + n4) >= object.getSize()) throw NotFoundException.getNotFoundInstance();
            if (!object.isRange(n4, n3, false)) {
                throw NotFoundException.getNotFoundInstance();
            }
            object3 = stringBuilder.toString();
            if (object3.length() < 8) {
                throw FormatException.getFormatInstance();
            }
            if (!this.checkChecksum((String)object3)) {
                throw ChecksumException.getChecksumInstance();
            }
            float f = (float)(object2[1] + object2[0]) / 2.0f;
            float f2 = (float)(arrn[1] + arrn[0]) / 2.0f;
            barcodeFormat = this.getBarcodeFormat();
            float f3 = n;
            object2 = new Result((String)object3, null, new ResultPoint[]{new ResultPoint(f, f3), new ResultPoint(f2, f3)}, barcodeFormat);
            try {
                object = this.extensionReader.decodeRow(n, (BitArray)object, arrn[1]);
                object2.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, object.getText());
                object2.putAllMetadata(object.getResultMetadata());
                object2.addResultPoints(object.getResultPoints());
                n = object.getText().length();
                break block12;
            }
            catch (ReaderException readerException) {}
            n = 0;
        }
        object = map == null ? var12_6 : (int[])map.get((Object)((Object)DecodeHintType.ALLOWED_EAN_EXTENSIONS));
        if (object != null) {
            block13 : {
                n3 = ((Object)object).length;
                for (n4 = 0; n4 < n3; ++n4) {
                    if (n != object[n4]) continue;
                    n = n2;
                    break block13;
                }
                n = 0;
            }
            if (n == 0) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (barcodeFormat != BarcodeFormat.EAN_13) {
            if (barcodeFormat != BarcodeFormat.UPC_A) return object2;
        }
        if ((object = this.eanManSupport.lookupCountryIdentifier((String)object3)) == null) return object2;
        object2.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, object);
        return object2;
    }

    abstract BarcodeFormat getBarcodeFormat();
}
