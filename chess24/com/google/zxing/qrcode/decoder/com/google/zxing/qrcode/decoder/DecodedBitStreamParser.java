/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class DecodedBitStreamParser {
    private static final char[] ALPHANUMERIC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();
    private static final int GB2312_SUBSET = 1;

    private DecodedBitStreamParser() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static DecoderResult decode(byte[] arrby, Version arrayList, ErrorCorrectionLevel object, Map<DecodeHintType, ?> object2) throws FormatException {
        Mode mode;
        Enum enum_;
        int n;
        BitSource bitSource = new BitSource(arrby);
        StringBuilder stringBuilder = new StringBuilder(50);
        ArrayList<byte[]> arrayList2 = new ArrayList<byte[]>(1);
        boolean bl = false;
        int n2 = n = -1;
        Enum enum_2 = null;
        do {
            block14 : {
                int n3;
                int n4;
                block13 : {
                    try {
                        mode = bitSource.available() < 4 ? Mode.TERMINATOR : Mode.forBits(bitSource.readBits(4));
                        enum_ = enum_2;
                        n3 = n;
                        n4 = n2;
                        if (mode == Mode.TERMINATOR) break block13;
                        if (mode == Mode.FNC1_FIRST_POSITION || mode == Mode.FNC1_SECOND_POSITION) break block14;
                        if (mode == Mode.STRUCTURED_APPEND) {
                            if (bitSource.available() < 16) {
                                throw FormatException.getFormatInstance();
                            }
                            n3 = bitSource.readBits(8);
                            n4 = bitSource.readBits(8);
                            enum_ = enum_2;
                            break block13;
                        }
                        if (mode == Mode.ECI) {
                            enum_ = enum_2 = CharacterSetECI.getCharacterSetECIByValue(DecodedBitStreamParser.parseECIValue(bitSource));
                            n3 = n;
                            n4 = n2;
                            if (enum_2 == null) {
                                throw FormatException.getFormatInstance();
                            }
                            break block13;
                        }
                        if (mode == Mode.HANZI) {
                            int n5 = bitSource.readBits(4);
                            int n6 = bitSource.readBits(mode.getCharacterCountBits((Version)((Object)arrayList)));
                            enum_ = enum_2;
                            n3 = n;
                            n4 = n2;
                            if (n5 == 1) {
                                DecodedBitStreamParser.decodeHanziSegment(bitSource, stringBuilder, n6);
                                enum_ = enum_2;
                                n3 = n;
                                n4 = n2;
                            }
                            break block13;
                        }
                        n3 = bitSource.readBits(mode.getCharacterCountBits((Version)((Object)arrayList)));
                        if (mode == Mode.NUMERIC) {
                            DecodedBitStreamParser.decodeNumericSegment(bitSource, stringBuilder, n3);
                            enum_ = enum_2;
                            n3 = n;
                            n4 = n2;
                            break block13;
                        }
                        if (mode == Mode.ALPHANUMERIC) {
                            DecodedBitStreamParser.decodeAlphanumericSegment(bitSource, stringBuilder, n3, bl);
                            enum_ = enum_2;
                            n3 = n;
                            n4 = n2;
                            break block13;
                        }
                        if (mode == Mode.BYTE) {
                            DecodedBitStreamParser.decodeByteSegment(bitSource, stringBuilder, n3, (CharacterSetECI)enum_2, arrayList2, object2);
                            continue;
                        }
                        if (mode != Mode.KANJI) throw FormatException.getFormatInstance();
                        DecodedBitStreamParser.decodeKanjiSegment(bitSource, stringBuilder, n3);
                        continue;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw FormatException.getFormatInstance();
                    }
                }
                enum_2 = enum_;
                n = n3;
                n2 = n4;
                continue;
            }
            bl = true;
        } while (mode != (enum_ = Mode.TERMINATOR));
        object2 = stringBuilder.toString();
        arrayList = arrayList2.isEmpty() ? null : arrayList2;
        if (object == null) {
            object = null;
            return new DecoderResult(arrby, (String)object2, arrayList, (String)object, n, n2);
        }
        object = object.toString();
        return new DecoderResult(arrby, (String)object2, arrayList, (String)object, n, n2);
    }

    private static void decodeAlphanumericSegment(BitSource bitSource, StringBuilder stringBuilder, int n, boolean bl) throws FormatException {
        int n2 = stringBuilder.length();
        while (n > 1) {
            if (bitSource.available() < 11) {
                throw FormatException.getFormatInstance();
            }
            int n3 = bitSource.readBits(11);
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 / 45));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 % 45));
            n -= 2;
        }
        if (n == 1) {
            if (bitSource.available() < 6) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(bitSource.readBits(6)));
        }
        if (bl) {
            for (n = n2; n < stringBuilder.length(); ++n) {
                if (stringBuilder.charAt(n) != '%') continue;
                if (n < stringBuilder.length() - 1 && stringBuilder.charAt(n2 = n + 1) == '%') {
                    stringBuilder.deleteCharAt(n2);
                    continue;
                }
                stringBuilder.setCharAt(n, '\u001d');
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void decodeByteSegment(BitSource object, StringBuilder stringBuilder, int n, CharacterSetECI characterSetECI, Collection<byte[]> collection, Map<DecodeHintType, ?> map) throws FormatException {
        if (n << 3 > object.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)object.readBits(8);
        }
        object = characterSetECI == null ? StringUtils.guessEncoding(arrby, map) : characterSetECI.name();
        try {
            stringBuilder.append(new String(arrby, (String)object));
            collection.add(arrby);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void decodeHanziSegment(BitSource bitSource, StringBuilder stringBuilder, int n) throws FormatException {
        if (n * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] arrby = new byte[2 * n];
        int n2 = 0;
        while (n > 0) {
            int n3 = bitSource.readBits(13);
            n3 = (n3 = n3 % 96 | n3 / 96 << 8) < 959 ? (n3 += 41377) : (n3 += 42657);
            arrby[n2] = (byte)(n3 >> 8);
            arrby[n2 + 1] = (byte)n3;
            n2 += 2;
            --n;
        }
        try {
            stringBuilder.append(new String(arrby, "GB2312"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void decodeKanjiSegment(BitSource bitSource, StringBuilder stringBuilder, int n) throws FormatException {
        if (n * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] arrby = new byte[2 * n];
        int n2 = 0;
        while (n > 0) {
            int n3 = bitSource.readBits(13);
            n3 = (n3 = n3 % 192 | n3 / 192 << 8) < 7936 ? (n3 += 33088) : (n3 += 49472);
            arrby[n2] = (byte)(n3 >> 8);
            arrby[n2 + 1] = (byte)n3;
            n2 += 2;
            --n;
        }
        try {
            stringBuilder.append(new String(arrby, "SJIS"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeNumericSegment(BitSource bitSource, StringBuilder stringBuilder, int n) throws FormatException {
        while (n >= 3) {
            if (bitSource.available() < 10) {
                throw FormatException.getFormatInstance();
            }
            int n2 = bitSource.readBits(10);
            if (n2 >= 1000) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 / 100));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 / 10 % 10));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 % 10));
            n -= 3;
        }
        if (n == 2) {
            if (bitSource.available() < 7) {
                throw FormatException.getFormatInstance();
            }
            n = bitSource.readBits(7);
            if (n >= 100) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n / 10));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n % 10));
            return;
        }
        if (n == 1) {
            if (bitSource.available() < 4) {
                throw FormatException.getFormatInstance();
            }
            n = bitSource.readBits(4);
            if (n >= 10) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n));
        }
    }

    private static int parseECIValue(BitSource bitSource) throws FormatException {
        int n = bitSource.readBits(8);
        if ((n & 128) == 0) {
            return n & 127;
        }
        if ((n & 192) == 128) {
            return bitSource.readBits(8) | (n & 63) << 8;
        }
        if ((n & 224) == 192) {
            return bitSource.readBits(16) | (n & 31) << 16;
        }
        throw FormatException.getFormatInstance();
    }

    private static char toAlphaNumericChar(int n) throws FormatException {
        if (n >= ALPHANUMERIC_CHARS.length) {
            throw FormatException.getFormatInstance();
        }
        return ALPHANUMERIC_CHARS[n];
    }
}
