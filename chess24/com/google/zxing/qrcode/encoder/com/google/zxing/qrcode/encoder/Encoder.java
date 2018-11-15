/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.BlockPair;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.MatrixUtil;
import com.google.zxing.qrcode.encoder.QRCode;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Encoder {
    private static final int[] ALPHANUMERIC_TABLE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    private Encoder() {
    }

    static void append8BitBytes(String arrby, BitArray bitArray, String string) throws WriterException {
        try {
            arrby = arrby.getBytes(string);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new WriterException(unsupportedEncodingException);
        }
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            bitArray.appendBits(arrby[i], 8);
        }
        return;
    }

    static void appendAlphanumericBytes(CharSequence charSequence, BitArray bitArray) throws WriterException {
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = Encoder.getAlphanumericCode(charSequence.charAt(n2));
            if (n3 == -1) {
                throw new WriterException();
            }
            int n4 = n2 + 1;
            if (n4 < n) {
                if ((n4 = Encoder.getAlphanumericCode(charSequence.charAt(n4))) == -1) {
                    throw new WriterException();
                }
                bitArray.appendBits(n3 * 45 + n4, 11);
                n2 += 2;
                continue;
            }
            bitArray.appendBits(n3, 6);
            n2 = n4;
        }
    }

    static void appendBytes(String charSequence, Mode mode, BitArray bitArray, String string) throws WriterException {
        switch (.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()]) {
            default: {
                charSequence = new StringBuilder("Invalid mode: ");
                charSequence.append((Object)mode);
                throw new WriterException(charSequence.toString());
            }
            case 4: {
                Encoder.appendKanjiBytes((String)charSequence, bitArray);
                return;
            }
            case 3: {
                Encoder.append8BitBytes((String)charSequence, bitArray, string);
                return;
            }
            case 2: {
                Encoder.appendAlphanumericBytes(charSequence, bitArray);
                return;
            }
            case 1: 
        }
        Encoder.appendNumericBytes(charSequence, bitArray);
    }

    private static void appendECI(CharacterSetECI characterSetECI, BitArray bitArray) {
        bitArray.appendBits(Mode.ECI.getBits(), 4);
        bitArray.appendBits(characterSetECI.getValue(), 8);
    }

    static void appendKanjiBytes(String arrby, BitArray bitArray) throws WriterException {
        try {
            arrby = arrby.getBytes("Shift_JIS");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new WriterException(unsupportedEncodingException);
        }
        int n = arrby.length;
        for (int i = 0; i < n; i += 2) {
            int n2 = (arrby[i] & 255) << 8 | arrby[i + 1] & 255;
            n2 = n2 >= 33088 && n2 <= 40956 ? (n2 -= 33088) : (n2 >= 57408 && n2 <= 60351 ? (n2 -= 49472) : -1);
            if (n2 == -1) {
                throw new WriterException("Invalid byte sequence");
            }
            bitArray.appendBits((n2 >> 8) * 192 + (n2 & 255), 13);
        }
        return;
    }

    static void appendLengthInfo(int n, Version object, Mode mode, BitArray bitArray) throws WriterException {
        int n2 = mode.getCharacterCountBits((Version)object);
        int n3 = 1 << n2;
        if (n >= n3) {
            object = new StringBuilder();
            object.append(n);
            object.append(" is bigger than ");
            object.append(n3 - 1);
            throw new WriterException(object.toString());
        }
        bitArray.appendBits(n, n2);
    }

    static void appendModeInfo(Mode mode, BitArray bitArray) {
        bitArray.appendBits(mode.getBits(), 4);
    }

    static void appendNumericBytes(CharSequence charSequence, BitArray bitArray) {
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = charSequence.charAt(n2) - 48;
            int n4 = n2 + 2;
            if (n4 < n) {
                bitArray.appendBits(n3 * 100 + (charSequence.charAt(n2 + 1) - 48) * 10 + (charSequence.charAt(n4) - 48), 10);
                n2 += 3;
                continue;
            }
            if (++n2 < n) {
                bitArray.appendBits(n3 * 10 + (charSequence.charAt(n2) - 48), 7);
                n2 = n4;
                continue;
            }
            bitArray.appendBits(n3, 4);
        }
    }

    private static int calculateBitsNeeded(Mode mode, BitArray bitArray, BitArray bitArray2, Version version) {
        return bitArray.getSize() + mode.getCharacterCountBits(version) + bitArray2.getSize();
    }

    private static int calculateMaskPenalty(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1(byteMatrix) + MaskUtil.applyMaskPenaltyRule2(byteMatrix) + MaskUtil.applyMaskPenaltyRule3(byteMatrix) + MaskUtil.applyMaskPenaltyRule4(byteMatrix);
    }

    private static int chooseMaskPattern(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, ByteMatrix byteMatrix) throws WriterException {
        int n = Integer.MAX_VALUE;
        int n2 = -1;
        for (int i = 0; i < 8; ++i) {
            MatrixUtil.buildMatrix(bitArray, errorCorrectionLevel, version, i, byteMatrix);
            int n3 = Encoder.calculateMaskPenalty(byteMatrix);
            int n4 = n;
            if (n3 < n) {
                n2 = i;
                n4 = n3;
            }
            n = n4;
        }
        return n2;
    }

    public static Mode chooseMode(String string) {
        return Encoder.chooseMode(string, null);
    }

    private static Mode chooseMode(String string, String string2) {
        boolean bl;
        if ("Shift_JIS".equals(string2) && Encoder.isOnlyDoubleByteKanji(string)) {
            return Mode.KANJI;
        }
        boolean bl2 = bl = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                bl2 = true;
                continue;
            }
            if (Encoder.getAlphanumericCode(c) != -1) {
                bl = true;
                continue;
            }
            return Mode.BYTE;
        }
        if (bl) {
            return Mode.ALPHANUMERIC;
        }
        if (bl2) {
            return Mode.NUMERIC;
        }
        return Mode.BYTE;
    }

    private static Version chooseVersion(int n, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        for (int i = 1; i <= 40; ++i) {
            Version version = Version.getVersionForNumber(i);
            if (!Encoder.willFit(n, version, errorCorrectionLevel)) continue;
            return version;
        }
        throw new WriterException("Data too big");
    }

    public static QRCode encode(String string, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        return Encoder.encode(string, errorCorrectionLevel, null);
    }

    public static QRCode encode(String object, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, ?> object2) throws WriterException {
        Object object3;
        Object object4 = DEFAULT_BYTE_MODE_ENCODING;
        Object object5 = object4;
        if (object2 != null) {
            object5 = object4;
            if (object2.containsKey((Object)EncodeHintType.CHARACTER_SET)) {
                object5 = object2.get((Object)EncodeHintType.CHARACTER_SET).toString();
            }
        }
        object4 = Encoder.chooseMode((String)object, (String)object5);
        BitArray bitArray = new BitArray();
        if (object4 == Mode.BYTE && !DEFAULT_BYTE_MODE_ENCODING.equals(object5) && (object3 = CharacterSetECI.getCharacterSetECIByName((String)object5)) != null) {
            Encoder.appendECI((CharacterSetECI)((Object)object3), bitArray);
        }
        Encoder.appendModeInfo((Mode)((Object)object4), bitArray);
        object3 = new BitArray();
        Encoder.appendBytes((String)object, (Mode)((Object)object4), (BitArray)object3, (String)object5);
        if (object2 != null && object2.containsKey((Object)EncodeHintType.QR_VERSION)) {
            object2 = object5 = Version.getVersionForNumber(Integer.parseInt(object2.get((Object)EncodeHintType.QR_VERSION).toString()));
            if (!Encoder.willFit(Encoder.calculateBitsNeeded((Mode)((Object)object4), bitArray, (BitArray)object3, (Version)object5), (Version)object5, errorCorrectionLevel)) {
                throw new WriterException("Data too big for requested version");
            }
        } else {
            object2 = Encoder.recommendVersion(errorCorrectionLevel, (Mode)((Object)object4), bitArray, (BitArray)object3);
        }
        object5 = new BitArray();
        object5.appendBitArray(bitArray);
        int n = object4 == Mode.BYTE ? object3.getSizeInBytes() : object.length();
        Encoder.appendLengthInfo(n, (Version)object2, (Mode)((Object)object4), (BitArray)object5);
        object5.appendBitArray((BitArray)object3);
        object = object2.getECBlocksForLevel(errorCorrectionLevel);
        n = object2.getTotalCodewords() - object.getTotalECCodewords();
        Encoder.terminateBits(n, (BitArray)object5);
        object = Encoder.interleaveWithECBytes((BitArray)object5, object2.getTotalCodewords(), n, object.getNumBlocks());
        object5 = new QRCode();
        object5.setECLevel(errorCorrectionLevel);
        object5.setMode((Mode)((Object)object4));
        object5.setVersion((Version)object2);
        n = object2.getDimensionForVersion();
        object4 = new ByteMatrix(n, n);
        n = Encoder.chooseMaskPattern((BitArray)object, errorCorrectionLevel, (Version)object2, (ByteMatrix)object4);
        object5.setMaskPattern(n);
        MatrixUtil.buildMatrix((BitArray)object, errorCorrectionLevel, (Version)object2, n, (ByteMatrix)object4);
        object5.setMatrix((ByteMatrix)object4);
        return object5;
    }

    static byte[] generateECBytes(byte[] arrby, int n) {
        int n2;
        int n3 = 0;
        int n4 = arrby.length;
        int[] arrn = new int[n4 + n];
        for (n2 = 0; n2 < n4; ++n2) {
            arrn[n2] = arrby[n2] & 255;
        }
        new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(arrn, n);
        arrby = new byte[n];
        for (n2 = n3; n2 < n; ++n2) {
            arrby[n2] = (byte)arrn[n4 + n2];
        }
        return arrby;
    }

    static int getAlphanumericCode(int n) {
        if (n < ALPHANUMERIC_TABLE.length) {
            return ALPHANUMERIC_TABLE[n];
        }
        return -1;
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int n, int n2, int n3, int n4, int[] arrn, int[] arrn2) throws WriterException {
        if (n4 >= n3) {
            throw new WriterException("Block ID too large");
        }
        int n5 = n % n3;
        int n6 = n3 - n5;
        int n7 = n / n3;
        int n8 = (n2 /= n3) + 1;
        int n9 = n7 - n2;
        if (n9 != (n7 = n7 + 1 - n8)) {
            throw new WriterException("EC bytes mismatch");
        }
        if (n3 != n6 + n5) {
            throw new WriterException("RS blocks mismatch");
        }
        if (n != (n2 + n9) * n6 + (n8 + n7) * n5) {
            throw new WriterException("Total bytes mismatch");
        }
        if (n4 < n6) {
            arrn[0] = n2;
            arrn2[0] = n9;
            return;
        }
        arrn[0] = n8;
        arrn2[0] = n7;
    }

    static BitArray interleaveWithECBytes(BitArray bitArray, int n, int n2, int n3) throws WriterException {
        int n4;
        int n5;
        int n6;
        Object object;
        byte[] arrby;
        if (bitArray.getSizeInBytes() != n2) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        Serializable serializable = new ArrayList(n3);
        int n7 = 0;
        int n8 = n6 = (n5 = (n4 = 0));
        int n9 = n5;
        for (n5 = n4; n5 < n3; ++n5) {
            object = new int[1];
            int[] arrn = new int[1];
            Encoder.getNumDataBytesAndNumECBytesForBlockID(n, n2, n3, n5, (int[])object, arrn);
            n4 = object[0];
            arrby = new byte[n4];
            bitArray.toBytes(n9 << 3, arrby, 0, n4);
            arrn = Encoder.generateECBytes(arrby, arrn[0]);
            serializable.add(new BlockPair(arrby, (byte[])arrn));
            n6 = Math.max(n6, n4);
            n8 = Math.max(n8, arrn.length);
            n9 += object[0];
        }
        if (n2 != n9) {
            throw new WriterException("Data bytes does not match offset");
        }
        bitArray = new BitArray();
        n2 = 0;
        do {
            if (n2 >= n6) break;
            object = serializable.iterator();
            while (object.hasNext()) {
                arrby = ((BlockPair)object.next()).getDataBytes();
                if (n2 >= arrby.length) continue;
                bitArray.appendBits(arrby[n2], 8);
            }
            ++n2;
        } while (true);
        for (n3 = n7; n3 < n8; ++n3) {
            object = serializable.iterator();
            while (object.hasNext()) {
                arrby = ((BlockPair)object.next()).getErrorCorrectionBytes();
                if (n3 >= arrby.length) continue;
                bitArray.appendBits(arrby[n3], 8);
            }
        }
        if (n != bitArray.getSizeInBytes()) {
            serializable = new StringBuilder("Interleaving error: ");
            serializable.append(n);
            serializable.append(" and ");
            serializable.append(bitArray.getSizeInBytes());
            serializable.append(" differ.");
            throw new WriterException(serializable.toString());
        }
        return bitArray;
    }

    private static boolean isOnlyDoubleByteKanji(String arrby) {
        try {
            arrby = arrby.getBytes("Shift_JIS");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return false;
        }
        int n = arrby.length;
        if (n % 2 != 0) {
            return false;
        }
        for (int i = 0; i < n; i += 2) {
            int n2 = arrby[i] & 255;
            if (n2 >= 129 && n2 <= 159) continue;
            if (n2 >= 224) {
                if (n2 <= 235) continue;
                return false;
            }
            return false;
        }
        return true;
    }

    private static Version recommendVersion(ErrorCorrectionLevel errorCorrectionLevel, Mode mode, BitArray bitArray, BitArray bitArray2) throws WriterException {
        return Encoder.chooseVersion(Encoder.calculateBitsNeeded(mode, bitArray, bitArray2, Encoder.chooseVersion(Encoder.calculateBitsNeeded(mode, bitArray, bitArray2, Version.getVersionForNumber(1)), errorCorrectionLevel)), errorCorrectionLevel);
    }

    static void terminateBits(int n, BitArray bitArray) throws WriterException {
        int n2;
        int n3 = n << 3;
        if (bitArray.getSize() > n3) {
            StringBuilder stringBuilder = new StringBuilder("data bits cannot fit in the QR Code");
            stringBuilder.append(bitArray.getSize());
            stringBuilder.append(" > ");
            stringBuilder.append(n3);
            throw new WriterException(stringBuilder.toString());
        }
        int n4 = 0;
        for (n2 = 0; n2 < 4 && bitArray.getSize() < n3; ++n2) {
            bitArray.appendBit(false);
        }
        n2 = bitArray.getSize() & 7;
        if (n2 > 0) {
            while (n2 < 8) {
                bitArray.appendBit(false);
                ++n2;
            }
        }
        int n5 = bitArray.getSizeInBytes();
        for (n2 = n4; n2 < n - n5; ++n2) {
            n4 = (n2 & 1) == 0 ? 236 : 17;
            bitArray.appendBits(n4, 8);
        }
        if (bitArray.getSize() != n3) {
            throw new WriterException("Bits size does not equal capacity");
        }
    }

    private static boolean willFit(int n, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        if (version.getTotalCodewords() - version.getECBlocksForLevel(errorCorrectionLevel).getTotalECCodewords() >= (n + 7) / 8) {
            return true;
        }
        return false;
    }

}
